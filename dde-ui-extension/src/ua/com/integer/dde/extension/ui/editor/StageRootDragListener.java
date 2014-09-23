package ua.com.integer.dde.extension.ui.editor;

import ua.com.integer.dde.extension.ui.Side;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.UiConfigurator;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.startpanel.Settings;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Слушатель на перемещение корневого (root) актера у 
 * stage экрана UiEditorScreen или перемещения клавишами курсора выбранного актера.
 *  Возможность перетаскивания актера может быть отключена -  
 * для этого надо у Settings.setSettingsClass(UiEditorDialog.class) выставить булево свойство 
 * "allow-drag-root-group" в false
 * 
 * Если перетаскивание актера отключено - включен режим перемещения и наоборот
 * 
 * @author 1nt3g3r
 */
public class StageRootDragListener extends InputListener {
	private float touchX, touchY;
	private float stageMoveDx, stageMoveDy;
	
	private boolean isDragEnabled;
	
	private static float speedMultiplier = 0.1f;
	
	class MoveTask extends Task {
		@Override
		public void run() {
			Actor root = EditorKernel.getInstance().getScreen(UiEditorScreen.class).getStage().getRoot();
			root.moveBy(stageMoveDx, stageMoveDy);
		}
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		updateAllowDragState();
		
		touchX = x;
		touchY = y;
		
		return isDragEnabled;
	}
	
	private void updateAllowDragState() {
		Settings sets = Settings.getInstance();
		sets.setSettingsClass(UiEditorDialog.class);
		isDragEnabled = sets.getBoolean("allow-drag-root-group", true);
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		UiEditorScreen screen = EditorKernel.getInstance().getScreen(UiEditorScreen.class);
		screen.getStage().getRoot().setPosition(
				(int) (screen.getStage().getRoot().getX() + (x - touchX)), 
				(int) (screen.getStage().getRoot().getY() + (y - touchY)));
		touchX = x;
		touchY = y;
		
		EditorKernel.getInstance().getActorListDialog().updateStageRootPosition();
	}
	
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		updateAllowDragState();
		if (isDragEnabled) {
			
			stageMoveDx = 0;
			stageMoveDy = 0;
			
			switch(event.getKeyCode()) {
			case Keys.LEFT: 
				stageMoveDx = -20; 
				break;
			case Keys.RIGHT:
				stageMoveDx = 20;
				break;
			case Keys.UP:
				stageMoveDy = 20;
				break;
			case Keys.DOWN:
				stageMoveDy = -20;
				break;
			}
			
			Actor root = EditorKernel.getInstance().getScreen(UiEditorScreen.class).getStage().getRoot();
			root.moveBy(stageMoveDx, stageMoveDy);
			
			EditorKernel.getInstance().getActorListDialog().updateStageRootPosition();
			return true;
		} else {
			UiConfig config = EditorKernel.getInstance().getScreen(UiEditorScreen.class).getSelectedConfig();
			if (config == null) return false;
			
			float dx = 0;
			float dy = 0;
			switch(event.getKeyCode()) {
			case Keys.LEFT :
				dx = -speedMultiplier;
				dy = 0f;
				break;
			case Keys.RIGHT:
				dx = speedMultiplier;
				dy = 0f;
				break;
			case Keys.UP:
				dx = 0f;
				dy = speedMultiplier;
				break;
			case Keys.DOWN:
				dx = 0f;
				dy = -speedMultiplier;
				break;
			}
			
			if (config.targetCorner == Side.BOTTOM_LEFT || config.targetCorner == Side.TOP_LEFT || config.targetCorner == Side.CENTER) {
				config.horizontalDistance.addSizeValue(dx);
			} else {
				config.horizontalDistance.addSizeValue(-dx);
			}
			
			if (config.targetCorner == Side.BOTTOM_LEFT || config.targetCorner == Side.BOTTOM_RIGHT || config.targetCorner == Side.CENTER) {
				config.verticalDistance.addSizeValue(dy);
			} else {
				config.verticalDistance.addSizeValue(-dy);
			}
			
			UiConfigurator configurator = EditorKernel.getInstance().getScreen(UiEditorScreen.class).getConfiguratorForConfig(config);
			if (configurator != null) {
				configurator.eventHappened(EditorKernel.getInstance().getScreen(UiEditorScreen.class), ScreenEvent.RESIZE);
				EditorKernel.getInstance().getActorListDialog().updatePropertyPanelForSelectedActor();
			} else {
				System.out.println("No configurator!");
			}
			return true;
		}
	}

	@Override
	public boolean keyTyped(InputEvent event, char character) {
		switch(character) {
		case '=':
			speedMultiplier *= 10f;
			if (speedMultiplier > 0.1f) {
				speedMultiplier = 0.1f;
			}
			showSpeedMessage();
			return true;
		case '-':
			speedMultiplier /= 10f;
			if (speedMultiplier <= 0.001f) {
				speedMultiplier = 0.001f;
			}
			showSpeedMessage();
			return true;
		}
		System.out.println(character);
		return super.keyTyped(event, character);
	}
	
	private void showSpeedMessage() {
		String speed = "";
		if (speedMultiplier >= 0.1f) {
			speed = "fast";
		} else if (speedMultiplier >= 0.01f) {
			speed = "medium";
		} else {
			speed = "slow";
		}
		
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).showMessage("Move speed: " + speed);
	}
}
