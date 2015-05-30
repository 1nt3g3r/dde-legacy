package ua.com.integer.dde.extension.ui.actor.animation.frame;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class FrameAnimation extends Image {
	public static final String ID = "ua.com.integer.dde.ui.frame.animation";
	public static final String CATEGORY = "Animations";
	public static final String DESCRIPTION = "Frame animation";
	
	private Animation animation;
	private float animationDelay;
	private TextureRegionDrawable regionDrawable;
	
	public FrameAnimation() {
	}
	
	public void setFromAtlas(TextureAtlas atlas, String animation, float delay) {
		initAnimation(delay, atlas.findRegions(animation));
	}
	
	public FrameAnimation(float delay, TextureRegion[] regions) {
		initAnimation(delay, regions);
	}

	public void initAnimation(float delay, TextureRegion[] regions) {
		animationDelay = 0f;
		
		animation = new Animation(delay, regions);
		
		regionDrawable = new TextureRegionDrawable(regions[0]);
		setScaling(Scaling.fit);
		setDrawable(regionDrawable);
	}
	
	public FrameAnimation(float delay, Array<? extends TextureRegion> regions) {
		initAnimation(delay, regions);
	}

	public void initAnimation(float delay, Array<? extends TextureRegion> regions) {
		animationDelay = 0f;
		
		animation = new Animation(delay, regions);
		
		regionDrawable = new TextureRegionDrawable(regions.first());
		setScaling(Scaling.fit);
		setDrawable(regionDrawable);
	}
	
	public void setPlayMode(PlayMode mode) {
		if (animation != null) {
			animation.setPlayMode(mode);
		}
	}
	
	@Override
	public void act(float delta) {
		if (animation != null) {
			animationDelay += delta;
			
			TextureRegion newRegion = animation.getKeyFrame(animationDelay);
			if (regionDrawable.getRegion() != newRegion) {
				regionDrawable.setRegion(newRegion);
				validate();
			}
		}
		
		super.act(delta);
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public void setFrameDuration(float frameDuration) {
		if (animation != null) {
			animation.setFrameDuration(frameDuration);
		}
	}
}
