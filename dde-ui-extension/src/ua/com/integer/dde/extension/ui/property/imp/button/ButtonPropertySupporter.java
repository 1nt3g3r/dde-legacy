package ua.com.integer.dde.extension.ui.property.imp.button;

import javax.swing.JPanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.UiConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.imp.button.ButtonPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

public class ButtonPropertySupporter extends PropertySupporter {

	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor,
			UiConfigEditor editor) {
		return new ButtonPropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		Button button = (Button) actor;
		
		if (exists("up-drawable")) button.getStyle().up = getDrawable("up-drawable");
		if(exists("down-drawable")) button.getStyle().down = getDrawable("down-drawable");
		if(exists("checked-drawable")) button.getStyle().checked = getDrawable("checked-drawable");
		if(exists("over-drawable")) button.getStyle().over = getDrawable("over-drawable");
		if (exists("checked-over-drawable")) button.getStyle().checkedOver = getDrawable("checked-over-drawable");
		if (exists("disabled-drawable")) button.getStyle().disabled = getDrawable("disabled-drawable");
		
		if(exists("pressed-offset-x")) button.getStyle().pressedOffsetX = getInt("pressed-offset-x");
		if(exists("pressed-offset-y")) button.getStyle().pressedOffsetY = getInt("pressed-offset-y");
		
		if(exists("unpressed-offset-x")) button.getStyle().unpressedOffsetX = getInt("unpressed-offset-x");
		if(exists("unpressed-offset-y")) button.getStyle().unpressedOffsetY = getInt("unpressed-offset-y");

		if(exists("disabled")) button.setDisabled(getBoolean("disabled"));
		
		button.setStyle(button.getStyle());
	}

}
