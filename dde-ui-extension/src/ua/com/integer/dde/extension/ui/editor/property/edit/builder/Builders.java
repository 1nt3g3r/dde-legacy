package ua.com.integer.dde.extension.ui.editor.property.edit.builder;

import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;


public class Builders {
	private PropertyChangeListener changeListener;

	public Builders(PropertyChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
	public BooleanBuilder bool() {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.changeListener(changeListener);
		return booleanBuilder;
	}
}
