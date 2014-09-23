package ua.com.integer.dde.extension.ui.property.imp.image;

import javax.swing.JPanel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.UiConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.imp.image.ImagePropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

public class ImagePropertySupporter extends PropertySupporter {

	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor,
			UiConfigEditor editor) {
		return new ImagePropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		Image image = (Image) actor;
		
		if (exists("drawable")) {
			image.setDrawable(getDrawable("drawable"));
		}
		
		if (exists("scaling")) {
			image.setScaling(getScaling("scaling"));
		}
		
		if (exists("align")) {
			image.setAlign(getAlign("align"));
		}
	}

}
