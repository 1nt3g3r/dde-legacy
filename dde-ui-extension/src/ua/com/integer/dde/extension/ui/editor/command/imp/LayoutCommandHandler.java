package ua.com.integer.dde.extension.ui.editor.command.imp;

import ua.com.integer.dde.extension.ui.Side;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class LayoutCommandHandler implements CommandHandler {

	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		if (arguments.length < 0) {
			screen.setConsoleOutput(help());
			return;
		}

		UiConfig selectedConfig = EditorKernel.editorScreen().getSelectedConfig();
		if (selectedConfig == null) {
			screen.setConsoleOutput("Select actor to apply this command!");
		} else {
			String side = arguments[0];
			if (side.equals("top-left") || side.equals("tl")) {
				selectedConfig.setSide(Side.TOP_LEFT);
			} else if (side.equals("top-right") || side.equalsIgnoreCase("tr")) {
				selectedConfig.setSide(Side.TOP_RIGHT);
			} else if (side.equals("bottom-left") || side.equals("bl")) {
				selectedConfig.setSide(Side.BOTTOM_LEFT);
			} else if (side.equals("bottom-right") || side.equals("br")) {
				selectedConfig.setSide(Side.BOTTOM_RIGHT);
			} else if (side.equals("center") || side.equals("c")) {
				selectedConfig.setSide(Side.CENTER);
			} else {
				screen.setConsoleOutput(help());
			}
			
			EditorKernel.editorScreen().updateConfig();
		}
	}

	@Override
	public String help() {
		return "(lay)out <mode>/n"
				+ "Modes: (t)op-(l)eft, (t)op-(r)ight, (b)ottom-(l)eft, (b)ottom-(r)ight, (c)enter";
	}

}
