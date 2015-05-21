package ua.com.integer.dde.extension.ui.property.imp.common;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.common.CommonPropertiesExpandPanel;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.util.GraphicUtils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class CommonPropertySupporter extends PropertySupporter {
	private static CommonPropertySupporter instance = new CommonPropertySupporter();
	
	private CommonPropertySupporter() {}
	
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new CommonPropertiesExpandPanel();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		checkVisible();
		checkCenterOrigin();
		checkTouchable();
		checkRotation();
		checkScales();
		checkColor();
	}
	
	private void checkVisible() {
		actor.setVisible(Boolean.parseBoolean(config.get("actor-visible", "true")));
	}
	
	private void checkCenterOrigin() {
		if (Boolean.parseBoolean(config.get("actor-center-origin", "true"))) {
			actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
		}
	}
	
	private void checkTouchable() {
		actor.setTouchable(Touchable.valueOf(config.get("actor-touchable", Touchable.enabled + "")));
	}
	
	private void checkRotation() {
		actor.setRotation(Float.parseFloat(config.get("actor-rotation", "0")));
	}
	
	private void checkScales() {
		actor.setScaleX(Float.parseFloat(config.get("actor-scale-x", "1")));
		actor.setScaleY(Float.parseFloat(config.get("actor-scale-y", "1")));
	}
	
	private void checkColor() {
		String initialColorString = config.get("actor-color", "1 1 1 1");
		actor.setColor(GraphicUtils.decodeToGDXColor(initialColorString));
	}

	public static CommonPropertySupporter getInstance() {
		return instance;
	}
	
}
