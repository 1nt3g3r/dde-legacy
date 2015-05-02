package ua.com.integer.dde.extension.ui.editor.drag;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.skin.DefaultSkin;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.res.screen.ScreenListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

/**
 * Listener which allows user to drag widgets and save position of that drag to attached {@link UiConfig} object
 *
 * @author 1nt3g3r
 */
public class WidgetDragListener extends InputListener implements ScreenListener {
	private float offsetX, offsetY;
	private Actor touchActor;
	private Vector2 tmp = new Vector2();
	private TextField commandText;
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		touchActor = event.getStage().hit(x, y, true);
		if (touchActor.getUserObject() != null && touchActor.getUserObject() instanceof UiConfig) {
			EditorKernel.editorScreen().selectActor(touchActor);
		}
		
		if (touchActor != null && touchActor.getParent() != null) {
			tmp.set(x, y);
			Vector2 offset = touchActor.stageToLocalCoordinates(tmp);
			offsetX = offset.x;
			offsetY = offset.y;
			return true;
		}
		
		return false;
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		Group parent = touchActor.getParent();
		
		tmp.set(x, y);
		Vector2 newPosition = parent.stageToLocalCoordinates(tmp);
		touchActor.setPosition(newPosition.x - offsetX, newPosition.y - offsetY);
	}

	/**
	 * When we released actor, we should to save his current location to {@link UiConfig}, which is attached as getUserObject()
	 */
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if (touchActor == null) {
			return;
		}
		
		if (touchActor.getUserObject() == null) {
			return;
		}
		
		UiConfig config = (UiConfig) touchActor.getUserObject();
		config.loadFromActor(touchActor);
		
		EditorKernel.editorScreen().updatePropertyPanel();
		
		touchActor = null;
	}
	
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		if (keycode == Keys.ENTER && (commandText == null || !commandText.hasParent())) {
			if (commandText == null) {
				commandText = new TextField("", DefaultSkin.getInstance().getSkin());
				TextFieldStyle style = new TextFieldStyle(commandText.getStyle());
				style.fontColor = Color.GREEN;
				style.font = EditorKernel.getInstance().getFont(20);
				commandText.setStyle(style);
				commandText.setMessageText("Enter a command...");
				
			}
			commandText.setSize(Gdx.graphics.getWidth(), 30);
			EditorKernel.editorScreen().getStage().setKeyboardFocus(commandText);
			EditorKernel.editorScreen().addActor(commandText);

			return true;
		} else if (keycode == Keys.ENTER && commandText != null && commandText.hasParent()) {
			commandText.remove();
			String command = commandText.getText();
			commandText.setText("");
			EditorKernel.getInstance().getActorListDialog().sendCommand(command);
			return true;
		}
		
		return false;
	}

	@Override
	public void eventHappened(AbstractScreen screen, ScreenEvent event) {
		switch(event) {
		case RESIZE:
			if (commandText != null) {
				commandText.setWidth(Gdx.graphics.getWidth());
			}
			break;
		default:
			break;
		}
	}
	@Override
	public void enter(InputEvent event, float x, float y, int pointer,
			Actor fromActor) {
		touchActor = event.getStage().hit(x, y, true);
		if (touchActor.getUserObject() != null && touchActor.getUserObject() instanceof UiConfig) {
			EditorKernel.editorScreen().selectActor(touchActor);
		}
	}
}