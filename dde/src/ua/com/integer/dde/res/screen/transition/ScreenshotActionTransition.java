package ua.com.integer.dde.res.screen.transition;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;

public class ScreenshotActionTransition extends ScreenshotActorTransition {
	private Action action;
	
	public ScreenshotActionTransition(ScreenshotActionTransition ... transitions) {
		ParallelAction parallel = Actions.parallel();
		for(ScreenshotActionTransition transition: transitions) {
			parallel.addAction(transition.getAction());
		}
		
		setAction(parallel);
	}
	
	public ScreenshotActionTransition() {
	}
	
	public ScreenshotActionTransition(Action action) {
		this.action = action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public void performTransition() {
		background.addAction(Actions.sequence(action, Actions.removeActor()));
		getCore().showScreen(next);
	}
	
	public Action getAction() {
		return action;
	}
}
