package ua.com.integer.dde.extension.ui.editor.property.edit.font;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;

public class FontNameEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 5635076338216542087L;

	private UiConfig config;
	private String uiPropertyName;
	private PropertyChangeListener listener;
	@SuppressWarnings("rawtypes")
	private JComboBox fontNameBox;
	private JLabel propertyName;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("rawtypes")
	public FontNameEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(100, 20));
		propertyName.setMinimumSize(new Dimension(100, 20));
		propertyName.setMaximumSize(new Dimension(100, 20));
		add(propertyName);
		
		fontNameBox = new JComboBox();
		fontNameBox.addActionListener(new FontNameChangeListener());
		fontNameBox.setBackground(Color.LIGHT_GRAY);
		add(fontNameBox);

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			fontNameBox.setModel(new DefaultComboBoxModel(FontUtils.getFontNamesWithStandard()));

			fontNameBox.setSelectedItem(config.get(uiPropertyName, getDefaultValue()));
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
		return "standard";
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
