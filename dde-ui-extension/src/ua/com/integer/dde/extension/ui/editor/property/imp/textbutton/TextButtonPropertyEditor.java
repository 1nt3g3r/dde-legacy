package ua.com.integer.dde.extension.ui.editor.property.imp.textbutton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.font.FontNameEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.LocalizedStringEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.button.ButtonPropertyEditor;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.SizeType;
import ua.com.integer.dde.startpanel.FrameTools;

public class TextButtonPropertyEditor extends ButtonPropertyEditor {
	private static final long serialVersionUID = -3412949830723416080L;
	
	private LocalizedStringEditPanel text;
	private ColorEditPanel overFontColor;
	private SizeEditPanel fontSize;
	private ColorEditPanel checkedOverFontColor;
	private ColorEditPanel fontColor;
	private FontNameEditPanel fontName;
	private ColorEditPanel checkedFontColor;
	private ColorEditPanel disabledFontColor;
	private ColorEditPanel downFontColor;
	public TextButtonPropertyEditor() {
		setMinimumSize(new Dimension(310, 570));
		setMaximumSize(new Dimension(310, 570));
		setPreferredSize(new Dimension(310, 570));
		
		JLabel lblNewLabel = new JLabel("Text button properties:");
		lblNewLabel.setForeground(Color.YELLOW);
		add(lblNewLabel);
		
		text = new LocalizedStringEditPanel();
		text.setPropertyChangedListener(this);
		text.setUiPropertyName("text");
		text.setPropertyName("Text:");
		add(text);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		add(verticalStrut_1);
		
		JLabel lblFont = new JLabel("Font:");
		lblFont.setForeground(Color.YELLOW);
		add(lblFont);
		
		fontName = new FontNameEditPanel();
		fontName.setPropertyChangedListener(this);
		fontName.setPropertyName("Font name:");
		fontName.setUiPropertyName("font");
		add(fontName);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		fontSize = new SizeEditPanel();
		fontSize.setPropertyChangedListener(this);
		fontSize.setDefaultSize(FontUtils.getDefaultFontSize());
		fontSize.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		fontSize.setUiPropertyName("font-size");
		fontSize.setPropertyName("Font size:");
		add(fontSize);
		
		JLabel lblFontColor = new JLabel("Font color:");
		lblFontColor.setForeground(Color.YELLOW);
		add(lblFontColor);
		
		fontColor = new ColorEditPanel();
		fontColor.setPropertyChangedListener(this);
		fontColor.setUiPropertyName("font-color");
		fontColor.setPropertyName("Default:");
		add(fontColor);
		
		Component verticalStrut_2 = Box.createVerticalStrut(2);
		add(verticalStrut_2);
		
		downFontColor = new ColorEditPanel();
		downFontColor.setPropertyChangedListener(this);
		downFontColor.setUiPropertyName("down-font-color");
		downFontColor.setPropertyName("Down:");
		add(downFontColor);
		
		Component verticalStrut_3 = Box.createVerticalStrut(2);
		add(verticalStrut_3);
		
		overFontColor = new ColorEditPanel();
		overFontColor.setPropertyChangedListener(this);
		overFontColor.setUiPropertyName("over-font-color");
		overFontColor.setPropertyName("Over:");
		add(overFontColor);
		
		Component verticalStrut_4 = Box.createVerticalStrut(2);
		add(verticalStrut_4);
		
		checkedFontColor = new ColorEditPanel();
		checkedFontColor.setPropertyChangedListener(this);
		checkedFontColor.setUiPropertyName("checked-font-color");
		checkedFontColor.setPropertyName("Checked:");
		add(checkedFontColor);
		
		Component verticalStrut_5 = Box.createVerticalStrut(2);
		add(verticalStrut_5);
		
		checkedOverFontColor = new ColorEditPanel();
		checkedOverFontColor.setPropertyChangedListener(this);
		checkedOverFontColor.setUiPropertyName("checked-over-font-color");
		checkedOverFontColor.setPropertyName("Checked over:");
		add(checkedOverFontColor);
		
		Component verticalStrut_6 = Box.createVerticalStrut(2);
		add(verticalStrut_6);
		
		disabledFontColor = new ColorEditPanel();
		disabledFontColor.setPropertyChangedListener(this);
		disabledFontColor.setUiPropertyName("disabled-font-color");
		disabledFontColor.setPropertyName("Disabled:");
		add(disabledFontColor);
	}
	
	@Override
	public void setConfig(UiConfig config) {
		super.setConfig(config);
		
		text.setConfig(config);
		
		fontSize.setConfig(config);
		fontName.setConfig(config);
		
		fontColor.setConfig(config);
		downFontColor.setConfig(config);
		disabledFontColor.setConfig(config);
		checkedFontColor.setConfig(config);
		checkedOverFontColor.setConfig(config);
		overFontColor.setConfig(config);
	}

	
	public static void main(String[] args) {
		FrameTools.testingFrame(new TextButtonPropertyEditor());
	}
}
