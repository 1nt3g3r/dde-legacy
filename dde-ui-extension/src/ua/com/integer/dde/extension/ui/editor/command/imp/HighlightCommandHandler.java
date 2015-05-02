package ua.com.integer.dde.extension.ui.editor.command.imp;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class HighlightCommandHandler implements CommandHandler {

	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		if (arguments.length <= 0) {
			screen.setConsoleOutput(help());
			return;
		}
		
		String firstArgument = arguments[0];
		if (firstArgument.equals("all") || firstArgument.equals("a")) {
			EditorKernel.editorScreen().setBorderAroundActorVisible(true);
			EditorKernel.editorScreen().setBorderAroundInactiveActorsVisible(true);
		} else if (firstArgument.equals("selected") || firstArgument.equals("s")) {
			EditorKernel.editorScreen().setBorderAroundActorVisible(true);
			EditorKernel.editorScreen().setBorderAroundInactiveActorsVisible(false);
		} else if (firstArgument.equals("inactive") || firstArgument.equals("i")) {
			EditorKernel.editorScreen().setBorderAroundActorVisible(false);
			EditorKernel.editorScreen().setBorderAroundInactiveActorsVisible(true);
		} else {
			screen.setConsoleOutput(help());
		}
	}

	@Override
	public String help() {
		return "higlight <mode>/n"
				+ "Modes: <(a)ll>, <(s)elected>, <(i)nactive>";
	}

}
