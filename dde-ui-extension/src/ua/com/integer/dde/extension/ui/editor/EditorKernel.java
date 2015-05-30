package ua.com.integer.dde.extension.ui.editor;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.command.CommandProcessor;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;

public class EditorKernel extends DDKernel {
	private static EditorKernel instance = new EditorKernel();

	private UiEditorDialog actorListDialog;
	private CommandProcessor commandProcessor;
	
	private UiConfig temporary;
	
	private EditorKernel() {
		if (ProjectFinder.findAndroidProject() != null) {
			getConfig().relativeDirectory = ProjectFinder.findAndroidProject() + "/assets/";
		}
	}
	
	public static EditorKernel getInstance() {
		return instance;
	}
	
	public void setActorListDialog(UiEditorDialog dialog) {
		this.actorListDialog = dialog;
	}
	
	public UiEditorDialog getMainWindow() {
		return actorListDialog;
	}
	
	@Override
	public void create() {
		super.create();
		commandProcessor = new CommandProcessor();
	}
	
	public static UiEditorScreen editorScreen() {
		return instance.getScreen(UiEditorScreen.class);
	}
	
	public static void executeCommand(String command) {
		instance.commandProcessor.executeCommand(command);
	}
	
	public void setTemporary(UiConfig temporary) {
		this.temporary = temporary;
	}
	
	public UiConfig getTemporary() {
		return temporary;
	}
}
