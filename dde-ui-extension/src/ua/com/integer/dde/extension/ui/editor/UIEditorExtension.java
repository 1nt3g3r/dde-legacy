package ua.com.integer.dde.extension.ui.editor;

import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;
import ua.com.integer.dde.startpanel.extension.Extension;

public class UIEditorExtension implements Extension {
	@Override
	public void launch() {
		UiEditorDialog.main(null);
	}
}
