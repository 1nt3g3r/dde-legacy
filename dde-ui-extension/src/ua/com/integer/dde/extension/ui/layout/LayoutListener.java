package ua.com.integer.dde.extension.ui.layout;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.res.screen.ScreenListener;

public class LayoutListener implements ScreenListener {
	private Actor toLayout;
	
	public LayoutListener(Actor toLayout) {
		this.toLayout = toLayout;
	}
	
	@Override
	public void eventHappened(AbstractScreen screen, ScreenEvent event) {
		if (event == ScreenEvent.SHOW || event == ScreenEvent.RESIZE) {
			layout(toLayout);
		}
	}
	
	public Actor getActor() {
		return toLayout;
	}
	
	private void layout(Actor actor) {
		if (actor == null) return;
		
		if (actor instanceof Layout) {
			((Layout) actor).layout();
		}
		
		if(actor instanceof Group) {
			for(Actor child : ((Group) actor).getChildren()) {
				layout(child);
			}
		}
	}

}
