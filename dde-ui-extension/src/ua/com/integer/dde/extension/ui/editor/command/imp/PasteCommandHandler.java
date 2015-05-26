package ua.com.integer.dde.extension.ui.editor.command.imp;

import java.io.File;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class PasteCommandHandler implements CommandHandler {
	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		UiConfig temporary = EditorKernel.getInstance().getTemporary();
		if (temporary != null) {
			temporary.saveToFile(new File("tmp.actor"));
			UiConfig toInsert = UiConfig.fromFile(new File("tmp.actor"));
		
			UiConfig selected = EditorKernel.editorScreen().getSelectedConfig();
			if (selected != null) {
				Actor selectedActor = EditorKernel.editorScreen().getSelectedActor();
				if (selectedActor != null && selectedActor instanceof Group) {
					EditorKernel.editorScreen().getSelectedConfig().children.add(toInsert);
					EditorKernel.editorScreen().updateConfig();
					EditorKernel.editorScreen().selectActorByConfig(toInsert);
					new File("tmp.actor").delete();
					EditorKernel.getInstance().getMainWindow().updateActorTree();
				}
			}
		}
	}

	@Override
	public String help() {
		return "";
	}

}
