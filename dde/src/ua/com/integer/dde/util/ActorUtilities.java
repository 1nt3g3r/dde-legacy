package ua.com.integer.dde.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Some useful methods for work with actors
 * 
 * @author 1nt3g3r
 */
public class ActorUtilities {
	/**
	 * Finds and returs actor in ierarchy by his path
	 */
	public static Actor findByNamePath(Group startGroup, String ... path) {
		Group currentGroup = startGroup;
		for(int i = 0; i < path.length; i++) {
			if (i == path.length - 1) {
				return currentGroup.findActor(path[i]);
			} else {
				try {
					currentGroup = currentGroup.findActor(path[i]);
				} catch (ClassCastException ex) {
					return null;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Adds actor willBeOnTheTop over the bottom actor
	 */
	public static void insertByTop(Actor bottom, Actor willBeOnTheTop) {
		Group bottomParent = bottom.getParent();
		willBeOnTheTop.setPosition(bottom.getX(), bottom.getY());
		bottomParent.addActor(willBeOnTheTop);
	}

	/**
	 * Post and run immediately task "action" in the actor as a Actions.run
	 */
	public static void runAsAction(Actor actor, Runnable action) {
		runAsAction(actor, 0f, action);
	}
	
	/**
	 * Post and run task with delay in the actor as a Actions.run
	 * @param delay delay before execute
	 * @param action runnable
	 */
	public static void runAsAction(Actor actor, float delay, Runnable action) {
		actor.addAction(Actions.sequence(Actions.delay(delay), Actions.run(action)));
	}
}
