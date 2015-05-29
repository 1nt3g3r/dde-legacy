package ua.com.integer.dde.extension.ui.editor.property.edit.font;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledBaseEditPanel;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;

public class FontNameEditPanel extends LabeledBaseEditPanel {
	private static final long serialVersionUID = 5635076338216542087L;

	private JComboBox<String> fontNameBox;

	private FontNameChangeListener fontChangeListener = new FontNameChangeListener();
	/**
	 * Create the panel.
	 */
	public FontNameEditPanel() {
		defaultValue = "standard";
		
		fontNameBox = new JComboBox<String>();
		fontNameBox.addActionListener(fontChangeListener);
		fontNameBox.setBackground(Color.LIGHT_GRAY);
		add(fontNameBox);

	}

	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			fontNameBox.removeActionListener(fontChangeListener);

			fontNameBox.setModel(new DefaultComboBoxModel<String>(FontUtils.getFontNamesWithStandard()));
			fontNameBox.setSelectedItem(config.get(uiPropertyName, getDefaultValue()));
		
			fontNameBox.addActionListener(fontChangeListener);
		}
	}
	
	class FontNameChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, fontNameBox.getSelectedItem().toString());
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
