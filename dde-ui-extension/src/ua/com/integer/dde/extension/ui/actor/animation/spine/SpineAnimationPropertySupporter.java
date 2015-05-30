package ua.com.integer.dde.extension.ui.actor.animation.spine;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpineAnimationPropertySupporter extends PropertySupporter {
	private SpineAnimationActor animation;
	
	private Runnable setConfigRunnable = new Runnable() {
		public void run() {
			animation.initByPath(config.get("spine-animation-config"));
		}
	};
	
	private Runnable setAnimationRunnable = new Runnable() {
		@Override
		public void run() {
			animation.setAnimation(1, config.get("spine-animation-name"), true);
		}
	};
	
	
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new SpineAnimationPropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		animation = (SpineAnimationActor) actor;
		
		editIfExists("spine-animation-config", setConfigRunnable);
		editIfExists("spine-animation-name", setAnimationRunnable);
	}
}
