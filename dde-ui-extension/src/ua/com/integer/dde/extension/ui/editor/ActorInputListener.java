package ua.com.integer.dde.extension.ui.editor;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.skin.DefaultSkin;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ActorInputListener extends InputListener {
	private int lastTouchX = -1, lastTouchY = -1;
	private UiEditorScreen screen;
	private JPopupMenu menu;
	
	private Actor actor;
	
	public ActorInputListener(UiEditorScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		if (event.isHandled()) return true;
		
		DefaultSkin.getInstance().getSkin();
		
		if (lastTouchX == -1) lastTouchX = (int) x;
		if (lastTouchY == -1) lastTouchY = (int) y;
		
		actor = event.getListenerActor();
		screen.selectActor(actor);
	
		hideMenu();
		
		if (button == 1) {
			showMenu();
		} else if (button == 0) {
			lastTouchX = (int) x;
			lastTouchY = (int) y;
		}
		event.handle();;

		return true;
	}
	
	private void hideMenu() {
		if (menu != null) {
			menu.setVisible(false);
			menu = null;
		}
	}
	
	private void showMenu() {
		JDialog frame = EditorKernel.getInstance().getMainWindow();
		menu = MenuCreator.getInstance().createMenu(actor, getUiConfig());
		menu.show(frame, frame.getMousePosition().x, frame.getMousePosition().y);
	}
	
	@Override
	public boolean scrolled(InputEvent event, float x, float y, int amount) {
		if (event.isCancelled()) return false;
		
		if (actor != null) {
			actor.getColor().a -= ((float) amount / 30f);
			if (actor.getColor().a < 0) {
				actor.getColor().a = 0;
			}
			
			if (actor.getColor().a > 1) {
				actor.getColor().a = 1;
			}
			
			event.cancel();
		}
		return true;
	}
	
	private UiConfig getUiConfig(){
		return (UiConfig) actor.getUserObject();
	}
}
