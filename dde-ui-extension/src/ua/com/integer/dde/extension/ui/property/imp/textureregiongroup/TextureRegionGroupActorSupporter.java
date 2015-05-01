package ua.com.integer.dde.extension.ui.property.imp.textureregiongroup;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.textureregiongroupactor.TextureRegionGroupActorPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.ui.actor.TextureRegionGroupActor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextureRegionGroupActorSupporter extends PropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new TextureRegionGroupActorPropertyEditor();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		if (exists("image")) {
			((TextureRegionGroupActor) actor).setRegion(getImageRegion("image"));
		}
	}
}
