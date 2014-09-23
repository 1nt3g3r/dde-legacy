package ua.com.integer.dde.extension.ui.editor.property.edit.touchable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import java.awt.Color;

public class TouchableEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = -2992801321578558963L;
	
	private JComboBox propertyBox;
	private JLabel propertyName;

	private UiConfig config;
	private String uiPropertyName;
	
	private PropertyChangeListener listener;
	
	/**
	 * Create the panel.
	 */
	public TouchableEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(100, 20));
		add(propertyName);
		
		propertyBox = new JComboBox();
		propertyBox.setBackground(Color.LIGHT_GRAY);
		propertyBox.addActionListener(new TouchableChangeListener());
		propertyBox.setModel(new DefaultComboBoxModel(Touchable.values()));
		add(propertyBox);

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
			propertyBox.setSelectedItem(Touchable.valueOf(config.get(uiPropertyName, getDefaultValue())));
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
		return Touchable.enabled.toString();
	}
}
