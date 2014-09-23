package ua.com.integer.dde.extension.config.editor.property;

import javax.swing.SpinnerNumberModel;

public class FloatPropertyEditor extends IntegerPropertyEditor {
	private static final long serialVersionUID = 8421558820117207918L;

	public FloatPropertyEditor() {
		valueSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.1f)));
		setTitle("Float property editor");
	}
	
	@Override
	public void setValue(String value) {
		try {
			valueSpinner.setValue(Float.parseFloat(value));
		} catch (IllegalArgumentException ex) {
			valueSpinner.setValue(0f);
		}
	}

}
