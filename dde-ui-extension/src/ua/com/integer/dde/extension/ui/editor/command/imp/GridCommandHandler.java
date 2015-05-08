package ua.com.integer.dde.extension.ui.editor.command.imp;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.drag.GridSettings;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

import com.badlogic.gdx.math.MathUtils;

public class GridCommandHandler implements CommandHandler {

	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		UiEditorScreen editor = EditorKernel.editorScreen();
		float currentGridPercentX = GridSettings.getInstance().getGridPercentX();
		float currentGridPercentY = GridSettings.getInstance().getGridPercentY();
		
		if (arguments.length > 0) {
			if (arguments[0].equals("+")) {
				currentGridPercentX += 0.05f;
				currentGridPercentX = MathUtils.clamp(currentGridPercentX, 0.01f, 1f);
				editor.setGridPercentX(currentGridPercentX);
				GridSettings.getInstance().setGridPercentX(currentGridPercentX);
			} else if (arguments[0].equals("-")) {
				currentGridPercentY -= 0.05f;
				currentGridPercentY = MathUtils.clamp(currentGridPercentY, 0.01f, 1f);
				editor.setGridPercentY(currentGridPercentY);
				GridSettings.getInstance().setGridPercentY(currentGridPercentY);
			} else if (arguments[0].equals("on")) {
				GridSettings.getInstance().setNeedSnapToGrid(true);
			} else if (arguments[0].equals("off")) {
				GridSettings.getInstance().setNeedSnapToGrid(false);
			} else if (arguments[0].equals("show")) {
				GridSettings.getInstance().setNeedShowGrid(true);
				editor.setDrawGrid(true);
			} else if (arguments[0].equals("hide")) {
				GridSettings.getInstance().setNeedShowGrid(false);
				editor.setDrawGrid(false);
			} else {
				try {
					int gridPercent = Integer.parseInt(arguments[0]);
					gridPercent = MathUtils.clamp(gridPercent, 1, 100);
					float floatGridPercent = (float) gridPercent / 100f;
					
					editor.setGridPercentX(floatGridPercent);
					editor.setGridPercentY(floatGridPercent);
					
					GridSettings.getInstance().setGridPercentX(floatGridPercent);
					GridSettings.getInstance().setGridPercentY(floatGridPercent);
					editor.setDrawGrid(true);
					
					GridSettings.getInstance().setNeedShowGrid(true);
					GridSettings.getInstance().setNeedSnapToGrid(true);
				} catch (NumberFormatException ex) {
					screen.setConsoleOutput(help());
				}
			}
		} else {
			screen.setConsoleOutput(help());
		}
		
	}
	
	@Override
	public String help() {
		return "";
	}

}
