package ua.com.integer.dde.extension.ui.editor;

import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;

public class EditorKernel extends DDKernel {
	private UiEditorDialog actorListDialog;

	private static EditorKernel instance = new EditorKernel();
	
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
	
	public UiEditorDialog getActorListDialog() {
		return actorListDialog;
	}
	
	@Override
	public void create() {
		super.create();
		showScreen(UiEditorScreen.class);
	}
	
	public static UiEditorScreen editorScreen() {
		return instance.getScreen(UiEditorScreen.class);
	}
}
