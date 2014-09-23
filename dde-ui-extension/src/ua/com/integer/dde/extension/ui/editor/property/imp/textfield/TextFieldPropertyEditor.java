package ua.com.integer.dde.extension.ui.editor.property.imp.textfield;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.font.FontNameEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.SizeType;
import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.LocalizedStringEditPanel;
import ua.com.integer.dde.extension.ui.editor.utils.ColorUtils;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.StringEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;

public class TextFieldPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = -2745808385597543502L;
	private FontNameEditPanel fontNamePanel;
	private SizeEditPanel fontSizePanel;
	private ColorEditPanel fontColor;
	private LocalizedStringEditPanel initialText;
	private LocalizedStringEditPanel messageText;
	private ColorEditPanel messageTextColor;
	private ColorEditPanel focusedTextColor;
	private ColorEditPanel disabledTextColor;
	private BooleanEditPanel disabledTextfield;
	private SizeEditPanel messageFontSize;
	private FontNameEditPanel messageFontName;
	private DrawableEditPanel backgroundDrawable;
	private DrawableEditPanel cursorDrawable;
	private DrawableEditPanel disabledBackgroundDrawable;
	private DrawableEditPanel selectionDrawable;
	private DrawableEditPanel focusedBackgroundDrawable;
	private BooleanEditPanel passwordMode;
	private StringEditPanel passwordChar;
	private IntegerEditPanel maxTextLength;
	private BooleanEditPanel fontOnlyChars;
	private BooleanEditPanel rightAlign;

	public TextFieldPropertyEditor() {
		setPreferredSize(new Dimension(310, 600));
		setMinimumSize(new Dimension(310, 600));
		setMaximumSize(new Dimension(310, 600));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.GRAY);
		messagePanel.setPreferredSize(new Dimension(300, 20));
		messagePanel.setMaximumSize(new Dimension(300, 20));
		messagePanel.setMinimumSize(new Dimension(30, 20));
		add(messagePanel);
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		messagePanel.add(horizontalStrut);
		
		JLabel lblTextfieldProperties = new JLabel("Textfield properties:");
		lblTextfieldProperties.setForeground(Color.GREEN);
		messagePanel.add(lblTextfieldProperties);
		
		initialText = new LocalizedStringEditPanel();
		initialText.setPropertyChangedListener(this);
		initialText.setUiPropertyName("text");
		initialText.setPropertyName("Text:");
		add(initialText);
		
		Component verticalStrut_18 = Box.createVerticalStrut(2);
		add(verticalStrut_18);
		
		fontNamePanel = new FontNameEditPanel();
		fontNamePanel.setPropertyChangedListener(this);
		fontNamePanel.setPropertyName("Font name:");
		fontNamePanel.setUiPropertyName("font");
		add(fontNamePanel);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		fontSizePanel = new SizeEditPanel();
		fontSizePanel.setPropertyChangedListener(this);
		fontSizePanel.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		fontSizePanel.setDefaultSize(FontUtils.getDefaultFontSize());
		fontSizePanel.setPropertyName("Font size:");
		fontSizePanel.setUiPropertyName("font-size");
		add(fontSizePanel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(5);
		add(verticalStrut_2);
		
		fontOnlyChars = new BooleanEditPanel();
		fontOnlyChars.setPropertyChangedListener(this);
		fontOnlyChars.setUiPropertyName("font-only-chars");
		fontOnlyChars.setPropertyName("Font chars:");
		add(fontOnlyChars);
		
		Component verticalStrut_5 = Box.createVerticalStrut(2);
		add(verticalStrut_5);
		
		fontColor = new ColorEditPanel();
		fontColor.setPropertyChangedListener(this);
		fontColor.setUiPropertyName("font-color");
		fontColor.setPropertyName("Text color:");
		add(fontColor);
		
		Component verticalStrut_3 = Box.createVerticalStrut(2);
		add(verticalStrut_3);
		
		maxTextLength = new IntegerEditPanel();
		maxTextLength.setPropertyChangedListener(this);
		maxTextLength.setUiPropertyName("max-text-length");
		maxTextLength.setDefaultValue("10");
		maxTextLength.setPropertyName("Max text length:");
		add(maxTextLength);
		
		Component verticalStrut_17 = Box.createVerticalStrut(2);
		add(verticalStrut_17);
		
		focusedTextColor = new ColorEditPanel();
		focusedTextColor.setPropertyChangedListener(this);
		focusedTextColor.setUiPropertyName("focused-text-color");
		focusedTextColor.setPropertyName("Focused color:");
		add(focusedTextColor);
		
		Component verticalStrut_7 = Box.createVerticalStrut(2);
		focusedTextColor.add(verticalStrut_7);
		
		JLabel lblDisabled = new JLabel("Disabled:");
		lblDisabled.setForeground(Color.GREEN);
		add(lblDisabled);
		
		disabledTextfield = new BooleanEditPanel();
		disabledTextfield.setPropertyChangedListener(this);
		disabledTextfield.setUiPropertyName("textfield-disabled");
		disabledTextfield.setDefaultValue("false");
		disabledTextfield.setPropertyName("Disabled:");
		add(disabledTextfield);
		
		Component verticalStrut_6 = Box.createVerticalStrut(5);
		add(verticalStrut_6);
		
		disabledTextColor = new ColorEditPanel();
		disabledTextColor.setPropertyChangedListener(this);
		disabledTextColor.setUiPropertyName("disabled-text-color");
		disabledTextColor.setPropertyName("Disabled color:");
		add(disabledTextColor);
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		add(verticalStrut_1);
		
		JLabel lblMessageText = new JLabel("Message text:");
		lblMessageText.setForeground(Color.GREEN);
		add(lblMessageText);
		
		messageText = new LocalizedStringEditPanel();
		messageText.setPropertyChangedListener(this);
		messageText.setPropertyName("Message text:");
		messageText.setUiPropertyName("message-text");
		add(messageText);
		
		Component verticalStrut_10 = Box.createVerticalStrut(2);
		add(verticalStrut_10);
		
		messageFontName = new FontNameEditPanel();
		messageFontName.setPropertyChangedListener(this);
		messageFontName.setUiPropertyName("message-font");
		messageFontName.setPropertyName("Msg font name:");
		add(messageFontName);
		
		Component verticalStrut_9 = Box.createVerticalStrut(2);
		add(verticalStrut_9);
		
		messageFontSize = new SizeEditPanel();
		messageFontSize.setPropertyChangedListener(this);
		messageFontSize.setDefaultSize(FontUtils.getDefaultFontSize());
		messageFontSize.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		messageFontSize.setUiPropertyName("message-font-size");
		messageFontSize.setPropertyName("Msg font size:");
		add(messageFontSize);
		
		Component verticalStrut_4 = Box.createVerticalStrut(2);
		add(verticalStrut_4);
		
		messageTextColor = new ColorEditPanel();
		messageTextColor.setDefaultColor(ColorUtils.decodeToAWTColor("0.7 0.7 0.7 1"));
		messageTextColor.setPropertyChangedListener(this);
		messageTextColor.setPropertyName("Message color:");
		messageTextColor.setUiPropertyName("message-text-color");
		add(messageTextColor);
		
		Component verticalStrut_8 = Box.createVerticalStrut(10);
		add(verticalStrut_8);
		
		JLabel lblDrawables = new JLabel("Drawables:");
		lblDrawables.setForeground(Color.GREEN);
		add(lblDrawables);
		
		backgroundDrawable = new DrawableEditPanel();
		backgroundDrawable.setPropertyChangedListener(this);
		backgroundDrawable.setUiPropertyName("background");
		backgroundDrawable.setPropertyName("Background:");
		add(backgroundDrawable);
		
		Component verticalStrut_11 = Box.createVerticalStrut(2);
		add(verticalStrut_11);
		
		focusedBackgroundDrawable = new DrawableEditPanel();
		focusedBackgroundDrawable.setPropertyChangedListener(this);
		focusedBackgroundDrawable.setUiPropertyName("focused-background");
		focusedBackgroundDrawable.setPropertyName("Focused bg:");
		add(focusedBackgroundDrawable);
		
		Component verticalStrut_12 = Box.createVerticalStrut(2);
		add(verticalStrut_12);
		
		disabledBackgroundDrawable = new DrawableEditPanel();
		disabledBackgroundDrawable.setPropertyChangedListener(this);
		disabledBackgroundDrawable.setUiPropertyName("disabled-background");
		disabledBackgroundDrawable.setPropertyName("Disabled bg:");
		add(disabledBackgroundDrawable);
		
		Component verticalStrut_13 = Box.createVerticalStrut(2);
		add(verticalStrut_13);
		
		cursorDrawable = new DrawableEditPanel();
		cursorDrawable.setPropertyChangedListener(this);
		cursorDrawable.setUiPropertyName("cursor");
		cursorDrawable.setPropertyName("Cursor:");
		add(cursorDrawable);
		
		Component verticalStrut_14 = Box.createVerticalStrut(2);
		add(verticalStrut_14);
		
		selectionDrawable = new DrawableEditPanel();
		selectionDrawable.setPropertyChangedListener(this);
		selectionDrawable.setUiPropertyName("selection");
		selectionDrawable.setPropertyName("Selection:");
		add(selectionDrawable);
		
		Component verticalStrut_15 = Box.createVerticalStrut(10);
		add(verticalStrut_15);
		
		JLabel lblPasswordMode = new JLabel("Password mode:");
		lblPasswordMode.setForeground(Color.GREEN);
		add(lblPasswordMode);
		
		passwordMode = new BooleanEditPanel();
		passwordMode.setPropertyChangedListener(this);
		passwordMode.setUiPropertyName("password-mode");
		passwordMode.setDefaultValue("false");
		passwordMode.setPropertyName("Is password:");
		add(passwordMode);
		
		passwordChar = new StringEditPanel();
		passwordChar.getPropertyValue().addKeyListener(new OneCharKeyListener());
		passwordChar.setPropertyChangedListener(this);
		passwordChar.setUiPropertyName("password-char");
		passwordChar.setDefaultValue("*");
		passwordChar.setPropertyName("Password char:");
		add(passwordChar);
		
		Component verticalStrut_16 = Box.createVerticalStrut(10);
		add(verticalStrut_16);
		
		JLabel lblTextAlign = new JLabel("Text align:");
		lblTextAlign.setForeground(Color.GREEN);
		add(lblTextAlign);
		
		rightAlign = new BooleanEditPanel();
		rightAlign.setPropertyChangedListener(this);
		rightAlign.setUiPropertyName("right-align");
		rightAlign.setDefaultValue("false");
		rightAlign.setPropertyName("Is align right:");
		add(rightAlign);
	}

	@Override
	public void setConfig(UiConfig config) {
		fontNamePanel.setConfig(config);
		fontSizePanel.setConfig(config);
		fontColor.setConfig(config);
		initialText.setConfig(config);
		messageText.setConfig(config);
		messageTextColor.setConfig(config);
		focusedTextColor.setConfig(config);
		disabledTextColor.setConfig(config);
		disabledTextfield.setConfig(config);
		messageFontSize.setConfig(config);
		messageFontName.setConfig(config);
		backgroundDrawable.setConfig(config);
		disabledBackgroundDrawable.setConfig(config);
		selectionDrawable.setConfig(config);
		cursorDrawable.setConfig(config);
		focusedBackgroundDrawable.setConfig(config);
		passwordMode.setConfig(config);
		passwordChar.setConfig(config);
		maxTextLength.setConfig(config);
		fontOnlyChars.setConfig(config);
		rightAlign.setConfig(config);
	}
	
	class OneCharKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			JTextField textfield = (JTextField) e.getComponent();
			if(textfield.getText().length() >= 1) {
				textfield.setText(textfield.getText().substring(0, 1));
			}
		}
	}
	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
