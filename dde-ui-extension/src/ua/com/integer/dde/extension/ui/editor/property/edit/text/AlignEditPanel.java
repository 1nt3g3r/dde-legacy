package ua.com.integer.dde.extension.ui.editor.property.edit.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.actor.Align;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;

public class AlignEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 13348312730673242L;
	
	private JLabel propertyName;
	private JComboBox alignValue;

	private UiConfig config;
	private String uiPropertyName;
	private PropertyChangeListener listener;

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
		
		alignValue = new JComboBox();
		alignValue.addActionListener(new AlignSelectListener());
		alignValue.setBackground(Color.LIGHT_GRAY);
		alignValue.setModel(new DefaultComboBoxModel(Align.values()));
		add(alignValue);

	}

	public JLabel getPropertyName() {
		return propertyName;
	}
	public JComboBox getAlignValue() {
		return alignValue;
	}
	
	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		updateUiFromConfig();
	}

	@Override
	public void setUiPropertyName(String propertyName) {
		uiPropertyName = propertyName;
		
		updateUiFromConfig();
	}
	
	private void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			alignValue.setSelectedItem(Align.valueOf(config.get(uiPropertyName, getDefaultValue())));
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
	
	@Override
	public String getDefaultValue() {
		return Align.CENTER + "";
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
