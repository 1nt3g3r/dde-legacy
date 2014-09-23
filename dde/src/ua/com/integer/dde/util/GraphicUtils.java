package ua.com.integer.dde.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GraphicUtils {
	public static Color createColor(int r, int g, int b) {
		return new Color(r/255.0f, g/255.0f, b/255.0f, 1);
	}
	
	public static Color createColor(java.awt.Color awtColor) {
		return new Color(awtColor.getRed()/255f, awtColor.getGreen()/255f, awtColor.getBlue()/255f, awtColor.getAlpha()/255f);
	}
	
	public static void saveImage(String imageUrl, File destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
	
	public static Drawable toDrawable(TextureRegion textureRegion) {
		return new TextureRegionDrawable(textureRegion);
	}
	
	public static Pixmap applyTransparentMask(Pixmap pixmap, Pixmap mask) {
		if (pixmap == null || mask == null) {
			throw new IllegalArgumentException("pixmap or mask cannot be null!");
		}
		if (pixmap.getWidth() != mask.getWidth() || pixmap.getHeight() != mask.getHeight()) {
			throw new IllegalArgumentException("mask and pixmap should be same size!");
		}
		
		Pixmap result = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Format.RGBA8888);
		for(int i = 0, iEnd = pixmap.getWidth(); i < iEnd; i++) {
			for(int j = 0, jEnd = pixmap.getHeight(); j < jEnd; j++) {
				if (mask.getPixel(i, j) != -1) {
					result.drawPixel(i, j, pixmap.getPixel(i, j));
				}
			}
		}
		return result;
	}
	
	/**
	 * Возвращает цвет, закодированный в строку. Строка - последовательность чисел с плавающей точкой в пределах 
	 * от 0 до 1 - формат RGBA. Пример красного цвета, закодированного данным методом: "1 0 0 1"
	 */
	public String encodeGDXColor(Color color) {
		return color.r + " " + color.g + " " + color.b + " " + color.a;
	}
	
	/**
	 * Создает и возвращает GDX цвет из строки, закодированной ранее методом encodeAWTColor
	 * @param color строка из цветом
	 */
	public static Color decodeToGDXColor(String color) {
		String[] colorParts = color.split(" ");
		
		float r = Float.parseFloat(colorParts[0]);
		float g = Float.parseFloat(colorParts[1]);
		float b = Float.parseFloat(colorParts[2]);
		float a = Float.parseFloat(colorParts[3]);
		
		return new Color(r, g, b, a);
	}
}
