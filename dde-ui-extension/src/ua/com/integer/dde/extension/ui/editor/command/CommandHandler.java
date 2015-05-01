package ua.com.integer.dde.extension.ui.editor.command;

import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public interface CommandHandler {
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen);
	public String help();
}
