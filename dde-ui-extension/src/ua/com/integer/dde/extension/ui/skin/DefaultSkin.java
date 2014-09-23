package ua.com.integer.dde.extension.ui.skin;

import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DefaultSkin {
	private static DefaultSkin instance = new DefaultSkin();
	
	private Skin skin;
	
	private DefaultSkin() {
		skin =new Skin();
		skin.add("default-font", AbstractScreen.getKernel().getFont(Gdx.graphics.getWidth()/30), BitmapFont.class);
		skin.addRegions(new TextureAtlas(Gdx.files.classpath("ua/com/integer/dde/extension/ui/skin/uiskin.atlas")));
		skin.load(Gdx.files.classpath("ua/com/integer/dde/extension/ui/skin/uiskin.json"));
	}
	
	public static DefaultSkin getInstance() {
		return instance;
	}
	
	public Skin getSkin() {
		return skin;
	}
}
