package ua.com.integer.dde.extension.ui.editor.command.imp;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class RemoveCommandHandler implements CommandHandler {

	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		UiEditorScreen editor = EditorKernel.editorScreen();
		
		if (editor.getSelectedConfig() != null) {
			Actor parent = editor.getSelectedActor().getParent();
			
			editor.removeUiConfig(editor.getSelectedConfig());
			
			if (parent != null) {
				editor.selectActor(parent);
			}

			EditorKernel.getInstance().getMainWindow().updateActorTree();
		}
	}

	@Override
	public String help() {
		return "";
	}
}
