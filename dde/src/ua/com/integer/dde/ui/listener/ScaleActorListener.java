package ua.com.integer.dde.ui.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ScaleActorListener extends InputListener {
	private boolean allowScale = true;
	private float delta;
	private float time = 0.1f;
	
	public ScaleActorListener(float delta, float time) {
		this.delta = delta;
		this.time = time;
	}
	
	public ScaleActorListener() {
		this(0.1f, 0.1f);
	}
	
	public void setDelta(float delta) {
		this.delta = delta;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		if (!allowScale) return false;
		
		allowScale = false;
		event.getListenerActor().addAction(Actions.scaleBy(delta, delta, time));
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		allowScale = true;
		event.getListenerActor().addAction(Actions.scaleBy(-delta, -delta, time));
	}
}
