package ua.com.integer.dde.extension.config.editor;

import ua.com.integer.dde.startpanel.extension.Extension;


public class ConfigEditorExtension implements Extension {
	@Override
	public void launch() {
		ConfigEditor.main(null);
	}
}
