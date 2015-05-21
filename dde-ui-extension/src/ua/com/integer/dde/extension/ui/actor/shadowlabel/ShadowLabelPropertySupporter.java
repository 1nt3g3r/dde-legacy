package ua.com.integer.dde.extension.ui.actor.shadowlabel;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.property.imp.textlabel.TextLabelPropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ShadowLabelPropertySupporter extends TextLabelPropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new ShadowLabelPropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		ShadowLabel shadowLabel = (ShadowLabel) actor;
		
		if (exists("shadow-offset-x")) {
			float shadowOffsetX = getSize("shadow-offset-x").getValue(actor);
			shadowLabel.setOffsetX(shadowOffsetX);
		}
		
		if (exists("shadow-offset-y")) {
			float shadowOffsetY = getSize("shadow-offset-y").getValue(actor);
			shadowLabel.setOffsetY(shadowOffsetY);
		}
		
		if (exists("shadow-color")) {
			Color shadowColor = getColor("shadow-color");
			shadowLabel.setShadowColor(shadowColor);
		}
	}
}
