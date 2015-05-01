package ua.com.integer.dde.extension.ui.editor.command.imp;

import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class FullscreenCommandHandler implements CommandHandler {

	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		screen.sendCommand("resolution 0 0 fullscreen");
	}

	@Override
	public String help() {
		return "Maximizes window to full screen. This command hasn't arguments.";
	}

}
