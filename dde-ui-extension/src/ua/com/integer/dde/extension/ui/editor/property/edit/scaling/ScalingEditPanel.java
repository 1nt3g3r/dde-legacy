package ua.com.integer.dde.extension.ui.editor.property.edit.scaling;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledBaseEditPanel;

import com.badlogic.gdx.utils.Scaling;

public class ScalingEditPanel extends LabeledBaseEditPanel {
	private static final long serialVersionUID = -9219486169697324844L;
	private JComboBox<Scaling> valueField;
	
	/**
	 * Create the panel.
	 */
	public ScalingEditPanel() {
		defaultValue = Scaling.none.toString();
		
		valueField = new JComboBox<Scaling>();
		valueField.addActionListener(new ScalingSelectListener());
		valueField.setBackground(Color.LIGHT_GRAY);
		valueField.setModel(new DefaultComboBoxModel<Scaling>(Scaling.values()));
		add(valueField);
	}
	
	@Override
	protected void updateUIFromConfig() {
		if (config != null) {
			String scalingStr = config.get(uiPropertyName, getDefaultValue());
			Scaling scaling = Scaling.valueOf(scalingStr);
			valueField.setSelectedItem(scaling);
		}
	}
	
	class ScalingSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, valueField.getSelectedItem().toString());
				
				if (listener != null) listener.propertyChanged();
			}
		} 
	}
}
