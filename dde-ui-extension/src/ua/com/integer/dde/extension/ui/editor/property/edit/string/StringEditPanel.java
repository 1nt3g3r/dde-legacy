package ua.com.integer.dde.extension.ui.editor.property.edit.string;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;

public class StringEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = 6656308580516186483L;
	
	protected JTextField propertyValue;

	/**
	 * Create the panel.
	 */
	public StringEditPanel() {
		propertyValue = new JTextField();
		propertyValue.setBackground(Color.LIGHT_GRAY);
		propertyValue.setMaximumSize(new Dimension(2147483640, 20));
		propertyValue.setPreferredSize(new Dimension(32000, 20));
		propertyValue.addKeyListener(new KeyTypedListener());
		propertyValue.setColumns(10);
		
		add(propertyValue);
	}
	
	
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			propertyValue.setText(config.get(uiPropertyName, getDefaultValue()));
		}
	}
	
	class KeyTypedListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER && config != null && uiPropertyName != null) {
				saveStringToConfig(propertyValue.getText());
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
	
	protected void saveStringToConfig(String value) {
		config.set(uiPropertyName, value);
	}
	
	public JTextField getPropertyValue() {
		return propertyValue;
	}
}
