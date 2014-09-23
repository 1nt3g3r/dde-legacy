package ua.com.integer.dde.res.screen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class TiledBackgroundScreen extends AbstractScreen {
	private TiledDrawable tiledDrawable;
	
	public TiledBackgroundScreen(String packName, String regionName) {
		this(getRegion(packName, regionName));
	}

	public TiledBackgroundScreen(TextureRegion region) {
		this();
		tiledDrawable = new TiledDrawable(region);
	}
	
	public TiledBackgroundScreen() {
		getConfig().needDrawBackgroundImage = true;
	}
	
	@Override
	protected void drawBackgroundImage() {
		getStage().getBatch().begin();
		if (tiledDrawable != null) {
			tiledDrawable.draw(getStage().getBatch(), 0, 0, getStage().getWidth(), getStage().getHeight());
		}
		getStage().getBatch().end();
	}
}
