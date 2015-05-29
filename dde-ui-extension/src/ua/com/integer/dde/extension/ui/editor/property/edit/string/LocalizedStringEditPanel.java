package ua.com.integer.dde.extension.ui.editor.property.edit.string;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import ua.com.integer.dde.extension.localize.Localize;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;
import ua.com.integer.dde.startpanel.FrameTools;

public class LocalizedStringEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = 7823894230864943611L;
	
	private JTextField textfield;
	private JComboBox<String> tagCombobox;
	private JCheckBox needLocalize;
	
	private CheckNeedLocalizeListener needLocalizeListener = new CheckNeedLocalizeListener();
	private TagSelectedListener tagListener = new TagSelectedListener();
	
	public LocalizedStringEditPanel() {
		defaultValue = "";
		
		needLocalize = new JCheckBox("Localize");
		needLocalize.addActionListener(needLocalizeListener);
		needLocalize.setBackground(Color.GRAY);
		add(needLocalize);
		
		textfield = new JTextField();
		textfield.addKeyListener(new TextEnterListener());
		textfield.setBackground(Color.LIGHT_GRAY);
		textfield.setText("Text");
		add(textfield);
		textfield.setColumns(10);
		
		tagCombobox = new JComboBox<String>();
		tagCombobox.addActionListener(tagListener);
		tagCombobox.setBackground(Color.LIGHT_GRAY);
		tagCombobox.setVisible(false);
		add(tagCombobox);
	}
	
	class CheckNeedLocalizeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			textfield.setVisible(!needLocalize.isSelected());
			tagCombobox.setVisible(needLocalize.isSelected());
			
			if (!needLocalize.isSelected() && config != null && uiPropertyName != null) {
				config.set(uiPropertyName + "-localize", "false");
				config.set(uiPropertyName, textfield.getText());
			} else {
				updateTagList();
				localizeText();
			}
			
			if (listener != null) listener.propertyChanged();
		}
	}
	
	private void localizeText() {
		if (!config.properties.containsKey(uiPropertyName)) {
			config.set(uiPropertyName, getDefaultValue());
		}
		
		if (tagCombobox.getSelectedIndex() >= 0) {
			config.set(uiPropertyName + "-localized-tag", tagCombobox.getSelectedItem().toString());
			config.set(uiPropertyName + "-localize", "true");
		} else {
			needLocalize.setSelected(false);
			textfield.setVisible(true);
			tagCombobox.setVisible(false);
		}
	}
	
	private void updateTagList() {
		tagCombobox.setModel(new DefaultComboBoxModel<String>(Localize.getInstance().getTags()));
	}
	
	public static void main(String[] args) {
		FrameTools.testingFrame(new LocalizedStringEditPanel());
	}

	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			if (config.get(uiPropertyName + "-localize", "false").equals("true")) {
				tagCombobox.removeActionListener(tagListener);
				
				updateTagList();
				tagCombobox.setSelectedItem(config.get(uiPropertyName + "-localized-tag"));
				textfield.setVisible(false);
				tagCombobox.setVisible(true);
				
				tagCombobox.addActionListener(tagListener);
			} else {
				textfield.setText(config.get(uiPropertyName, getDefaultValue()));
				textfield.setVisible(true);
				tagCombobox.setVisible(false);
			}
		}
	}
	
	class TextEnterListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (config != null && uiPropertyName != null) {
					config.set(uiPropertyName, textfield.getText());
					
					if (listener != null) listener.propertyChanged();
				}
			}
		}
	}
	
	class TagSelectedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				localizeText();
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
}
