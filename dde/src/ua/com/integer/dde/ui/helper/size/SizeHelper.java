package ua.com.integer.dde.ui.helper.size;

import ua.com.integer.dde.ui.distance.Distance;
import ua.com.integer.dde.ui.helper.ActorHelper;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class SizeHelper extends ActorHelper {
	private Distance width;
	private Distance height;
	
	public SizeHelper(Actor actor, Distance width, Distance height) {
		super(actor);
		this.width = width;
		this.height = height;
	}

	@Override
	public void updateActor() {
		Actor actor = getActor();
		actor.setSize(width.getValue(actor), height.getValue(actor));
	}

}
