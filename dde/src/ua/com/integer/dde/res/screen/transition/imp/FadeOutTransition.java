package ua.com.integer.dde.res.screen.transition.imp;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.com.integer.dde.res.screen.transition.ScreenshotActionTransition;

public class FadeOutTransition extends ScreenshotActionTransition {
	private float time = 0.3f;
	private Interpolation interpolation = Interpolation.linear;
	
	private FadeOutTransition() {
		updateAction();
	}
	
	private void updateAction() {
		setAction(Actions.fadeOut(time, interpolation));
	}
	
	public static FadeOutTransition fadeOut() {
		return new FadeOutTransition();
	}
	
	public FadeOutTransition time(float time) {
		this.time = time;
		updateAction();
		return this;
	}
	
	public FadeOutTransition interpolation(Interpolation interpolation) {
		this.interpolation = interpolation;
		updateAction();
		return this;
	}
	
	public FadeOutTransition create() {
		updateAction();
		return this;
	}
}
