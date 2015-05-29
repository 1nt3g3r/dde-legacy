package ua.com.integer.dde.extension.ui.editor.property.edit.side;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.Side;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;

public class SideEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = 8272656692182461302L;

	private JComboBox<Side> sideCombobox;
	
	private SideChangeListener sideListener = new SideChangeListener();

	/**
	 * Create the panel.
	 */
	public SideEditPanel() {
		defaultValue = Side.CENTER + "";
		
		sideCombobox = new JComboBox<Side>();
		sideCombobox.addActionListener(sideListener);
		sideCombobox.setModel(new DefaultComboBoxModel<Side>(Side.values()));
		sideCombobox.setBackground(Color.LIGHT_GRAY);
		add(sideCombobox);
	}

	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		if (config != null) {
			sideCombobox.removeActionListener(sideListener);
			sideCombobox.setSelectedItem(config.parentCorner);
			sideCombobox.addActionListener(sideListener);
		}
	}

	class SideChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null) {
				config.parentCorner = Side.valueOf(sideCombobox.getSelectedItem().toString());
				config.targetCorner = Side.valueOf(sideCombobox.getSelectedItem().toString());
				
				if (EditorKernel.editorScreen().getSelectedActor() != null) {
					config.loadPositionFromActor(EditorKernel.editorScreen().getSelectedActor());
				}
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
