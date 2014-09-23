package ua.com.integer.dde.extension.ui.property.imp.box;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.actor.Box;
import ua.com.integer.dde.extension.ui.actor.Box.Align;
import ua.com.integer.dde.extension.ui.editor.UiConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.imp.box.BoxPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BoxPropertySupporter extends PropertySupporter {
	private Box horizontalBox;
	
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor,
			UiConfigEditor editor) {
		return new BoxPropertyEditor();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		horizontalBox = (Box) actor;
		
		checkAlignment();
		checkPad();
	}
	
	private void checkAlignment() {
		String align = config.properties.get("alignment", Align.CENTER + "");
		horizontalBox.setAlignment(Align.valueOf(align));
	}
	
	private void checkPad() {
		if (exists("box-pad")) {
			horizontalBox.setPad(getSize("box-pad"));
		}
	}
}
