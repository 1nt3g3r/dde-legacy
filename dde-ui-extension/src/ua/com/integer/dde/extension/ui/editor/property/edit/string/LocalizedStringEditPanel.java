package ua.com.integer.dde.extension.ui.editor.property.edit.string;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.localize.Localize;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.startpanel.FrameTools;

public class LocalizedStringEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = 7823894230864943611L;
	
	private JTextField textfield;
	private JComboBox tagCombobox;
	private JCheckBox needLocalize;
	private JLabel propertyName;

	private String uiPropertyName;
	private UiConfig config;
	
	private PropertyChangeListener listener;
	
	public LocalizedStringEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(97, 20));
		propertyName.setMinimumSize(new Dimension(97, 20));
		propertyName.setMaximumSize(new Dimension(97, 20));
		add(propertyName);
		
		needLocalize = new JCheckBox("Localize");
		needLocalize.addActionListener(new CheckNeedLocalizeListener());
		needLocalize.setBackground(Color.GRAY);
		add(needLocalize);
		
		textfield = new JTextField();
		textfield.addKeyListener(new TextEnterListener());
		textfield.setBackground(Color.LIGHT_GRAY);
		textfield.setText("Text");
		add(textfield);
		textfield.setColumns(10);
		
		tagCombobox = new JComboBox();
		tagCombobox.addActionListener(new TagSelectedListener());
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
		
		config.set(uiPropertyName + "-localized-tag", tagCombobox.getSelectedItem().toString());
		config.set(uiPropertyName + "-localize", "true");
	}
	
	private void updateTagList() {
		tagCombobox.setModel(new DefaultComboBoxModel(Localize.getInstance().getTags()));
	}
	
	public static void main(String[] args) {
		FrameTools.testingFrame(new LocalizedStringEditPanel());
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
			if (config.get(uiPropertyName + "-localize", "false").equals("true")) {
				updateTagList();
				tagCombobox.setSelectedItem(config.get(uiPropertyName + "-localized-tag"));
				textfield.setVisible(false);
				tagCombobox.setVisible(true);
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
		return "";
	}
}
