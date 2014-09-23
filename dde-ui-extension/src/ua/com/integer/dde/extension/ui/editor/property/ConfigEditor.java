package ua.com.integer.dde.extension.ui.editor.property;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;

public abstract class ConfigEditor extends JPanel {
	private static final long serialVersionUID = 1026628646395670062L;

	public abstract void setConfig(UiConfig config);
}
