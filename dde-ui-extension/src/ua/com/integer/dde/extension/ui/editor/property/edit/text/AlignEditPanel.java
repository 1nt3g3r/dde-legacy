package ua.com.integer.dde.extension.ui.editor.property.edit.text;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.actor.Align;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;

public class AlignEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = 13348312730673242L;
	private JComboBox<Align> alignValue;
	private AlignSelectListener alignListener = new AlignSelectListener();

	/**
	 * Create the panel.
	 */
	public AlignEditPanel() {
		setDefaultValue(Align.CENTER.toString());
		
		alignValue = new JComboBox<Align>();
		alignValue.addActionListener(alignListener);
		alignValue.setBackground(Color.LIGHT_GRAY);
		alignValue.setModel(new DefaultComboBoxModel<Align>(Align.values()));
		add(alignValue);
	}
	
	@Override
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			alignValue.removeActionListener(alignListener);
			alignValue.setSelectedItem(Align.valueOf(config.get(uiPropertyName, getDefaultValue())));
			alignValue.addActionListener(alignListener);
		}
	}
	
	class AlignSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, alignValue.getSelectedItem().toString());
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
