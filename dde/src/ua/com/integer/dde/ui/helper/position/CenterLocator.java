package ua.com.integer.dde.ui.helper.position;

import ua.com.integer.dde.ui.helper.ActorHelper;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class CenterLocator extends ActorHelper {
	public CenterLocator(Actor actor) {
		super(actor);
	}

	@Override
	public void updateActor() {
		float parentWidth = 0;
		float parentHeight = 0;
		Actor actor = getActor();
		if (actor.getParent() == null) {
			if (actor.getStage() != null) {
				parentWidth = actor.getStage().getWidth();
				parentHeight = actor.getStage().getHeight();
			} else {
				return;
			}
		} else {
			parentWidth = actor.getParent().getWidth();
			parentHeight = actor.getParent().getHeight();
		}
		
		float x = (parentWidth - actor.getWidth())/2;
		float y = (parentHeight - actor.getHeight())/2;
		
		actor.setPosition(x, y);
	}
}
