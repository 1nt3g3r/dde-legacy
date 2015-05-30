package ua.com.integer.dde.extension.ui.actor.animation.spine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineAnimation extends Actor implements Disposable {
	public static final String ID = "ua.com.integer.dde.ui.spine.animation";
	public static final String CATEGORY = "Animations";
	public static final String DESCRIPTION = "Spine animation";
	
	private SkeletonRenderer renderer = new SkeletonRenderer();
	
	private AnimationState animationState;
	private Skeleton skeleton;
	
	private TextureAtlas atlas;
	private FileHandle spineData;
	
	public SpineAnimation() {
		initByPath("bee");
		setAnimation(1, "action", true);
	}
	
	public void initByPath(String name) {
		String basePath = "data/spine-animations/";
		
		String atlasPath = basePath + name + ".atlas";
		String jsonPath = basePath + name + ".json";
		
		setAtlas(new TextureAtlas(atlasPath));
		setSpineData(Gdx.files.internal(jsonPath));
	}
	
	public void setAtlas(TextureAtlas atlas) {
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
			setupSize();
			skeleton.setPosition(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
			skeleton.getRootBone().setRotation(getRotation());

			animationState.update(delta);
			animationState.apply(skeleton);
			skeleton.updateWorldTransform();
		}
		
		super.act(delta);
	}
	
	private void setupSize() {
		float baseAnimationWidth = skeleton.getData().getWidth();
		float baseAnimationHeight = skeleton.getData().getHeight();
		
		float parentWidth = getWidth() * getScaleX();
		float parentHeight = getHeight() * getScaleY();
		
		float scaleX = parentWidth / baseAnimationWidth;
		float scaleY = parentHeight / baseAnimationHeight;
		
		skeleton.getRootBone().setScale(Math.min(scaleX, scaleY));
	}
	
//	@Override
//	public void setOrigin(float originX, float originY) {
//		throw new IllegalAccessError("Origin is always in center!");
//	}
//	
//	@Override
//	public void setWidth(float width) {
//		throw new IllegalAccessError("Size is always (0, 0)!");
//	}
//	
//	@Override
//	public void setHeight(float height) {
//		throw new IllegalAccessError("Size is always (0, 0)!");
//	}
//	
//	@Override
//	public void setSize(float width, float height) {
//		throw new IllegalAccessError("Size is always (0, 0)!");
//	}

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
