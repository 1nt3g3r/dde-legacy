package ua.com.integer.dde.extension.ui.editor.property.edit.size;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class SizeEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -4171978323826119465L;
	private JComboBox<SizeType> sizeTypeBox;
	private JSpinner multSpinner;
	
	private SizeTypeChangeListener actionListener = new SizeTypeChangeListener();
	private SizeListener valueChangeListener = new SizeListener();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SizeEditPanel() {
		defaultValue = FontUtils.getDefaultFontSize().toString();
		
		sizeTypeBox = new JComboBox();
		sizeTypeBox.addActionListener(actionListener);
		sizeTypeBox.setBackground(Color.LIGHT_GRAY);
		sizeTypeBox.setModel(new DefaultComboBoxModel(SizeType.values()));
		add(sizeTypeBox);
		
		JLabel multLabel = new JLabel("x");
		add(multLabel);
		
		multSpinner = new JSpinner();
		multSpinner.setPreferredSize(new Dimension(60, 20));
		multSpinner.setMinimumSize(new Dimension(60, 20));
		multSpinner.setMaximumSize(new Dimension(60, 20));
		multSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));

		multSpinner.getEditor().setBackground(Color.LIGHT_GRAY);
		multSpinner.setBackground(Color.LIGHT_GRAY);
		add(multSpinner);
	}
	
	protected void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			multSpinner.removeChangeListener(valueChangeListener);
			sizeTypeBox.removeActionListener(actionListener);
			
			Size size = Size.fromString(config.get(uiPropertyName, getDefaultValue()));
			
			sizeTypeBox.setSelectedItem(size.getSizeType());
			multSpinner.setValue(size.getSizeValue());
			
			multSpinner.addChangeListener(valueChangeListener);
			sizeTypeBox.addActionListener(actionListener);
		}
	}
	
	class SizeListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			updateConfigFromUi();
		}

		private void updateConfigFromUi() {
			if (config != null && uiPropertyName != null) {
				writeChangesIntoConfig();
				if (listener != null) {
					listener.propertyChanged();
				}
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
	
	public void setAllowedSizeTypes(SizeType ... types) {
		sizeTypeBox.setModel(new DefaultComboBoxModel<SizeType>(types));
	}
	
	public void setDefaultSize(Size defaultSize) {
		defaultValue = defaultSize.toString();
	}
	
	public JSpinner getMultSpinner() {
		return multSpinner;
	}
}
