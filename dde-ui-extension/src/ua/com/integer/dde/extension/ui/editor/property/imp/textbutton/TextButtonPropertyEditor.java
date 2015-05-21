package ua.com.integer.dde.extension.ui.editor.property.imp.textbutton;

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
		setTitle("Text button properties");
		
		text = new LocalizedStringEditPanel();
		text.setPropertyChangedListener(this);
		text.setUiPropertyName("text");
		text.setPropertyName("Text:");
		
		fontName = new FontNameEditPanel();
		fontName.setPropertyChangedListener(this);
		fontName.setPropertyName("Font name:");
		fontName.setUiPropertyName("font");
		
		fontSize = new SizeEditPanel();
		fontSize.setPropertyChangedListener(this);
		fontSize.setDefaultSize(FontUtils.getDefaultFontSize());
		fontSize.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		fontSize.setUiPropertyName("font-size");
		fontSize.setPropertyName("Font size:");
		
		fontColor = new ColorEditPanel();
		fontColor.setPropertyChangedListener(this);
		fontColor.setUiPropertyName("font-color");
		fontColor.setPropertyName("Default:");
		
		downFontColor = new ColorEditPanel();
		downFontColor.setPropertyChangedListener(this);
		downFontColor.setUiPropertyName("down-font-color");
		downFontColor.setPropertyName("Down:");
		
		overFontColor = new ColorEditPanel();
		overFontColor.setPropertyChangedListener(this);
		overFontColor.setUiPropertyName("over-font-color");
		overFontColor.setPropertyName("Over:");

		checkedFontColor = new ColorEditPanel();
		checkedFontColor.setPropertyChangedListener(this);
		checkedFontColor.setUiPropertyName("checked-font-color");
		checkedFontColor.setPropertyName("Checked:");
		
		checkedOverFontColor = new ColorEditPanel();
		checkedOverFontColor.setPropertyChangedListener(this);
		checkedOverFontColor.setUiPropertyName("checked-over-font-color");
		checkedOverFontColor.setPropertyName("Checked over:");
		
		disabledFontColor = new ColorEditPanel();
		disabledFontColor.setPropertyChangedListener(this);
		disabledFontColor.setUiPropertyName("disabled-font-color");
		disabledFontColor.setPropertyName("Disabled:");
		
		addContent(
				subHeader("Specific text button properties"),
				header("Button text"),
					text,
				header("Font"),
					fontName,
					fontSize,
				header("Font color"),
					fontColor,
					downFontColor,
					overFontColor,
					checkedFontColor,
					checkedOverFontColor,
					disabledFontColor
				);
	}

	public static void main(String[] args) {
		FrameTools.testingFrame(new TextButtonPropertyEditor());
	}
}
