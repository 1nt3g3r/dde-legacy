package ua.com.integer.dde.res.screen.transition;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.util.ScreenshotUtils;


public abstract class ScreenshotTransition extends ScreenTransition {
	protected TextureRegion screenshot;
	
	@Override
	public void transit(Class<? extends AbstractScreen> next) {
		screenshot = ScreenshotUtils.getScreenshot();
		
		super.transit(next);
	}
}
