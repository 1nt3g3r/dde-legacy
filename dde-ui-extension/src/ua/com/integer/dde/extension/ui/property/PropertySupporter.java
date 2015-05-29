package ua.com.integer.dde.extension.ui.property;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.localize.Localize;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.actor.Align;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.skin.DefaultSkin;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.util.GraphicUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public abstract class PropertySupporter {
	protected UiConfig config;
	protected Actor actor;
	protected DDKernel kernel;
	private static final String DEFAULT_SIZE_STR = new Size().toString();
	
	public abstract JPanel createSetupPanel(UiConfig config, Actor actor);
	
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		this.config = config;
		this.actor = actor;
	};
	
	public Color getColor(String name) {
		return GraphicUtils.decodeToGDXColor(config.get(name, "1 1 1 1"));
	}
	
	public TextureRegion getImageRegion(String name) {
		String[] nameParts = config.get(name).split(";");
		return AbstractScreen.getRegion(nameParts[0], nameParts[1]);
	}
	
	public TextureAtlas getAtlas(String fullName) {
		String[] nameParts = config.get(fullName).split(";");
		return AbstractScreen.getKernel().getResourceManager().atlases().getAtlas(nameParts[0]);
	}
	
	public Array<AtlasRegion> getRegions(String fullName) {
		String[] nameParts = config.get(fullName).split(";");
		String animationName = nameParts[1].split("_")[0];
		return getAtlas(fullName).findRegions(animationName);
	}
	
	public int getInt(String name) {
		String value = config.get(name);
		if (value.contains(".")) {
			return (int) Float.parseFloat(config.get(name));
		}
		
		return Integer.parseInt(config.get(name));
	}
	
	public float getFloat(String name) {
		return Float.parseFloat(config.get(name));
	}
	
	public boolean getBoolean(String name) {
		return Boolean.parseBoolean(config.get(name));
	}
	
	public boolean getBoolean(String name, boolean defValue) {
		return Boolean.parseBoolean(config.get(name, defValue + ""));
	}
	
	public Touchable getTouchable(String name) {
		return Touchable.valueOf(config.get(name));
	}
	
	public String getLocalizedText(String name) {
		if (exists(name + "-localize") && getBoolean(name + "-localize", true)) {
			if (config.get(name + "-localized-tag", "").equals("")) {
				return config.get(name);
			} else {
				return Localize.getInstance().translate(config.get(name + "-localized-tag"));
			}
		} else {
			return config.get(name);
		}
	}
	
	public Align getTextAlign(String name) {
		return Align.valueOf(config.get(name, Align.CENTER + ""));
	}
	
	public Size getSize(String name) {
		return Size.fromString(config.get(name, DEFAULT_SIZE_STR));
	}
	
	public Size getSize(String name, Size defaultSize) {
		return Size.fromString(config.get(name, defaultSize.toString()));
	}
	
	public BitmapFont getFont(String name) {
		int fontSize = (int) (getSize(name + "-size", FontUtils.getDefaultFontSize()).getValue());
		
		if (fontSize < 5) fontSize = 5;
		
		return AbstractScreen.getFont(config.get(name), fontSize); 
	}
	
	public boolean exists(String name) {
		return config.properties.containsKey(name);
	}
	
	public Drawable getDrawable(String name) {
		if (config.get(name, "").equals("")) {
			return DefaultSkin.getInstance().getSkin().getDrawable("default-select-selection");
		} else {
			String type = config.get(name + "-type");
			
			BaseDrawable toReturn = null;
			if (type.equals("texture-region-drawable")) {
				toReturn = new TextureRegionDrawable(getImageRegion(name));
			} else if (type.equals("tiled-region-drawable")) {
				toReturn = new TiledDrawable(getImageRegion(name));
			} else if (type.equals("9patch-region-drawable")) {
				int left = getInt(name + "-9-left");
				int right = getInt(name + "-9-right");
				int top = getInt(name + "-9-top");
				int bottom = getInt(name + "-9-bottom");
				
				toReturn = new NinePatchDrawable(new NinePatch(getImageRegion(name), left, right, top, bottom));
			}
			
			if (getBoolean("setup-size", false)) {
				if (exists(name + "-min-width")) toReturn.setMinWidth(getInt(name + "-min-width"));
				if (exists(name + "-min-height")) toReturn.setMinHeight(getInt(name + "-min-height"));
				if (exists(name + "-left-width")) toReturn.setLeftWidth(getInt(name + "-left-width"));
				if (exists(name + "-right-width")) toReturn.setRightWidth(getInt(name + "-right-width"));
				if (exists(name + "-top-height")) toReturn.setTopHeight(getInt(name + "-top-height"));
				if (exists(name + "-bottom-height")) toReturn.setBottomHeight(getInt(name + "-bottom-height"));
			}
			
			
			return toReturn;
		}
	}
	
	public int getAlign(String name) {
		return Align.valueOf(config.get(name)).getAlign();
	}
	
	public Scaling getScaling(String name) {
		return Scaling.valueOf(config.get(name));
	}
	
	public void editIfExists(String key, Runnable editRunnable) {
		if (exists(key)) {
			editRunnable.run();
		}
	}
}
