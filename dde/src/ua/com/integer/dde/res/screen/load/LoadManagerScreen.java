package ua.com.integer.dde.res.screen.load;

import ua.com.integer.dde.res.load.LoadManager;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.res.screen.ScreenListener;

public class LoadManagerScreen extends AbstractScreen {
	private LoadManager loadManager;
	private Class<? extends AbstractScreen> nextScreen;
	private float lastPercent;
	private LoadListener loadListener;
	
	public LoadManagerScreen() {
		initScreenEventListener();
	}
	
	private void initScreenEventListener() {
		addScreenEventListener(new ScreenListener() {
			@Override
			public void eventHappened(AbstractScreen screen, ScreenEvent event) {
				switch(event) {
				case SHOW:
					lastPercent = 0f;
					if (loadListener != null) {
						loadListener.loadPercentChanged(0f);
					}
					break;
				default:
					break;
				}
			}
		});
	}
	
	public void setLoadManager(LoadManager loadManager) {
		this.loadManager = loadManager;
	}
	
	public void setNextScreen(Class<? extends AbstractScreen> nextScreen) {
		this.nextScreen = nextScreen;
	}
	
	public void setLoadListener(LoadListener loadListener) {
		this.loadListener = loadListener;
	}
	
	public void init(LoadManager loadManager, Class<? extends AbstractScreen> nextScreen, LoadListener loadListener) {
		this.loadManager = loadManager;
		this.loadListener = loadListener;
		this.nextScreen = nextScreen;
	}
	
	public void init(LoadManager loadManager, Class<? extends AbstractScreen> nextScreen) {
		init(loadManager, nextScreen, null);
	}
	
	@Override
	public void render(float delta) {
		if (loadManager.loadStep()) {
			getKernel().showScreen(nextScreen);
		} else {
			float currentPercent = loadManager.getLoadPercent();
			if (loadListener != null) {
				loadListener.loadStep(currentPercent);
			}

			if (Math.abs(currentPercent - lastPercent) >= 0.01f) {
				lastPercent = currentPercent;
				if (loadListener != null) {
					loadListener.loadPercentChanged(currentPercent);
				}
			}
		}
		
		super.render(delta);
	}
	
	public LoadManager getLoadManager() {
		return loadManager;
	}
	
	public LoadListener getLoadListener() {
		return loadListener;
	}
	
	public float getPercent() {
		return lastPercent;
	}
	
	public Class<? extends AbstractScreen> getNextScreen() {
		return nextScreen;
	}
}
