package ua.com.integer.dde.extension.ui.editor.command.imp;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;

public class AlignActorCommandHandler implements CommandHandler {
	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		if (arguments.length <= 0) {
			screen.setConsoleOutput(help());
		} else {
			String mode = arguments[0];
			UiConfig selected = EditorKernel.editorScreen().getSelectedConfig();
			Actor actor = EditorKernel.editorScreen().getSelectedActor();
			Group parent;
			if (selected == null) {
				screen.setConsoleOutput("Select actor to apply this command!");
			} else {
				parent = actor.getParent();
				if (mode.equals("horizontally") || mode.equals("h")) {
					float freeHorizontalSpace = parent.getWidth() - actor.getWidth();
					float dstX = freeHorizontalSpace/2f;
					actor.setX(dstX);
					selected.loadPositionFromActor(actor);
				} else if (mode.equalsIgnoreCase("vertically") || mode.equals("v")) {
					float freeVerticalSpace = parent.getHeight() - actor.getHeight();
					float dstY = freeVerticalSpace/2f;
					actor.setY(dstY);
					selected.loadPositionFromActor(actor);
				} else if (mode.equals("both") || mode.equals("b")) {
					float freeHorizontalSpace = parent.getWidth() - actor.getWidth();
					float dstX = freeHorizontalSpace/2f;
					actor.setX(dstX);
					selected.loadPositionFromActor(actor);
					
					float freeVerticalSpace = parent.getHeight() - actor.getHeight();
					float dstY = freeVerticalSpace/2f;
					actor.setY(dstY);
					selected.loadPositionFromActor(actor);
				}
			}
		}
	}

	@Override
	public String help() {
		return "(al)ign <mode>\n"
				+ "Modes: (h)orizontally, (v)ertically, (b)oth";
	}
}
