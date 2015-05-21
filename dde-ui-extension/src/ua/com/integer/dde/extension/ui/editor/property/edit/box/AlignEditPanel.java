package ua.com.integer.dde.extension.ui.editor.property.edit.box;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.actor.Box.Align;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledBaseEditPanel;

public class AlignEditPanel extends LabeledBaseEditPanel {
	private static final long serialVersionUID = 1871482774965698728L;
	
	private JComboBox<Align> alignBox;

	/**
	 * Create the panel.
	 */
	public AlignEditPanel() {
		alignBox = new JComboBox<Align>();
		alignBox.addActionListener(new AlignSelectListener());
		alignBox.setModel(new DefaultComboBoxModel<Align>(Align.values()));
		alignBox.setBackground(Color.LIGHT_GRAY);
		add(alignBox);
		setDefaultValue(Align.CENTER.toString());
	}

	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		updateUiFromConfig();
	}

	private void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			alignBox.setSelectedItem(Align.valueOf(config.get(uiPropertyName, getDefaultValue())));
		}
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		setLabel(propertyName);
	}

	class AlignSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, alignBox.getSelectedItem().toString());
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
