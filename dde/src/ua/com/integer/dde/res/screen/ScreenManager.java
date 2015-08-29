package ua.com.integer.dde.res.screen;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.load.LoadManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Screen manager. Contains game screens. Can contain only one instance 
 * of each screen - as example, if you create TestScreen class, you 
 * can't put it twice to this manager.
 * 
 * @author 1nt3g3r
 *
 */
public class ScreenManager implements Disposable, LoadManager {
	public static final String LOG_TAG = "DDE.ScreenManager";
	
	private Array<Class<? extends AbstractScreen>> loadQueue;
	private int totalScreenCountToLoad, loadedScreenCount;
	
	private ObjectMap<String, AbstractScreen> screens;
	private Array<String> screenNames;
	private Array<String> screenStack;
	private DDKernel kernel;
	private boolean logEnabled;
	
	/**
	 * Creates manager and call setGame() method
	 */
	public ScreenManager(DDKernel game) {
		this();
		setGame(game);
	}
	
	/**
	 * Creates screen manager. For future use you should call setGame() method
	 */
	public ScreenManager() {
		screens = new ObjectMap<String, AbstractScreen>();
		screenNames = new Array<String>();
		screenStack = new Array<String>();
		
		loadQueue = new Array<Class<? extends AbstractScreen>>();
		
		log("created");
	}
	
	/**
	 * Sets game, where screens will be shown. You should 
	 * call this method before adding screens.
	 */
	public void setGame(DDKernel kernel) {
		this.kernel = kernel;
	}
	
	/**
	 * Adds AbstractScreen instance object
	 */
	public void addScreen(AbstractScreen screen) {
		screens.put(screen.getScreenName(), screen);
		screenNames.add(screen.getScreenName());
		log("screen " + screen.getScreenName() + " added");
	}
	
	/**
	 * Removes screen with selected name
	 * @param screenName
	 */
	public void removeScreen(String screenName) {
		Screen toRemove = screens.get(screenName);
		screenNames.removeValue(screenName, false);
		if (toRemove != null) {
			toRemove.dispose();
			screens.remove(screenName);
			log("screen " + screenName + " removed");
		}
	}
	
	/**
	 * @param screenName name of screen, which you want to show.
	 */
	public void showScreen(String screenName) {
		if (kernel == null) {
			throw new IllegalStateException("Game isn't set. Use setGame() method before!");
		}
		Screen screen = screens.get(screenName);
		if (screen == null) {
			throw new IllegalArgumentException("Screen " + screenName + " not found!");
		}
		kernel.setScreen(screen);
		log("screen " + screenName + " was shown");
	}

	/**
	 * Clears this ScreenManager and call dispose() 
	 * for every screen. Also disposes AbstractScreen.batch
	 */
	@Override
	public void dispose() {
		log("start dispose...");
		for(AbstractScreen screen : screens.values()) {
			screen.dispose();
		}
		screens.clear();
		if (AbstractScreen.getBatch() != null) {
			AbstractScreen.disposeBatch();
		}
		log("dispose finished");
	}
	
	/**
	 * Return screen with selected name. If no screen found, throws IllegalArgumentException
	 */
	public AbstractScreen getScreen(String screenName) {
		AbstractScreen toReturn = screens.get(screenName);
		if (toReturn == null) {
			throw new IllegalArgumentException("Screen \"" + screenName + "\" not found!"); 
		}
		return toReturn;
	}
	
	/**
	 * Shows previous shown screen.
	 * @param needUpdate if true "performUpdate()" method will be called in previous showed screen
	 */
	public void previousScreen(boolean needUpdate) {
		if (screenStack.size >= 1) {
			screenStack.pop();
			showScreen(screenStack.peek());
			log("switched to previous screen");
		}
	}
	
	/**
	 * @return list with loaded screen names
	 */
	public Array<String> getScreenNames() {
		return screenNames;
	}
	
	/**
	 * Returns screen by his class
	 * @param type class of the screen
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractScreen> T getScreen(Class<T> type) {
		for (AbstractScreen screen : screens.values()) {
			if (screen.getClass() == type) {
				return (T) screen;
			}
		}

		AbstractScreen toReturn = null;
		try {
			toReturn = (AbstractScreen) type.newInstance();
			screens.put(toReturn.getScreenName(), toReturn);
			log("screen <" + toReturn.getScreenName() + " created");
			return (T) toReturn;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		throw new IllegalArgumentException("No screen for this class!");
	}
	
	/**
	 * Add screen with selected class to the load queue, but don't load it. 
	 * Call loadStep() to do it;
	 */
	public <T extends AbstractScreen> void loadScreen(Class<T> type) {
		loadQueue.add(type);
		totalScreenCountToLoad++;
		log("screen with type " + type + " added to load queue");
	}
	
	/**
	 * Shows screen by his class
	 */
	public <T extends AbstractScreen> void showScreen(Class<T> type) {
		AbstractScreen screenToShow = getScreen(type);
		if (screenToShow == null) {
			try {
				screenToShow = (AbstractScreen) type.newInstance();
				screens.put(screenToShow.getScreenName(), screenToShow);
				log("screen " + screenToShow.getScreenName() + " created");
			} catch (InstantiationException e) {
				log(e.toString());
			} catch (IllegalAccessException e) {
				log(e.toString());
			}
		}
		showScreen(screenToShow.getScreenName());
	}

	@Override
	public void loadAll() {
		log("loadAll() isn't implemented in ScreenManager now");
	}

	@Override
	public boolean loadStep() {
		if (loadQueue.size > 0) {
			Class <? extends AbstractScreen> type = loadQueue.pop();
			try {
				AbstractScreen screen = type.newInstance();
				addScreen(screen);
				loadedScreenCount++;
				return false;
			} catch (Exception e) {
				Gdx.app.error("ScreenManager", "Error during loading " + type + " screen! " + e);
				System.exit(0);
			}
		}
		return true;
	}

	@Override
	public float getLoadPercent() {
		if (totalScreenCountToLoad == 0) {
			return 1;
		}
		return (float) loadedScreenCount / (float) totalScreenCountToLoad;
	}

	@Override
	public int getAssetCount() {
		return 0;
	}

	@Override
	public int getLoadedAssetCount() {
		return 0;
	}
	
	public void disposeScreen(Class<? extends AbstractScreen> screen) {
		log("try to dispose screen " + screen);
		if (isLoaded(screen)) {
			AbstractScreen screenToRemove = getScreen(screen);
			String screenName = screenToRemove.getScreenName();
		
			screenToRemove.dispose();
			screens.remove(screenName);
			
			log("   ... success, screen " + screenName + " disposed");
		} else {
			log("   ... fail, screen " + screen + " isn't loaded");
		}
	}
	
	public boolean isLoaded(Class<? extends AbstractScreen> screen) {
		return screens.containsKey(screen + "");
	}

	@Override
	public boolean isLoaded(String name) {
		return screens.containsKey(name);
	}
	
	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}
	
	private void log(String text) {
		if (logEnabled) {
			Gdx.app.log(LOG_TAG, text);
		}
	}
}
