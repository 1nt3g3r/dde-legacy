package ua.com.integer.dde.ui.helper;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ActorHelper {
	private Actor actor;
	
	public ActorHelper(Actor actor) {
		this.actor = actor;
	}
	
	public Actor getActor() {
		return actor;
	}
	
	public abstract void updateActor();
}
