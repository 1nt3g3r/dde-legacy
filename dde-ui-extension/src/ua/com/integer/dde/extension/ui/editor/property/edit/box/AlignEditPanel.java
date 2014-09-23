package ua.com.integer.dde.extension.ui.editor.property.edit.box;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.actor.Box.Align;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;

public class AlignEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 1871482774965698728L;
	private JComboBox alignBox;
	private JLabel propertyName;
	
	private PropertyChangeListener listener;

	private UiConfig config;
	private String uiPropertyName;

	/**
	 * Create the panel.
	 */
	public AlignEditPanel() {
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(100, 20));
		propertyName.setMinimumSize(new Dimension(100, 20));
		propertyName.setMaximumSize(new Dimension(100, 20));
		add(propertyName);
		
		alignBox = new JComboBox();
		alignBox.addActionListener(new AlignSelectListener());
		alignBox.setModel(new DefaultComboBoxModel(Align.values()));
		alignBox.setBackground(Color.LIGHT_GRAY);
		add(alignBox);
	}

	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		updateUiFromConfig();
	}

	@Override
	public void setUiPropertyName(String propertyName) {
		this.uiPropertyName = propertyName;
		
		updateUiFromConfig();
	}

	private void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			alignBox.setSelectedItem(Align.valueOf(config.get(uiPropertyName, getDefaultValue())));
		}
	}
	@Override
	public void setPropertyName(String propertyName) {
		this.propertyName.setText(propertyName);
	}

	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
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

	@Override
	public String getDefaultValue() {
		return Align.CENTER.toString();
	}
}
