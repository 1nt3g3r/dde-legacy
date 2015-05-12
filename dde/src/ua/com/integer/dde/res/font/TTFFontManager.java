package ua.com.integer.dde.res.font;

import java.util.HashMap;
import java.util.Map;

import ua.com.integer.dde.res.load.LoadManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Manager for fonts. Allows to generate bitmap fonts from .ttf files. 
 * Implements "lazy" loading - if font wasn't loaded, it will try load it 
 * on the fly.
 * 
 * @author integer
 */
public class TTFFontManager implements LoadManager {
	public static final String DEFAULT_CHARS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>’";
	
	private String charsToGenerate;
	private String fontDirectory;
	
	private ObjectMap<String, Map<Integer, BitmapFont>> fonts;
	private ObjectMap<String, FreeTypeFontGenerator> fGenerators;
	
	private FreeTypeFontGenerator robotoCondensedGenerator;
	private ObjectMap<Integer, BitmapFont> robotoCondensedFonts;
	
	private Array<String> loadFontQueue;
	private int totalFontCount;
	
	
	public TTFFontManager() {
		this(DEFAULT_CHARS);
	}
	
	public TTFFontManager(String charsToGenerate) {
		this.charsToGenerate = charsToGenerate;
		fonts = new ObjectMap<String, Map<Integer, BitmapFont>>();
		fGenerators = new ObjectMap<String, FreeTypeFontGenerator>();
	}
	
	public void setFontChars(String charsToGenerate) {
		this.charsToGenerate = charsToGenerate;
	}
	
	public void setFontDirectory(String fontDirectory) {
		this.fontDirectory = fontDirectory;
	}
	
	public BitmapFont getFont(String fontName, int size) {
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.characters = charsToGenerate;
		param.flip = false;

		return getFont(fontName, size, param);
	}
	
	/**
	 * Возвращает шрифт указанного размера. Если шрифт еще не создан, 
	 * создает и кеширует его для последующих вызовов.
	 * ВАЖНО! Имя "standard" зарезервировано и используется для встроенного шрифта Roboto-Condensed
	 * @param size размер шрифта
	 * @return
	 */
	public BitmapFont getFont(String fontName, int size, FreeTypeFontParameter param) {
		if (fontName.equals("standard")) return getFont(size);
		
		createFontGeneratorIfNeed(fontName);
		createFontMapIfNeed(fontName);
		
		createFontIfNeed(fontName, size, param);
		
		return fonts.get(fontName).get(size);
	}
	
	/**
	 * Returns Roboto-Condensed font. It built in gdx-freetype extension jar
	 * @param size size of font what you want
	 * @return cached bitmap font
	 */
	public BitmapFont getFont(int size) {
		if (robotoCondensedGenerator == null) {
			robotoCondensedGenerator = new FreeTypeFontGenerator(Gdx.files.classpath("ua/com/integer/dde/res/font/Roboto-Condensed.ttf"));
		}
		
		if (robotoCondensedFonts == null) {
			robotoCondensedFonts = new ObjectMap<Integer, BitmapFont>();
		}
		
		if (!robotoCondensedFonts.containsKey(size)) {
			FreeTypeFontParameter param = new FreeTypeFontParameter();
			param.size = size;
			param.characters = charsToGenerate;
			param.flip = false;
			BitmapFont fnt = robotoCondensedGenerator.generateFont(param);
			robotoCondensedFonts.put(size, fnt);
		}
		
		return robotoCondensedFonts.get(size);
	}
	
	private void createFontGeneratorIfNeed(String fontName) {
		try {
			if (fGenerators.get(fontName) == null) {
				FreeTypeFontGenerator fGen = new FreeTypeFontGenerator(Gdx.files.internal(fontDirectory +"/" + fontName + ".ttf"));
				fGenerators.put(fontName, fGen);
			}
		} catch (GdxRuntimeException ex) {
			Gdx.app.error(getClass().toString(), "Can't load font " + fontName + ex);
			System.exit(0);
		}
	}
	
	private void createFontMapIfNeed(String fontName) {
		if (fonts.get(fontName) == null) {
			fonts.put(fontName, new HashMap<Integer, BitmapFont>());
		}
	}
	
	private void createFontIfNeed(String fontName, int size, FreeTypeFontParameter parameter) {
		if (fonts.get(fontName).get(size) == null) {
			BitmapFont font = fGenerators.get(fontName).generateFont(parameter);
			fonts.get(fontName).put(size, font);
		}
	}
	
	/**
	 * Dispose fonts and font generators.
	 */
	public void dispose() {
		Gdx.app.debug(getClass().toString(), "dispose()");
		disposeFonts();
		disposeFontGenerators();
	}
	
	private void disposeFonts() {
		Gdx.app.debug(getClass().toString(), "disposeFonts()");
		for(Map<Integer, BitmapFont> fontMap : fonts.values()) {
			for(BitmapFont font : fontMap.values()) {
				font.dispose();
			}
			fontMap.clear();
		}
	}
	
	private void disposeFontGenerators() {
		for(FreeTypeFontGenerator fGen : fGenerators.values()) {
			fGen.dispose();
		}
		fGenerators.clear();
	}

	/**
	 * This method loads TTF Generators only - but don't creaty any fonts. 
	 * Only after getFont(name, size) method font with selected name and size will be created 
	 * and added to the font cache.
	 */
	@Override
	public void loadAll() {
		if (fontDirectory == null) {
			Gdx.app.error(getClass().toString(), "Font directory isn't selected! Interrupt loading fonts");
			return;
		}
		clearLoadQueue();
		fillLoadQueue();
	}
	
	public void clearLoadQueue() {
		if (loadFontQueue == null) {
			loadFontQueue = new Array<String>();
		} else {
			loadFontQueue.clear();
		}
	}
	
	private void fillLoadQueue() {
		for(FileHandle fontHandle : Gdx.files.internal(fontDirectory).list()) {
			if (!fontHandle.isDirectory() && fontHandle.extension().equals("ttf")) {
				loadFontQueue.add(fontHandle.nameWithoutExtension());
			}
		}
		totalFontCount = loadFontQueue.size;
	}

	@Override
	public boolean loadStep() {
		if (loadFontQueue.size > 0) {
			createFontGeneratorIfNeed(loadFontQueue.pop());
			return false;
		} else {
			Gdx.app.debug(getClass().toString(), "All font generators created.");
			return true;
		}
	}

	@Override
	public float getLoadPercent() {
		if (loadFontQueue == null || loadFontQueue.size == 0) {
			return 1;
		} else {
			return (float) fGenerators.size / (float) totalFontCount;
		}
	}
	
	public String[] getFontNames() {
		String[] fontNames = new String[fGenerators.size];
		
		int fontIndex = -1;
		for(String fontName : fGenerators.keys()) {
			fontNames[++fontIndex] = fontName;
		}
		
		return fontNames;
	}

	@Override
	public int getAssetCount() {
		return totalFontCount;
	}

	@Override
	public int getLoadedAssetCount() {
		return fGenerators.size;
	}
	
	public void addFont(String fontName, int fontSize, BitmapFont font) {
		createFontMapIfNeed(fontName);
		if (fonts.get(fontName).get(fontSize) != null) {
			Gdx.app.log("Font manager", "font with " + fontName + " will be overriden by given you font!");
		}
		fonts.get(fontName).put(fontSize, font);
	}
	
	public void addFont(String fontName, int fontSize, FreeTypeFontParameter parameter) {
		createFontGeneratorIfNeed(fontName);
		
	}

	@Override
	public boolean isLoaded(String name) {
		return fGenerators.containsKey(name);
	}
}
