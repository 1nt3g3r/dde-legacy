package ua.com.integer.dde.extension.ui.property.imp.textbutton;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.textbutton.TextButtonPropertyEditor;
import ua.com.integer.dde.extension.ui.property.imp.button.ButtonPropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextButtonPropertySupporter extends ButtonPropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new TextButtonPropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		TextButton button = (TextButton) actor;
		
		if (exists("text")) {
			button.setText(getLocalizedText("text"));
		} else {
			button.setText("Button");
		}
		
		if (exists("font")) button.getStyle().font = getFont("font");
		
		if (exists("font-color")) button.getStyle().fontColor = getColor("font-color");
		if (exists("down-font-color")) button.getStyle().downFontColor = getColor("down-font-color");
		if (exists("over-font-color")) button.getStyle().overFontColor = getColor("over-font-color");
		if (exists("checked-font-color")) button.getStyle().checkedFontColor = getColor("checked-font-color");
		if (exists("checked-over-font-color")) button.getStyle().checkedOverFontColor = getColor("checked-over-font-color");
		if (exists("disabled-font-color")) button.getStyle().disabledFontColor = getColor("disabled-font-color");
		
		button.setStyle(button.getStyle());
	}
}
