package ua.com.integer.dde.ui.listener;

import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GoToScreenListener extends ClickListener {
	private Class<? extends AbstractScreen> screen;
	private Class<? extends AbstractScreen> fallbackScreen;
	
	public GoToScreenListener() {
	}
	
	public GoToScreenListener(Class<? extends AbstractScreen> screen) {
		this.screen = screen;
	}
	
	public GoToScreenListener(Class<? extends AbstractScreen> screen, Class<? extends AbstractScreen> fallbackScreen) {
		this.screen = screen;
		this.fallbackScreen = fallbackScreen;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		switchToScreen();
	}
	
	public void switchToScreen() {
		if (screen == null && fallbackScreen != null) {
			AbstractScreen.getKernel().showScreen(fallbackScreen);
		} else {
			AbstractScreen.getKernel().showScreen(screen);
		}
	}
}
