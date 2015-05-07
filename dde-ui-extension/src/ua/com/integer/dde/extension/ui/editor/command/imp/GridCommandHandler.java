package ua.com.integer.dde.extension.ui.editor.command.imp;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

import com.badlogic.gdx.math.MathUtils;

public class GridCommandHandler implements CommandHandler {

	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		UiEditorScreen editor = EditorKernel.editorScreen();
		float currentGridPercent = editor.getGridPercent();
		
		if (arguments.length > 0) {
			if (arguments[0].equals("+")) {
				currentGridPercent += 0.05f;
				currentGridPercent = MathUtils.clamp(currentGridPercent, 0.01f, 1f);
				editor.setGridPercent(currentGridPercent);
			} else if (arguments[0].equals("-")) {
				currentGridPercent -= 0.05f;
				currentGridPercent = MathUtils.clamp(currentGridPercent, 0.01f, 1f);
				editor.setGridPercent(currentGridPercent);
			} else if (arguments[0].equals("on")) {
				editor.setDrawGrid(true);
			} else if (arguments[0].equals("off")) {
				editor.setDrawGrid(false);
			} else {
				try {
					int gridPercent = Integer.parseInt(arguments[0]);
					gridPercent = MathUtils.clamp(gridPercent, 1, 100);
					float floatGridPercent = (float) gridPercent / 100f;
					editor.setGridPercent(floatGridPercent);
					editor.setDrawGrid(true);
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
