package ua.com.integer.dde.extension.ui.editor.property.edit.bool;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import java.awt.Color;

public class BooleanEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 241676505870111294L;

	private JCheckBox propertyValue;
	
	private String defaultValue = "true";
	
	private PropertyChangeListener listener;

	private UiConfig config;
	private String uiPropertyName;
	private JLabel propertyName;

	public BooleanEditPanel() {
		setBackground(Color.GRAY);
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setPreferredSize(new Dimension(300, 20));
		
		propertyValue = new JCheckBox("Property name");
		propertyValue.setBackground(Color.GRAY);
		propertyValue.addActionListener(new CheckboxClickListener());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		propertyName = new JLabel("Property name:");
		propertyName.setMaximumSize(new Dimension(97, 20));
		propertyName.setPreferredSize(new Dimension(97, 20));
		propertyName.setMinimumSize(new Dimension(97, 20));
		add(propertyName);
		add(propertyValue);

	}
	
	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		updateUIFromConfig();
	}
	
	@Override
	public void setUiPropertyName(String propertyName) {
		this.uiPropertyName = propertyName;
		updateUIFromConfig();
	}
	
	private void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			propertyValue.setSelected(Boolean.parseBoolean(config.get(uiPropertyName, getDefaultValue())));
		}
		
		propertyValue.setText(propertyValue.isSelected() + "");
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		this.propertyName.setText(propertyName);
	}
	
	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String getDefaultValue() {
		return defaultValue;
	}
	
	class CheckboxClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config.get(uiPropertyName, getDefaultValue()).equals(propertyValue.isSelected() + "")) return;
			
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, propertyValue.isSelected() + "");
				
				if (listener != null) listener.propertyChanged();
			}
			
			propertyValue.setText(propertyValue.isSelected() + "");
		}
	}
	public JLabel getPropertyName() {
		return propertyName;
	}
}
