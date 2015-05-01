package ua.com.integer.dde.extension.ui.editor.drag;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Listener which allows user to drag widgets and save position of that drag to attached {@link UiConfig} object
 *
 * @author 1nt3g3r
 */
public class WidgetDragListener extends InputListener {
	private float offsetX, offsetY;
	private Actor touchActor;
	private Vector2 tmp = new Vector2();
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		touchActor = event.getStage().hit(x, y, true);
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
		UiConfig config = (UiConfig) touchActor.getUserObject();
		config.loadFromActor(touchActor);
		
		EditorKernel.editorScreen().updatePropertyPanel();
		
		touchActor = null;
	}
}