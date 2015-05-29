package ua.com.integer.dde.extension.ui.editor.property.edit.builder;

import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.BaseEditPanel;

public class EditComponentBuilder {
	protected String propertyName = "";
	protected String uiPropertyName = "";
	protected String defaultValue = "";
	
	protected BaseEditPanel editPanel;
	
	protected PropertyChangeListener changeListener;
	
	public EditComponentBuilder(BaseEditPanel panel) {
		init(panel);
	}
	
	public EditComponentBuilder changeListener(PropertyChangeListener changeListener) {
		this.changeListener = changeListener;
		return this;
	}
	
	public EditComponentBuilder title(String propertyName) {
		this.propertyName = propertyName;
		init(editPanel);
		return this;
	}
	
	public EditComponentBuilder paramName(String param) {
		uiPropertyName = param;
		init(editPanel);
		return this;
	}
	
	public EditComponentBuilder defaultValue(Object defaultValue) {
		this.defaultValue = defaultValue.toString();
		init(editPanel);
		return this;
	}
	
	public EditComponentBuilder init(BaseEditPanel editPanel) {
		this.editPanel = editPanel;
		
		if (editPanel != null) {
			editPanel.setUiPropertyName(uiPropertyName);
			editPanel.setPropertyName(propertyName);
			editPanel.setDefaultValue(defaultValue);
		}

		return this;
	}
	
	public EditComponentBuilder setup(String title, String paramName, Object defaultValue) {
		return title(title).paramName(paramName).defaultValue(defaultValue);
	}
	
	public BaseEditPanel build() {
		editPanel.setPropertyChangedListener(changeListener);
		return editPanel;
	};
}
