package ua.com.integer.dde.extension.ui.editor.property.edit.side;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import ua.com.integer.dde.extension.ui.Side;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;

public class SideEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 8272656692182461302L;

	private JLabel propertyName;
	
	private UiConfig config;
	private PropertyChangeListener listener;
	@SuppressWarnings("rawtypes")
	private JComboBox sideCombobox;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SideEditPanel() {
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
		
		sideCombobox = new JComboBox();
		sideCombobox.addActionListener(new SideChangeListener());
		sideCombobox.setModel(new DefaultComboBoxModel(Side.values()));
		sideCombobox.setBackground(Color.LIGHT_GRAY);
		add(sideCombobox);
	}

	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		if (config != null) {
			sideCombobox.setSelectedItem(config.parentCorner);
		}
	}

	@Override
	public void setUiPropertyName(String propertyName) {
		
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
		return Side.CENTER + "";
	}
	
	class SideChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null) {
				config.parentCorner = Side.valueOf(sideCombobox.getSelectedItem().toString());
				config.targetCorner = Side.valueOf(sideCombobox.getSelectedItem().toString());
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
