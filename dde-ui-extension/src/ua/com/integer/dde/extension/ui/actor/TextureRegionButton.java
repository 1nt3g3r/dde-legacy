package ua.com.integer.dde.extension.ui.actor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import ua.com.integer.dde.ui.actor.TextureRegionGroupActor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Кнопка, которая при нажатии уменьшается и увеличивается. 
 * 
 * @author 1nt3g3r
 */
public class TextureRegionButton extends TextureRegionGroupActor {
	class ScaleListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			event.getListenerActor().addAction(scaleTo(0.95f, 0.95f, 0.1f));
			return true;
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			event.getListenerActor().addAction(scaleTo(1f, 1f, 0.1f));
			super.touchUp(event, x, y, pointer, button);
		}
	}
	
	public TextureRegionButton() {
		addListener(new ScaleListener());
	}
}
