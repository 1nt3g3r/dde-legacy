package ua.com.integer.dde.extension.ui.property.imp.textfield;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.textfield.TextFieldPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextFieldPropertySupporter extends PropertySupporter {

	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new TextFieldPropertyEditor();
	}

	private TextField textField;
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		textField = (TextField) actor;
		
		if (exists("font-only-chars")) {
			textField.setOnlyFontChars(getBoolean("font-only-chars"));
		}
		
		if (exists("texfield-background")) {
			textField.getStyle().background = getDrawable("textfield-background");
		}
		
		if (exists("font")) {
			textField.getStyle().font = getFont("font");
		}
		
		if (exists("font-color")) {
			textField.getStyle().fontColor = getColor("font-color");
		}
		
		if (exists("text")) {
			textField.setText(getLocalizedText("text"));
		}
		
		if (exists("message-font")) {
			textField.getStyle().messageFont = (getFont("message-font"));
		}
		
		if (exists("message-text")) {
			textField.setMessageText(getLocalizedText("message-text"));
		}
		
		if (exists("message-text-color")) {
			textField.getStyle().messageFontColor = getColor("message-text-color");
		}
		
		if (exists("focused-text-color")) {
			textField.getStyle().focusedFontColor = getColor("focused-text-color");
		}
		
		if (exists("disabled-text-color")) {
			textField.getStyle().disabledFontColor = getColor("disabled-text-color");
		}
		
		if (exists("textfield-disabled")) {
			textField.setDisabled(getBoolean("textfield-disabled"));
		}
		
		if (exists("background")) {
			textField.getStyle().background = getDrawable("background");
		}
		
		if (exists("focused-background")) {
			textField.getStyle().focusedBackground = getDrawable("focused-background");
		}
		
		if (exists("disabled-background")) {
			textField.getStyle().disabledBackground = getDrawable("disabled-background");
		}
		
		if (exists("cursor")) {
			textField.getStyle().cursor = getDrawable("cursor");
		}
		
		if (exists("selection")) {
			textField.getStyle().selection = getDrawable("selection");
		}
		
		if (exists("password-mode")) {
			textField.setPasswordMode(getBoolean("password-mode"));
			if (exists("password-char")) {
				String passwordString = config.get("password-char", "");
				if (passwordString.length() >= 1) {
					textField.setPasswordCharacter(passwordString.charAt(0));
				}
			}
		}
		
		if (exists("max-text-length")) {
			textField.setMaxLength(getInt("max-text-length"));
		}
		
		if (exists("right-align")) {
			textField.setRightAligned(getBoolean("right-align"));
		}
		
		textField.setStyle(textField.getStyle());
	}
}
