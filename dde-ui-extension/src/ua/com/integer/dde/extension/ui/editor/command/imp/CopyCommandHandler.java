package ua.com.integer.dde.extension.ui.editor.command.imp;

import java.io.File;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class CopyCommandHandler implements CommandHandler {
	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		UiConfig temporary = EditorKernel.editorScreen().getSelectedConfig();
		if (temporary != null) {
			temporary.saveToFile(new File("tmp.actor"));
			EditorKernel.getInstance().setTemporary(UiConfig.fromFile(new File("tmp.actor")));
			new File("tmp.actor").delete();		
		}
	}

	@Override
	public String help() {
		return "";
	}
}
