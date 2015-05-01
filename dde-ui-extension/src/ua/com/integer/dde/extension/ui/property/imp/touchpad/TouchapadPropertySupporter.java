package ua.com.integer.dde.extension.ui.property.imp.touchpad;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.touchpad.TouchpadPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class TouchapadPropertySupporter extends PropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new TouchpadPropertyEditor();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		Touchpad touchpad = (Touchpad) actor;
		
		if (exists("background")) touchpad.getStyle().background = getDrawable("background");
		if (exists("knob")) touchpad.getStyle().knob = getDrawable("knob");
		if (exists("deadzone")) touchpad.setDeadzone(getInt("deadzone"));
		
		touchpad.setStyle(touchpad.getStyle());
	}
}
