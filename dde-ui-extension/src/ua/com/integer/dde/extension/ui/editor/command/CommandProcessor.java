package ua.com.integer.dde.extension.ui.editor.command;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.command.imp.AlignActorCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.CopyCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.DefaultHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.FullscreenCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.GridCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.HighlightCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.LayoutCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.PasteCommandHandler;
import ua.com.integer.dde.extension.ui.editor.command.imp.RemoveCommandHandler;
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
		initGridCommandHandler();
		initRemoveCommandHandler();
		initCopyCommandHandler();
		initPasteCommandHandler();
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
	
	private void initGridCommandHandler() {
		CommandHandler gridHandler = new GridCommandHandler();
		commandHandlers.put("grid", gridHandler);
	}
	
	private void initRemoveCommandHandler() {
		CommandHandler removeHandler = new RemoveCommandHandler();
		commandHandlers.put("remove", removeHandler);
		commandHandlers.put("rm", removeHandler);
	}
	
	private void initCopyCommandHandler() {
		CommandHandler copyHandler = new CopyCommandHandler();
		commandHandlers.put("copy", copyHandler);
		commandHandlers.put("cp", copyHandler);
	}
	
	private void initPasteCommandHandler() {
		CommandHandler pasteHandler = new PasteCommandHandler();
		commandHandlers.put("paste", pasteHandler);
	}
	
	public void executeCommand(String rawComand) {
		UiEditorDialog screen = EditorKernel.getInstance().getMainWindow();
		
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
