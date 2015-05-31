package ua.com.integer.dde.extension.ui.editor.drag;

import java.awt.Cursor;

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
import com.badlogic.gdx.utils.Align;

/**
 * Listener which allows user to drag widgets and save position of that drag to attached {@link UiConfig} object
 *
 * @author 1nt3g3r
 */
public class WidgetDragListener extends InputListener implements ScreenListener {
	public static final int QUAD_SIZE_TO_RESIZE = 10;
	
	public static final int FREE_MOVE = 0;
	public static final int SNAP_TO_BOTTOM_LEFT = 1;
	public static final int SNAP_TO_LEFT_ONLY = 2;
	public static final int SNAP_TO_TOP_LEFT = 3;
	public static final int SNAP_TO_TOP_ONLY = 4;
	public static final int SNAP_TO_TOP_RIGHT = 5;
	public static final int SNAP_TO_RIGHT_ONLY = 6;
	public static final int SNAP_TO_BOTTOM_RIGHT = 7;
	public static final int SNAP_TO_BOTTOM_ONLY = 8;
	public static final int SNAP_TO_CENTER = 9;
	public static final int RESIZE = 10;
	
	private float offsetX, offsetY;
	private Actor touchActor;
	private Vector2 tmp = new Vector2();
	private TextField commandText;
	private Vector2 nearestGridPosition = new Vector2();
	
	private int dragMode;
	
	private float gridPercentX, gridPercentY;
	private float deltaX, deltaY;
	
	private boolean needSnapToGrid;
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		touchActor = event.getStage().hit(x, y, true);
		
		if (touchActor != null && touchActor.getParent() != null && touchActor == EditorKernel.editorScreen().getSelectedActor()) {
			needSnapToGrid = GridSettings.getInstance().needSnapToGrid();
			gridPercentX = GridSettings.getInstance().getGridPercentX();
			gridPercentY = GridSettings.getInstance().getGridPercentY();
			
			tmp.set(x, y);
			Vector2 offset = touchActor.stageToLocalCoordinates(tmp);
			offsetX = offset.x;
			offsetY = offset.y;
			
			deltaX = touchActor.getWidth() - offsetX;
			deltaY = touchActor.getHeight() - offsetY;
			
			setupDragMode(offsetX, offsetY);
			setupCursor();
			
			return true;
		}
		
		return false;
	}
	
	private void setupCursor() {
		if (dragMode == RESIZE) {
			EditorKernel.getInstance().getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		} else {
			EditorKernel.getInstance().getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
	}
	
	private void setupDragMode(float touchX, float touchY) {
		if (touchX >= touchActor.getWidth() - QUAD_SIZE_TO_RESIZE && touchY >= touchActor.getHeight() - QUAD_SIZE_TO_RESIZE) {
			dragMode = RESIZE;
			return;
		}
		
		if (!needSnapToGrid) {
			dragMode = FREE_MOVE;
			return;
		}
		
		float oneThirdWidth = touchActor.getWidth()/3f;
		float oneThirdHeight = touchActor.getHeight()/3f;
		
		if (touchX <= oneThirdWidth) {
			if (touchY <= oneThirdHeight) {
				dragMode = SNAP_TO_BOTTOM_LEFT;
			} else if (touchY > oneThirdHeight && touchY <= 2 * oneThirdHeight) {
				dragMode = SNAP_TO_LEFT_ONLY;
			} else {
				dragMode = SNAP_TO_TOP_LEFT;
			}
		} else if (touchX > oneThirdWidth && touchX <= 2 * oneThirdWidth) {
			if (touchY <= oneThirdHeight) {
				dragMode = SNAP_TO_BOTTOM_ONLY;
			} else if (touchY > oneThirdHeight && touchY <= 2 * oneThirdHeight) {
				dragMode = SNAP_TO_CENTER;
			} else {
				dragMode = SNAP_TO_TOP_ONLY;
			}
		} else {
			if (touchY <= oneThirdHeight) {
				dragMode = SNAP_TO_BOTTOM_RIGHT;
			} else if (touchY > oneThirdHeight && touchY <= 2 * oneThirdHeight) {
				dragMode = SNAP_TO_RIGHT_ONLY;
			} else {
				dragMode = SNAP_TO_TOP_RIGHT;
			}
		}
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		if (dragMode == RESIZE) {
			handleWidgetResizing(x, y);
		} else {
			handleWidgetMoving(x, y);
		}
	}
	
	private void handleWidgetResizing(float x, float y) {
		if (needSnapToGrid) {
			calculateNearestGrid(x - deltaX, y - deltaY);
			tmp.set(nearestGridPosition);
		} else {
			tmp.set(x, y);
		}

		Vector2 touchActorCoords = touchActor.stageToLocalCoordinates(tmp);
		
		float newWidth = touchActorCoords.x;
		float newHeight = touchActorCoords.y;
		
		if (!needSnapToGrid) {
			newWidth += deltaX;
			newHeight += deltaY;
		}

		touchActor.setSize(newWidth, newHeight);
	}

	private void handleWidgetMoving(float x, float y) {
		Group parent = touchActor.getParent();
		
		float drawOffsetX = 0;
		float drawOffsetY = 0;
		
		switch(dragMode) {
		case SNAP_TO_BOTTOM_LEFT:
			calculateNearestGrid(x - offsetX, y - offsetY);
			
			x = nearestGridPosition.x;
			y = nearestGridPosition.y;
			
			break;
		case SNAP_TO_BOTTOM_RIGHT:
			calculateNearestGrid(x - offsetX + touchActor.getWidth(), y - offsetY);
			
			x = nearestGridPosition.x;
			y = nearestGridPosition.y;
			
			drawOffsetX = -touchActor.getWidth();
			break;
		case SNAP_TO_TOP_LEFT:
			calculateNearestGrid(x - offsetX, y - offsetY + touchActor.getHeight());
			
			x = nearestGridPosition.x;
			y = nearestGridPosition.y;
			
			drawOffsetY = -touchActor.getHeight();
			break;
		case SNAP_TO_TOP_RIGHT:
			calculateNearestGrid(x - offsetX + touchActor.getWidth(), y - offsetY + touchActor.getHeight());

			x = nearestGridPosition.x;
			y = nearestGridPosition.y;
			
			drawOffsetX = -touchActor.getWidth();
			drawOffsetY = -touchActor.getHeight();
			break;
		case SNAP_TO_LEFT_ONLY:
			calculateNearestGrid(x - offsetX, y - offsetY);
			
			x = nearestGridPosition.x;
			y = y - offsetY;
			break;
		case SNAP_TO_RIGHT_ONLY:
			calculateNearestGrid(x - offsetX + touchActor.getWidth(), y - offsetY);

			x = nearestGridPosition.x;
			
			drawOffsetX = -touchActor.getWidth();
			y = y - offsetY;
			break;
		case SNAP_TO_TOP_ONLY:
			calculateNearestGrid(x - offsetX, y - offsetY + touchActor.getHeight());

			y = nearestGridPosition.y;
			x = x -offsetX;
			
			drawOffsetY = -touchActor.getHeight();
			break;
		case SNAP_TO_BOTTOM_ONLY:
			calculateNearestGrid(x - offsetX, y - offsetY);
			y = nearestGridPosition.y;
			x = x - offsetX;
			break;
		case SNAP_TO_CENTER:
			calculateNearestGrid(x - offsetX + touchActor.getWidth()/2f, y - offsetY + touchActor.getHeight()/2f);

			x = nearestGridPosition.x;
			y = nearestGridPosition.y;
			
			drawOffsetX = -touchActor.getWidth()/2f;
			drawOffsetY = -touchActor.getHeight()/2f;
			break;
		case FREE_MOVE:
			drawOffsetX = -offsetX;
			drawOffsetY = -offsetY;
			break;
		}
		
		tmp.set(x, y);
		Vector2 newPosition = parent.stageToLocalCoordinates(tmp);
		
		touchActor.setPosition(newPosition.x + drawOffsetX, newPosition.y + drawOffsetY);
	}
	
	/**
	 * Параметры передаются в координатах относительно Stage
	 * @param touchX
	 * @param touchY
	 */
	private void calculateNearestGrid(float touchX, float touchY) {
		Group parent = touchActor.getParent();
		
		nearestGridPosition.set(0, 0);
		nearestGridPosition = parent.localToStageCoordinates(nearestGridPosition);
		
		float startX = nearestGridPosition.x;
		float startY = nearestGridPosition.y;
		
		float endX = nearestGridPosition.x + parent.getWidth();
		float endY = nearestGridPosition.y + parent.getHeight();
		
		float deltaX = parent.getWidth() * gridPercentX;
		float deltaY = parent.getHeight() * gridPercentY;
		
		float nearestDistanceX = Float.MAX_VALUE;
		float nearestDistanceY = Float.MAX_VALUE;
		float nearestX = touchX;
		float nearestY = touchY;
		
		for(float x = startX; x <= endX; x += deltaX) {
			float tx = Math.abs(x - touchX);
			if (tx < nearestDistanceX) {
				nearestDistanceX = tx;
				nearestX = x;
			}
		}
		
		for (float y = startY; y <= endY; y += deltaY) {
			float ty = Math.abs(y - touchY);
				
			if (ty < nearestDistanceY) {
				nearestDistanceY = ty;
				nearestY = y;
			}
		}
		
		if (nearestDistanceX <= deltaX/2f) {
			nearestGridPosition.x = nearestX;
		} else {
			nearestGridPosition.x = touchX;
		}
		
		if (nearestDistanceY <= deltaY/2f) {
			nearestGridPosition.y = nearestY;
		} else {
			nearestGridPosition.y = touchY;
		}
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
		
		if (dragMode == RESIZE) {
			touchUpOnResize();
		} else {
			touchUpOnDragging();
		}
		
		EditorKernel.getInstance().getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		touchActor = null;
		EditorKernel.editorScreen().updatePropertyPanel();

	}
	
	private void touchUpOnResize() {
		UiConfig config = (UiConfig) touchActor.getUserObject();
		config.loadSizeFromActor(touchActor);
		
		if (Boolean.parseBoolean(config.get("actor-center-origin", "true"))) {
			touchActor.setOrigin(Align.center);
		}
	}

	private void touchUpOnDragging() {
		UiConfig config = (UiConfig) touchActor.getUserObject();
		config.loadPositionFromActor(touchActor);
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
			EditorKernel.executeCommand(command);
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
	
	public TextField getCommandText() {
		return commandText;
	}
}