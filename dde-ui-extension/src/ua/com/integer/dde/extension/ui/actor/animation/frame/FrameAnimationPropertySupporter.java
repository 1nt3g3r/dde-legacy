package ua.com.integer.dde.extension.ui.actor.animation.frame;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FrameAnimationPropertySupporter extends PropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new FrameAnimationPropertyEditor();
	}
	
	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		FrameAnimationActor frameAnimation = (FrameAnimationActor) actor;
		
		if (exists("frames")) {
			frameAnimation.initAnimation(0.3f, getRegions("frames"));
		}
		
		if (exists("animation-play-mode")) {
			frameAnimation.setPlayMode(PlayMode.valueOf(config.get("animation-play-mode")));
		}
		
		if (exists("frame-duration")) {
			frameAnimation.setFrameDuration(getFloat("frame-duration"));
		}

		if (exists("scaling")) {
			frameAnimation.setScaling(getScaling("scaling"));
		}
		
		if (exists("align")) {
			frameAnimation.setAlign(getAlign("align"));
		}
	}
}
