package ua.com.integer.dde.res.screen.transition.imp;

import ua.com.integer.dde.res.screen.transition.ScreenshotActionTransition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SideOutTransition extends ScreenshotActionTransition {
	private static final int TOP_SIDE = 0;
	private static final int BOTTOM_SIDE = 1;
	private static final int LEFT_SIDE = 2;
	private static final int RIGHT_SIDE = 3;
	
	private Interpolation interpolation = Interpolation.pow2In;
	private float time = 0.3f;
	private int dx, dy;
	
	private SideOutTransition(int mode) {
		dx = Gdx.graphics.getWidth();
		dy = Gdx.graphics.getHeight();
		
		switch(mode) {
		case TOP_SIDE:
			dx = 0;
			break;
		case BOTTOM_SIDE:
			dx = 0;
			dy *= -1;
			break;
		case LEFT_SIDE:
			dy = 0;
			dx *= -1;
			break;
		case RIGHT_SIDE:
			dy = 0;
			break;
		}
		
		updateAction();
	}
	
	public SideOutTransition time(float time) {
		this.time = time;
		updateAction();
		return this;
	}
	
	public SideOutTransition interpolation(Interpolation interpolation) {
		this.interpolation = interpolation;
		updateAction();
		return this;
	}
	
	private void updateAction() {
		setAction(Actions.moveBy(dx, dy, this.time, interpolation));

	}
	
	public static SideOutTransition right() {
		return new SideOutTransition(RIGHT_SIDE);
	}
	
	public static SideOutTransition left() {
		return new SideOutTransition(LEFT_SIDE);
	}
	
	public static SideOutTransition top() {
		return new SideOutTransition(TOP_SIDE);
	}
	
	public static SideOutTransition bottom() {
		return new SideOutTransition(BOTTOM_SIDE);
	}
	
	public SideOutTransition create() {
		updateAction();
		return this;
	}
}
