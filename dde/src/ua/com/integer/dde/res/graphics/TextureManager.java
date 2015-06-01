package ua.com.integer.dde.res.graphics;

import ua.com.integer.dde.res.load.LoadManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Менеджер текстур загружает изображения из ".pack" файлов. Вы 
 * можете создать такие файлы с использованием 
 * встроенного редактора текстур или же используя GdxTexturePacker - расширение для Libgdx. 
 * Менеджер поддерживает "ленивую загрузку" - если вы пытаетесь получить картинку из какого-либо 
 * атласа, а он еще не загружен, этот атлас будет подгружен "на лету".
 * 
 * Менеджер ресурсов работает поверх стандартного менеджера ресурсов AssetManager.
 * 
 * @author 1nt3g3r
 */
public class TextureManager implements LoadManager {
	private AssetManager assets;
	private Array<String> loadedPacks;
	
	private String packDirectory;
	
	private ObjectMap<String, TextureAtlas> customAtlases = new ObjectMap<String, TextureAtlas>(); 
	
	/**
	 * Создает менеджера, используя экземпляр {@link AssetManager}.
	 */
	public TextureManager(AssetManager assets) {
		if (assets == null) {
			throw new IllegalArgumentException("AssetManager instance can't be null!");
		}
		
		this.assets = assets;
		loadedPacks = new Array<String>();
	}
	
	/**
	 * Устанавливает базовую директорию. В этой директории должны находиться поддиректории с названиями в формате  
	 * "images"-"высота разрешения". Например, для разрешения 800*480 вы должны создать директорию
	 * images-480 и положить ваши атласы (.pack-файлы) внутрь нее. Менеджер автоматически выбирает наиболее подходящее 
	 * разрешение и загружает атласы оттуда.
	 * 
	 * @param packDirectory относительный путь - например, "data/img_packs"
	 */
	public void setPackDirectory(String packDirectory) {
		this.packDirectory = packDirectory;
		
		if (packDirectory == null || !Gdx.files.internal(packDirectory).exists()) {
			Gdx.app.error(getClass()+"", "Image directory isn't selected or doesn't exists!");
			return;
		}
		
		Array<Integer> possibleHeights = new Array<Integer>();
		for(FileHandle handle : Gdx.files.internal(packDirectory).list()) {
			if (handle.isDirectory() && handle.name().startsWith("images-")) {
				int height = Integer.parseInt(handle.name().split("-")[1]);
				possibleHeights.add(height);
			}
		}
		possibleHeights.sort();
		possibleHeights.reverse();

		int screenHeight = Gdx.graphics.getHeight();
		
		if (possibleHeights.size != 0) {
			int currentHeight = possibleHeights.first();
			for (int h : possibleHeights) {
				if (h >= screenHeight) {
					currentHeight = h;
				}
			}
			this.packDirectory += ("/images-" + currentHeight);
		}
	}
	
	/**
	 * Устанавливает необходимый набор атласов. Если атлас есть в этом списке, но еще не был загружен - 
	 * он загружается. Если атлас был загружен, но его нет в этом списке - он выгружается.
	 */
	public void setNecessaryPacks(Array<String> necessaryPacks) {
		if (packDirectory == null) { 
			throw new IllegalStateException("You should set up packDirectory before use this method");
		}
		
		for(String needPack : necessaryPacks) {
			if (!loadedPacks.contains(needPack, false)) {
				loadPack(needPack);
			}
		}
		
		for(String loadedPack : loadedPacks) {
			if (!necessaryPacks.contains(loadedPack, false)) {
				unloadPack(loadedPack);
			}
		}
	}
	
	/**
	 * Ищет все .pack файлы в наиболее подходящей директории и ставит их в очередь на загрузку
	 */
	@Override
	public void loadAll() {
		if (packDirectory == null || !Gdx.files.internal(packDirectory).exists()) {
			Gdx.app.error(getClass()+"", "Image directory isn't selected or doesn't exists!");
			return;
		}
		
		for(FileHandle file : Gdx.files.internal(packDirectory).list()) {
			if (!file.isDirectory() && file.extension().equals("pack")) {
				loadPack(file.nameWithoutExtension());
			}
		}
	}
	
	/**
	 * @return список загруженных атласов
	 */
	public Array<String> getLoadedPacks() {
		return loadedPacks;
	}
	
	public ObjectMap<String, TextureAtlas> getCustomPacks() {
		return customAtlases;
	}
	
	/**
	 * Возвращает список регионов текстур в выбранном атласе
	 * @param packName имя атласа
	 */
	public Array<AtlasRegion> getRegionsFromPack(String packName) {
		if (customAtlases.containsKey(packName)) {
			return getAtlas(packName).getRegions();
		}
		
		if (!isPackLoaded(packName)) {
			throw new IllegalStateException("Pack " + packName + " isn't loaded!");
		}
		
		TextureAtlas atlas = assets.get(packDirectory + "/" + packName, TextureAtlas.class);
		return atlas.getRegions();
	}
	
	/**
	 * Загружает атлас в память. В общем случае вы не должны вызывать этот метод вручную - 
	 * менеджер сам позаботится о его вызове в нужный момент.
	 * @param packName имя атласа
	 */
	public void loadPack(String packName) {
		if (customAtlases.containsKey(packName)) {
			return;
		}
		
		assets.load(packDirectory + "/" + packName + ".pack", TextureAtlas.class);
		loadedPacks.add(packName);
	}
	
	/**
	 * Существует ли атлас с указанным именем
	 * @param packName имя атласа
	 * @return true если атлас существует
	 */
	public boolean packExists(String packName) {
		if (customAtlases.containsKey(packName)) {
			return true;
		}
		
		return Gdx.files.internal(packDirectory + "/" + packName + ".pack").exists();
	}
	
	/**
	 * Существует ли регион с указанным именем в указанном атласе. 
	 * У метода есть побочный эффект - если атлас существует и ранее не был загружен в оперативную память, 
	 * он загружается
	 * 
	 * @param packName имя атласа
	 * @param regionName имя региона
	 * @return true, если регион с указанным именем существует
	 */
	public boolean regionExists(String packName, String regionName) {
		if(!packExists(packName)) return false;
		
		loadPack(packName);
		
		return getAtlas(packName).findRegion(regionName) != null;
	}
	
	/**
	 * Выгружает атлас из памяти
	 * @param packName имя атласа
	 */
	public void unloadPack(String packName) {
		if (customAtlases.containsKey(packName)) {
			customAtlases.get(packName).dispose();
			customAtlases.remove(packName);
			return;
		}
		
		if (isPackLoaded(packName)) {
			assets.unload(packDirectory + "/" + packName + ".pack");
			assets.finishLoading();
			loadedPacks.removeValue(packName, false);
		}
	}
	
	/**
	 * Возвращает {@link AtlasRegion} из выбранного атласа. Если атлас не был загружен, атлас загружается в память 
	 * автоматически. 
	 * Имя атласа и имя картинки указываются без расширений - например, get("test", "image").
	 * 
	 * @param packName имя атласа с нужным регионом
	 * @param regionName имя нужного региона текстуры
	 */
	public AtlasRegion get(String packName, String regionName) {
		TextureAtlas atlas = getAtlas(packName);
		AtlasRegion region = atlas.findRegion(regionName);
		
		if (region == null) {
			throw new IllegalArgumentException("Region " + regionName + " not found in pack " + packName);
		}
		
		return region;
	}
	
	/**
	 * Возвращает атлас текстуры по его имени
	 * @param packname имя атласа
	 */
	public TextureAtlas getAtlas(String packname) {
		if (customAtlases.containsKey(packname)) {
			return customAtlases.get(packname);
		}

		if (!isPackLoaded(packname)) {
			loadPack(packname);
			assets.finishLoading();
		}
		
		return assets.get(packDirectory + "/" + packname+".pack", TextureAtlas.class);
	}
	
	private boolean isPackLoaded(String packName) {
		return assets.isLoaded(packDirectory + "/" + packName+".pack") || customAtlases.containsKey(packName);
	}

	@Override
	public boolean loadStep() {
		return assets.update();
	}

	@Override
	public float getLoadPercent() {
		return assets.getProgress();
	}

	@Override
	public void dispose() {
		for(String pack : loadedPacks) {
			unloadPack(pack);
		}
		
		disposeCustomAtlases();
		
		assets.dispose();
		assets = null;
		
		Gdx.app.log("dde", "Texture manager dispose!");
	}

	private void disposeCustomAtlases() {
		for(TextureAtlas customAtlas : customAtlases.values()) {
			customAtlas.dispose();
		}
		customAtlases.clear();
	}

	@Override
	public int getAssetCount() {
		return loadedPacks.size;
	}

	@Override
	public int getLoadedAssetCount() {
		int result = 0;
		for(String pack : loadedPacks) {
			if (isPackLoaded(pack)) {
				result ++;
			}
		}
		return result;
	}
	
	public void addAtlas(String atlasName, TextureAtlas atlas) {
		customAtlases.put(atlasName, atlas);
	}

	@Override
	public boolean isLoaded(String name) {
		return isPackLoaded(name);
	}
}
