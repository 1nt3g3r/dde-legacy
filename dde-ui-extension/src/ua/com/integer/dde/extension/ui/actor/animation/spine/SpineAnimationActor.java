package ua.com.integer.dde.extension.ui.actor.animation.spine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineAnimationActor extends Actor implements Disposable {
	public static final String ID = "ua.com.integer.dde.ui.spine.animation";
	public static final String CATEGORY = "Animations";
	public static final String DESCRIPTION = "Spine animation";
	
	private SkeletonRenderer renderer = new SkeletonRenderer();
	
	private AnimationState animationState;
	private Skeleton skeleton;
	
	private TextureAtlas atlas;
	private FileHandle spineData;
	
	public void initByPath(String name) {
		String basePath = SpineAnimations.ANIMATION_PATH + "/";
		
		String atlasPath = basePath + name + ".atlas";
		String jsonPath = basePath + name + ".json";
		
		setAtlas(new TextureAtlas(atlasPath));
		setSpineData(Gdx.files.internal(jsonPath));
		
		sizeChanged();
		positionChanged();
	}

	public String[] getAnimations() {
		Array<Animation> animations = skeleton.getData().getAnimations();
		
		String[] result = new String[animations.size];
		for(int i = 0; i < animations.size; i++) {
			result[i] = animations.get(i).getName();
		}
		
		return result;
	}
	
	public void setAtlas(TextureAtlas atlas) {
		dispose();
		
		this.atlas = atlas;
	}
	
	public void setSpineData(FileHandle spineData) {
		this.spineData = spineData;
		
		tryInit();
	}

	public void setSpineData(String spineData) {
		this.spineData = Gdx.files.internal(spineData);
		
		tryInit();
	}
	
	private void tryInit() {
		if( atlas != null && spineData != null) {
			
			SkeletonJson json = new SkeletonJson(atlas);
			json.setScale(1f);
			
			SkeletonData skeletonData = json.readSkeletonData(spineData);
			skeleton = new Skeleton(skeletonData);
			
			AnimationStateData data = new AnimationStateData(skeletonData);
			animationState = new AnimationState(data);
		}
	}
		
	public void setAnimation(int id, String animation, boolean loop) {
		stopAllAnimations();
		animationState.setAnimation(id, animation, loop);
	}
	
	public void stopAllAnimations() {
		animationState.clearTracks();
	}
	
	public void setTimeScale(float scale) {
		animationState.setTimeScale(scale);
	}
	
	@Override
	public void act(float delta) {
		if (skeleton != null && animationState != null) {
			animationState.update(delta);
			animationState.apply(skeleton);
			skeleton.updateWorldTransform();
		}
		
		super.act(delta);
	}
	
	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees);
		
		if (skeleton != null) {
			skeleton.getRootBone().setRotation(getRotation());
		}
	}
	
	@Override
	protected void positionChanged() {
		if (skeleton == null) {
			return;
		}
		
		skeleton.setPosition(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
	}
	
	protected void sizeChanged() {
		if (skeleton == null) {
			return;
		}
		
		float baseAnimationWidth = skeleton.getData().getWidth();
		float baseAnimationHeight = skeleton.getData().getHeight();
		
		float parentWidth = getWidth() * getScaleX();
		float parentHeight = getHeight() * getScaleY();
		
		float scaleX = parentWidth / baseAnimationWidth;
		float scaleY = parentHeight / baseAnimationHeight;
		
		skeleton.getRootBone().setScale(Math.min(scaleX, scaleY));
		
		skeleton.setPosition(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
	}
	
	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);
		
		positionChanged();
	}
	
	@Override
	public void setScale(float scaleXY) {
		super.setScale(scaleXY);
		
		positionChanged();
	}
	
	@Override
	public void setScaleX(float scaleX) {
		super.setScaleX(scaleX);
		
		positionChanged();
	}
	
	@Override
	public void setScaleY(float scaleY) {
		super.setScaleY(scaleY);
		
		positionChanged();
	}

	@Override
	public void setOrigin(float originX, float originY) {
		super.setOrigin(getWidth()/2f, getHeight()/2f);
	}
	
	@Override
	public void setOriginX(float originX) {
		super.setOriginX(getWidth()/2f);
	}
	
	@Override
	public void setOriginY(float originY) {
		super.setOriginY(getHeight()/2f);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		if (renderer != null && skeleton != null) {
			renderer.draw(batch, skeleton);
		}
	}

	@Override
	public void dispose() {
		if (atlas != null) {
			atlas.dispose();
			atlas = null;
		}
	}
}
