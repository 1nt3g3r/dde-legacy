package ua.com.integer.dde.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Helper class to faster work with Screenshots
 * 
 * @author 1nt3g3r
 */
public class ScreenshotUtils {
	/**
	 * @return screenshot of whole screen
	 */
	public static TextureRegion getScreenshot() {
		return getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**
	 * @return part of screen
	 */
	public static TextureRegion getScreenshot(int x, int y, int width, int height) {
		final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, width, height);
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		
		TextureRegion result = new TextureRegion(texture, x, y, width, height);
		return result;
	}
}
