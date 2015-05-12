package ua.com.integer.dde.res.screen.load;

import ua.com.integer.dde.res.load.LoadManager;

import com.badlogic.gdx.scenes.scene2d.Group;

public class LoadManagerActor extends Group {
	private LoadManager loadManager;
	private float lastPercent;
	private LoadListener loadListener;
	private boolean init;
	
	public void setLoadManager(LoadManager loadManager) {
		this.loadManager = loadManager;
	}
	
	public void setLoadListener(LoadListener loadListener) {
		this.loadListener = loadListener;
	}
	
	public LoadListener getLoadListener() {
		return loadListener;
	}
	
	public LoadManager getLoadManager() {
		return loadManager;
	}
	
	@Override
	public void act(float delta) {
		if (!init) {
			lastPercent = 0f;
			if (loadListener != null) {
				loadListener.loadPercentChanged(0f);
			}
			init = true;
			System.out.println("Init here");
		}
		
		if (loadManager.loadStep()) {
			if (loadListener != null) {
				loadListener.loadPercentChanged(1f);
				loadListener.finished();
			}
			
			init = false;
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
		
		super.act(delta);
	}
}
