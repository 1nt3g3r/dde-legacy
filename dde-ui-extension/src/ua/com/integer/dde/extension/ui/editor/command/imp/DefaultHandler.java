package ua.com.integer.dde.extension.ui.editor.command.imp;

import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class DefaultHandler implements CommandHandler {
	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		screen.setConsoleOutput("Unknown command: " + command + ". " + help());
	}

	@Override
	public String help() {
		return "Type \"<command> help\" to get help by some command\n"
				+ "Example: resolution help";
	}
}
