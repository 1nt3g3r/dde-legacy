package ua.com.integer.dde.extension.ui.editor;

import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;
import ua.com.integer.dde.startpanel.extension.Extension;

/**
 * Extension class to DDE. It allows you to run this UI editor as extension from DDE Start Panel
 * 
 * @author 1nt3g3r
 */
public class UIEditorExtension implements Extension {
	@Override
	public void launch() {
		UiEditorDialog.main(null);
	}
}
