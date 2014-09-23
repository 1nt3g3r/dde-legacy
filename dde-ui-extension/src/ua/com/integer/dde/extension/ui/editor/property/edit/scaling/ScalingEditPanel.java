package ua.com.integer.dde.extension.ui.editor.property.edit.scaling;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;

import com.badlogic.gdx.utils.Scaling;

public class ScalingEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = -9219486169697324844L;

	private JLabel propertyName;
	private JComboBox valueField;
	
	private UiConfig config;
	private String uiPropertyName;
	private PropertyChangeListener listener;

	/**
	 * Create the panel.
	 */
	public ScalingEditPanel() {
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
		
		valueField = new JComboBox();
		valueField.addActionListener(new ScalingSelectListener());
		valueField.setBackground(Color.LIGHT_GRAY);
		valueField.setModel(new DefaultComboBoxModel(Scaling.values()));
		add(valueField);

	}

	public JLabel getPropertyName() {
		return propertyName;
	}
	public JComboBox getValueField() {
		return valueField;
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
			valueField.setSelectedItem(Scaling.valueOf(config.get(uiPropertyName, getDefaultValue())));
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
		return Scaling.none.toString();
	}
	
	class ScalingSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, valueField.getSelectedItem().toString());
				
				if (listener != null) listener.propertyChanged();
			}
		} 
	}
	
}
