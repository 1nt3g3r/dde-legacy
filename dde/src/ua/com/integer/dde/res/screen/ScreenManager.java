package ua.com.integer.dde.res.screen;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.loading.LoadManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Screen manager. Contains game screens. Can contain only one instance 
 * of one game screen - as example, if you create TestScreen class, you 
 * can't put it twice to this manager.
 * 
 * @author 1nt3g3r
 *
 */
public class ScreenManager implements Disposable, LoadManager {
	private Array<Class<? extends AbstractScreen>> loadQueue;
	private int totalScreenCountToLoad, loadedScreenCount;
	
	private ObjectMap<String, AbstractScreen> screens;
	private Array<String> screenNames;
	private Array<String> screenStack;
	private DDKernel kernel;
	
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
	}

	/**
	 * Clears this ScreenManager and call dispose() 
	 * for every screen
	 */
	@Override
	public void dispose() {
		for(AbstractScreen screen : screens.values()) {
			screen.dispose();
			//screen.getStage().dispose();
		}
		screens.clear();
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
		if (screenStack.size > 1) {
			screenStack.pop();
			showScreen(screenStack.peek());
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
			return (T) toReturn;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
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
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		showScreen(screenToShow.getScreenName());
	}

	@Override
	public void loadAll() {
		Gdx.app.log("ScreenManager", "loadAll() isn't implemented in ScreenManager now");
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
}
