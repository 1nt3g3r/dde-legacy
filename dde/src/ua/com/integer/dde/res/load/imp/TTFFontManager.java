package ua.com.integer.dde.res.load.imp;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ObjectMap;

public class TTFFontManager extends PathDescriptorLoadManager {
	public static final String DELIMITER = "##";
	public static final String DEFAULT_CHARS = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯяabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>’";
	public static final int MAX_FONT_TEXTURE_SIZE = 2048;
	
	private ObjectMap<String, FreeTypeFontGenerator> fontGenerators = new ObjectMap<String, FreeTypeFontGenerator>();
	private FreeTypeFontParameter lastParameter;
	private String lastShortName;

	public class FontBuilder {
		private FreeTypeFontParameter parameter;
		private String name;
		
		public FontBuilder() {
			parameter = new FreeTypeFontParameter();
			parameter.characters = DEFAULT_CHARS;
			parameter.incremental = true;
		}
		
		public FontBuilder chars(String chars) {
			parameter.characters = chars;
			return this;
		}
		
		public FontBuilder linearFilter() {
			return filter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		public FontBuilder filter(TextureFilter minFilter, TextureFilter magFilter) {
			parameter.minFilter = minFilter;
			parameter.magFilter = magFilter;
			return this;
		}
		
		public FontBuilder flip(boolean flip) {
			parameter.flip = flip;
			return this;
		}
		
		public FontBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public FontBuilder size(int size) {
			parameter.size = size;
			return this;
		}
		
		public FontBuilder border(Color color, float width) {
			parameter.borderColor = color;
			parameter.borderWidth = width;
			return this;
		}
		
		public FontBuilder borderStraight(boolean straight) {
			parameter.borderStraight = straight;
			return this;
		}
		
		public FontBuilder shadow(Color color, int offsetX, int offsetY) {
			parameter.shadowColor = color;
			parameter.shadowOffsetX = offsetX;
			parameter.shadowOffsetY = offsetY;
			return this;
		}
		
		public FontBuilder incremental(boolean incremental) {
			parameter.incremental = incremental;
			return this;
		}
		
		public FreeTypeFontParameter build() {
			return parameter;
		}
		
		public FontBuilder addToLoad() {
			load(name, build());
			return this;
		}
		
		public BitmapFont get() {
			return getFont(name, build());
		}
	}
	
	public TTFFontManager() {
		this(null);
	}
	
	public TTFFontManager(PathDescriptor descriptor) {
		FreeTypeFontGenerator.setMaxTextureSize(MAX_FONT_TEXTURE_SIZE);
		
		setDescriptor(descriptor);
		
		addExtension("ttf");
	}
	
	public FontBuilder builder(String fontName) {
		return new FontBuilder().name(fontName);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		disposeFontGenerators();
	}

	private void disposeFontGenerators() {
		for(FreeTypeFontGenerator generator: fontGenerators.values()) {
			generator.dispose();
		}
		fontGenerators.clear();
	}
	
	@Override
	protected FileHandle getHandle(String name) {
		String[] nameParts = name.split(DELIMITER);
		String param = nameParts[nameParts.length - 1];
		lastShortName = nameParts[0];
		for(int i = 1; i < nameParts.length - 1; i++) {
			lastShortName = lastShortName + DELIMITER + nameParts[i];
		}
		
		lastParameter = jsonToParam(param);
		
		return super.getHandle(lastShortName);
	}
	
	
	@Override
	protected Object createItem(FileHandle handle) {
		loadFontGeneratorIfNeed(handle);
		BitmapFont result = fontGenerators.get(lastShortName).generateFont(lastParameter);
		return result;
	}
	
	private void loadFontGeneratorIfNeed(FileHandle handle) {
		if (!fontGenerators.containsKey(lastShortName)) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(handle);
			fontGenerators.put(lastShortName, generator);
		}
	}
	
	private FreeTypeFontParameter jsonToParam(String json) {
		return JsonWorker.JSON.fromJson(FreeTypeFontParameter.class, json);
	}
	
	private String paramToJson(FreeTypeFontParameter param) {
		return JsonWorker.JSON.toJson(param);
	}
	
	@Override
	public void load(String name) {
		throw new IllegalStateException("You can't call this method!");
	}
	
	public void load(String name, int size) {
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.characters = DEFAULT_CHARS;
		
		load(name, param);
	}
	
	public void load(String name, FreeTypeFontParameter param) {
		String loadQuery = createFullQuery(name, param);
		super.load(loadQuery);
	}
	
	public BitmapFont getFont(String name, int size) {
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.characters = DEFAULT_CHARS;
		
		String query = createFullQuery(name, param);
		
		return (BitmapFont) get(query);
	}
	
	public BitmapFont getFont(String name, FreeTypeFontParameter param) {
		String fullQuery = createFullQuery(name, param);
		return (BitmapFont) get(fullQuery);
	}
	
	protected String createFullQuery(String name, FreeTypeFontParameter param) {
		String paramText = paramToJson(param);
		String result = name + DELIMITER + paramText;
		return result;
	}
	
	@Override
	public void add(String name, Object item) {
		throw new IllegalArgumentException("You can't call this method in FontManager!");
	}
	
	@Override
	public boolean isLoaded(String name) {
		throw new IllegalArgumentException("You can't call this method in FontManager!");
	}
}
