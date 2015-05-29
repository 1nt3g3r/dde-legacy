package ua.com.integer.dde.extension.ui.editor.property.edit.integer;


import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;
import ua.com.integer.dde.startpanel.FrameTools;

public class IntegerEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -5563346076050032646L;

	protected JSpinner valueSpinner;
	protected IntegerValueChangeListener changeListener = new IntegerValueChangeListener();

	/**
	 * Create the panel.
	 */
	public IntegerEditPanel() {
		defaultValue = "0";
		
		valueSpinner = new JSpinner();
		valueSpinner.setForeground(Color.LIGHT_GRAY);
		valueSpinner.setBackground(Color.LIGHT_GRAY);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(valueSpinner);
	}
	
	protected void setSpinnerValue(String value) {
		valueSpinner.removeChangeListener(changeListener);
		valueSpinner.setValue((int) Float.parseFloat(value));
		valueSpinner.addChangeListener(changeListener);
	}
	
	@Override
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			setSpinnerValue(config.get(uiPropertyName, getDefaultValue()));
		}
	}
	
	
	class IntegerValueChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (config != null && uiPropertyName != null) {
				if(!config.get(uiPropertyName, getDefaultValue()).equals(valueSpinner.getValue().toString())) {
					config.set(uiPropertyName, valueSpinner.getValue() + "");
					if (listener != null) listener.propertyChanged();
				}
			}
		}
	}
	
	public JSpinner getValueSpinner() {
		return valueSpinner;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				IntegerEditPanel panel = new IntegerEditPanel();
				panel.setConfig(new UiConfig());
				panel.setUiPropertyName("test-ing");
				panel.setPropertyName("Rotation");
				
				FrameTools.testingFrame(panel);
			}
		});
	}
}
