package ua.com.integer.dde.res.screen.transition;

import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.util.ScreenshotUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public abstract class ScreenshotActorTransition extends ScreenshotTransition {
	protected Group background;
	
	@Override
	public void transit(Class<? extends AbstractScreen> next) {
		Image b = new Image(ScreenshotUtils.getScreenshot());
		b.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		b.setOrigin(Align.center);
		b.setScaleY(-1f);
		
		background = new Group();
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		background.setOrigin(Align.center);
		background.addActor(b);
		
		super.transit(next);
	}
	
	@Override
	public void beforeTransaction() {
		getNextScreen().addActor(background);
	}
}
