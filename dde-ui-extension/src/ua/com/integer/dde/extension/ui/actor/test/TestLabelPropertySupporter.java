package ua.com.integer.dde.extension.ui.actor.test;

import javax.swing.JPanel;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;

public class TestLabelPropertySupporter extends PropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new TestLabelPropertyEditor();
	}

}
