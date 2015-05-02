package ua.com.integer.dde.extension.ui.editor.command;

import ua.com.integer.dde.extension.ui.editor.command.imp.AlignActorCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.DefaultHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.FullscreenCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.HighlightCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.LayoutCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.ResolutionCommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

import com.badlogic.gdx.utils.ObjectMap;

public class CommandProcessor {
	private ObjectMap<String, CommandHandler> commandHandlers = new ObjectMap<String, CommandHandler>();
	private DefaultHandler unknownCommandHandler = new DefaultHandler();
	
	public CommandProcessor() {
		initResolutionHandler();
		initFullscreenHandler();
		initHiglightHandler();
		initLayoutHandler();
		initAlignCommandHandler();
	}

	private void initResolutionHandler() {
		CommandHandler resolutionHandler = new ResolutionCommandHandler();
		commandHandlers.put("resolution", resolutionHandler);
		commandHandlers.put("res", resolutionHandler);
	}
	
	private void initFullscreenHandler() {
		CommandHandler fullScreenHandler = new FullscreenCommandHandler();
		commandHandlers.put("fullscreen", fullScreenHandler);
		commandHandlers.put("fsc", fullScreenHandler);
	}
	
	private void initHiglightHandler() {
		CommandHandler higlightHandler = new HighlightCommandHandler();
		commandHandlers.put("highlight", higlightHandler);
		commandHandlers.put("hl", higlightHandler);
	}
	
	private void initLayoutHandler() {
		CommandHandler layoutHandler = new LayoutCommandHandler();
		commandHandlers.put("layout", layoutHandler);
		commandHandlers.put("lay", layoutHandler);
	}
	
	private void initAlignCommandHandler() {
		CommandHandler alignHandler = new AlignActorCommandHandler();
		commandHandlers.put("align", alignHandler);
		commandHandlers.put("al", alignHandler);
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
