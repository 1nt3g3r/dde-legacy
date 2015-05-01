package ua.com.integer.dde.extension.ui.editor.command;

import ua.com.integer.dde.extension.ui.editor.command.imp.DefaultHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.FullscreenCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.ResolutionCommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

import com.badlogic.gdx.utils.ObjectMap;

public class CommandProcessor {
	private ObjectMap<String, CommandHandler> commandHandlers = new ObjectMap<String, CommandHandler>();
	private DefaultHandler unknownCommandHandler = new DefaultHandler();
	
	public CommandProcessor() {
		initResolutionHandler();
		initFullscreenHandler();
	}

	private void initResolutionHandler() {
		CommandHandler resolutionHandler = new ResolutionCommandHandler();
		commandHandlers.put("resolution", resolutionHandler);
		commandHandlers.put("res", resolutionHandler);
	}
	
	private void initFullscreenHandler() {
		CommandHandler fullScreenHandler = new FullscreenCommandHandler();
		commandHandlers.put("fullscreen", fullScreenHandler);
	}
	
	public void executeCommand(String rawComand, UiEditorDialog screen) {
		String[] commandParts = rawComand.split(" ");
		String command = commandParts[0];
		String[] params = getParams(commandParts);
		
		CommandHandler handler = commandHandlers.get(command);
		if (handler == null) {
			handler = unknownCommandHandler;
		}

		if (params.length == 1 && params[0].equals("help")) {
			screen.setConsoleOutput(handler.help());
		} else {
			handler.executeCommand(command, params, screen);
		}
	}
	
	private String[] getParams(String[] commandParts) {
		String[] result = new String[commandParts.length-1];
		for(int i = 0; i < result.length; i++) {
			result[i] = commandParts[i+1];
		}
		return result;
	}
}
