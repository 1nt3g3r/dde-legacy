package ua.com.integer.dde.extension.ui.editor.property.edit.bool;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.BaseEditPanel;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.SwingConstants;

public class BooleanEditPanel extends BaseEditPanel {
	private static final long serialVersionUID = 241676505870111294L;

	private JCheckBox propertyValue;
	
	public BooleanEditPanel() {
		setDefaultValue("true");
		propertyValue = new JCheckBox("Property name");
		propertyValue.setHorizontalAlignment(SwingConstants.LEFT);
		propertyValue.setBackground(Color.GRAY);
		propertyValue.addActionListener(new CheckboxClickListener());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(propertyValue);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
	}
	
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			propertyValue.setSelected(Boolean.parseBoolean(config.get(uiPropertyName, getDefaultValue())));
		}
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		propertyValue.setText(propertyName);
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	class CheckboxClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config.get(uiPropertyName, getDefaultValue()).equals(propertyValue.isSelected() + "")) return;
			
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, propertyValue.isSelected() + "");
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
