package ua.com.integer.dde.extension.ui.editor.property.edit.size;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SizeEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = -4171978323826119465L;
	@SuppressWarnings("rawtypes")
	private JComboBox sizeTypeBox;
	private JSpinner multSpinner;
	private JLabel propertyName;
	
	private PropertyChangeListener listener;

	private UiConfig config;
	private String uiPropertyName;
	
	private String defaultValue = FontUtils.getDefaultFontSize().toString();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SizeEditPanel() {
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
		
		sizeTypeBox = new JComboBox();
		sizeTypeBox.addActionListener(new SizeTypeChangeListener());
		sizeTypeBox.setBackground(Color.LIGHT_GRAY);
		sizeTypeBox.setModel(new DefaultComboBoxModel(SizeType.values()));
		add(sizeTypeBox);
		
		JLabel multLabel = new JLabel("x");
		add(multLabel);
		
		multSpinner = new JSpinner();
		multSpinner.setPreferredSize(new Dimension(60, 20));
		multSpinner.setMinimumSize(new Dimension(60, 20));
		multSpinner.setMaximumSize(new Dimension(60, 20));
		multSpinner.addChangeListener(new SizeListener());
		multSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
		multSpinner.getEditor().setBackground(Color.LIGHT_GRAY);
		multSpinner.setBackground(Color.LIGHT_GRAY);
		add(multSpinner);
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
	
	protected void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			Size size = Size.fromString(config.get(uiPropertyName, getDefaultValue()));
			
			sizeTypeBox.setSelectedItem(size.getSizeType());
			multSpinner.setValue(size.getSizeValue());
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
		return defaultValue;
	}
	
	class SizeListener implements ActionListener, ChangeListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateConfigFromUi();
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			updateConfigFromUi();
		}

		private void updateConfigFromUi() {
			if (config != null && uiPropertyName != null) {
				writeChangesIntoConfig();
				if (listener != null) listener.propertyChanged();
			}
		}

	}
	
	protected void writeChangesIntoConfig() {
		Size size = getCurrentSize();
		config.set(uiPropertyName, size.toString());
	}

	protected Size getCurrentSize() {
		Size size = new Size();
		size.setSizeValue(Float.parseFloat(multSpinner.getValue().toString()));
		size.setType(SizeType.valueOf(sizeTypeBox.getSelectedItem().toString()));
		return size;
	}
	
	class SizeTypeChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sizeTypeChanged();
		}
	}
	
	protected void sizeTypeChanged() {
		writeChangesIntoConfig();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setAllowedSizeTypes(SizeType ... types) {
		sizeTypeBox.setModel(new DefaultComboBoxModel(types));
	}
	
	public void setDefaultSize(Size defaultSize) {
		defaultValue = defaultSize.toString();
	}
}
