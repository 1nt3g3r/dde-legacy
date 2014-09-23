package ua.com.integer.dde.extension.ui.property.imp.textlabel;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.actor.TextLabel;
import ua.com.integer.dde.extension.ui.editor.UiConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.imp.textlabel.TextLabelPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Класс для настройки параметров текстовой метки
 * 
 * @author 1nt3g3r
 */
public class TextLabelPropertySupporter extends PropertySupporter {
	private TextLabel textLabel;
	
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor,
			UiConfigEditor editor) {
		return new TextLabelPropertyEditor();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		textLabel = (TextLabel) actor;
		
		checkText();
		checkTextColor();
		checkFont();
		checkTextAlignment();
		checkWrap();
		
		if (exists("font-scale-x")) {
			textLabel.setFontScaleX(getFloat("font-scale-x"));
		}
		
		if (exists("font-scale-y")) {
			textLabel.setFontScaleY(getFloat("font-scale-y"));
		}
		
		if (exists("background")) {
			textLabel.getStyle().background = getDrawable("background");
		}

		textLabel.setStyle(textLabel.getStyle());
	}
	
	private void checkText() {
		textLabel.setText(getLocalizedText("text"));
	}

	private void checkTextColor() {
		if (exists("text-color")) {
			textLabel.getTextLabel().getStyle().fontColor = getColor("text-color");
		}
	}
	
	private void checkFont() {
		if (exists("font-name")) {
			textLabel.getTextLabel().getStyle().font = getFont("font-name");
			textLabel.getTextLabel().setStyle(textLabel.getTextLabel().getStyle());
		}
	}
	
	private void checkTextAlignment() {
		if (exists("text-align")) {
			textLabel.setAlign(getTextAlign("text-align"));
		}
	}
	
	private void checkWrap() {
		textLabel.setWrap(Boolean.parseBoolean(config.get("text-wrap", "true")));
	}
}
