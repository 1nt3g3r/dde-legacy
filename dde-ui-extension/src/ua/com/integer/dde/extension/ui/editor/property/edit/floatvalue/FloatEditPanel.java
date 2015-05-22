package ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue;

import javax.swing.SpinnerNumberModel;

import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;

public class FloatEditPanel extends IntegerEditPanel {
	private static final long serialVersionUID = 6214336700528383860L;
	
	public FloatEditPanel() {
		defaultValue = "1.0";
		valueSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
	}
	
	@Override
	protected void setSpinnerValue(String value) {
		valueSpinner.setValue(Float.parseFloat(value));
	}
}
