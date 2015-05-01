package ua.com.integer.dde.extension.ui.property.imp.checkbox;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.checkbox.CheckboxPropertyEditor;
import ua.com.integer.dde.extension.ui.property.imp.textbutton.TextButtonPropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

public class CheckboxPropertySupporter extends TextButtonPropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new CheckboxPropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		CheckBox checkbox = (CheckBox) actor;
		
		if (!exists("text")) {
			checkbox.setText("Checkbox");
		}
		
		if (exists("checkbox-on-drawable")) checkbox.getStyle().checkboxOn = getDrawable("checkbox-on-drawable");
		if (exists("checkbox-off-drawable")) checkbox.getStyle().checkboxOff = getDrawable("checkbox-off-drawable");
		if (exists("checkbox-over-drawable")) checkbox.getStyle().checkboxOver = getDrawable("checkbox-over-drawable");
		if (exists("checkbox-disabled-on-drawable")) checkbox.getStyle().checkboxOnDisabled = getDrawable("checkbox-disabled-on-drawable");
		if (exists("checkbox-disabled-off-drawable")) checkbox.getStyle().checkboxOffDisabled = getDrawable("checkbox-disabled-off-drawable");
	}
}
