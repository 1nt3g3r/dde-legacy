package ua.com.integer.dde.extension.ui.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.skin.DefaultSkin;
import ua.com.integer.dde.startpanel.FrameTools;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ActorInputListener extends InputListener {
	private int lastTouchX = -1, lastTouchY = -1;
	private UiEditorScreen screen;
	private JPopupMenu menu;
	
	private Actor actor;
	private long lastClickTime;
	
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
			if (Math.abs(lastTouchX - x) < 10 && (Math.abs(lastTouchY - y) < 10)) {
				checkDoubleClick();
			}
			
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
		JDialog frame = EditorKernel.getInstance().getActorListDialog();
		menu = MenuCreator.getInstance().createMenu(actor, getUiConfig());
		menu.show(frame, frame.getMousePosition().x, frame.getMousePosition().y);
	}
	
	private void checkDoubleClick() {
		if (System.currentTimeMillis() - lastClickTime <= 500) {
			lastTouchX = -1;
			lastTouchY = -1;
			editActor();
		}
		
		lastClickTime = System.currentTimeMillis();
	}
	
	class EditItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			editActor();
		}
	}
	
	private void editActor() {
		UiConfigEditor editor = new UiConfigEditor();
		
		editor.addConfigChangeListener(screen);
		
		editor.setConfig(getUiConfig(), actor);
		FrameTools.situateOnCenter(editor);
		
		editor.setVisible(true);
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
