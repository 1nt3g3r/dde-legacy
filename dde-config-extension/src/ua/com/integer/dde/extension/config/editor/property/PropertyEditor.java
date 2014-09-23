package ua.com.integer.dde.extension.config.editor.property;

import java.awt.event.ActionListener;

public interface PropertyEditor {
	public void setEditListener(ActionListener editListener);
	public void setValue(String value);
	public String getValue();
	public void showEditor();
}
