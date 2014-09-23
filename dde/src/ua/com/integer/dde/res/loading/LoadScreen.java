package ua.com.integer.dde.res.loading;

import ua.com.integer.dde.res.loading.LoadManager;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.res.screen.ScreenListener;

public class LoadScreen extends AbstractScreen {
	private LoadManager loadManager;
	private LoadListener loadListener;
	private boolean endLoadCall;
	private boolean firstFrame;
	
	class LoadingScreenListener implements ScreenListener {
		@Override
		public void eventHappened(AbstractScreen screen, ScreenEvent event) {
			switch(event) {
			case SHOW:
				endLoadCall = false;
				firstFrame = false;
				break;
			default:
				break;
			}
		}
	}
	
	public void setLoadManager(LoadManager loadManager) {
		this.loadManager = loadManager;
	}
	
	public void setLoadParams(LoadManager loadManager, LoadListener loadListener) {
		this.loadManager = loadManager;
		this.loadListener = loadListener;
	}
	
	@Override
	public void render(float delta) {
		if (endLoadCall) {
			super.render(delta);
		} else {
			load(delta);
		}
	}
	
	private void load(float delta) {
		if (firstFrame) {
			super.render(delta);
			loadListener.beginLoad();
			firstFrame = false;
		} else {
			if (!loadManager.loadStep()) {
				loadListener.loadPercent(loadManager.getLoadPercent());
				super.render(delta);
			} else {
				loadListener.endLoad();
				endLoadCall = true;
			}
		}
	}
}
