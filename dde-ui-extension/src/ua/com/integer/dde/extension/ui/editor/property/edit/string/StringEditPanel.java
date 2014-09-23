package ua.com.integer.dde.extension.ui.editor.property.edit.string;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;

public class StringEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 6656308580516186483L;
	
	private String defaultValue = "";

	protected JTextField propertyValue;
	private JLabel propertyName;
	
	private UiConfig config;
	private String uiPropertyName;

	private PropertyChangeListener listener;

	/**
	 * Create the panel.
	 */
	public StringEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(100, 20));
		propertyName.setMinimumSize(new Dimension(100, 20));
		
		propertyValue = new JTextField();
		propertyValue.setBackground(Color.LIGHT_GRAY);
		propertyValue.setMaximumSize(new Dimension(2147483640, 20));
		propertyValue.setPreferredSize(new Dimension(32000, 20));
		propertyValue.addKeyListener(new KeyTypedListener());
		propertyValue.setColumns(10);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
	
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			propertyValue.setText(config.get(uiPropertyName, getDefaultValue()));
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

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public JLabel getPropertyName() {
		return propertyName;
	}
	
	class KeyTypedListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER && config != null && uiPropertyName != null) {
				saveStringToConfig(propertyValue.getText());
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
	
	protected void saveStringToConfig(String value) {
		config.set(uiPropertyName, value);
	}

	public JTextField getPropertyValue() {
		return propertyValue;
	}
}
