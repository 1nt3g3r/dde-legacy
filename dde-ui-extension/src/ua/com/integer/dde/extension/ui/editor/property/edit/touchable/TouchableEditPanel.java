package ua.com.integer.dde.extension.ui.editor.property.edit.touchable;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;

import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TouchableEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -2992801321578558963L;
	
	private JComboBox<Touchable> propertyBox;
	
	private TouchableChangeListener touchableListener = new TouchableChangeListener();
	
	/**
	 * Create the panel.
	 */
	public TouchableEditPanel() {
		defaultValue = Touchable.enabled.toString();
		
		propertyBox = new JComboBox<Touchable>();
		propertyBox.setBackground(Color.LIGHT_GRAY);
		propertyBox.addActionListener(touchableListener);
		propertyBox.setModel(new DefaultComboBoxModel<Touchable>(Touchable.values()));
		add(propertyBox);

	}

	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			propertyBox.removeActionListener(touchableListener);
			propertyBox.setSelectedItem(Touchable.valueOf(config.get(uiPropertyName, getDefaultValue())));
			propertyBox.addActionListener(touchableListener);
		}
	}
	
	class TouchableChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config.get(uiPropertyName, getDefaultValue()).equals(propertyBox.getSelectedItem().toString())) return;
			
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, propertyBox.getSelectedItem().toString());
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
