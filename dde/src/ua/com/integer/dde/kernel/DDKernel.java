package ua.com.integer.dde.kernel;

import ua.com.integer.dde.net.ClientCommandManager;
import ua.com.integer.dde.res.font.TTFFontManager;
import ua.com.integer.dde.res.graphics.TextureManager;
import ua.com.integer.dde.res.load.ResourceManager;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenManager;
import ua.com.integer.dde.res.sound.MusicManager;
import ua.com.integer.dde.res.sound.SoundManager;
import ua.com.integer.dde.ui.UIBuilder;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Ядро игры. Точка доступа ко всем возможностям - 
 * таким, как менедежеры ресурсов. Отличительной особенностью 
 * является управление ресурсами - в зависимости от типа платформы (android, desktop) 
 * и режима запуска (как проект в Eclipse, уже собранное приложение) "подменяет" путь к 
 * некоторым ресурсам - картинки, звуки и т. д.
 * 
 * @author 1nt3g3r
 */
public class DDKernel extends Game {
	private boolean created;
	
	private AssetManager assets;
	
	private ResourceManager resourceManager;

	private ClientCommandManager networkCommandManager;
	private TTFFontManager fontManager;
	private TextureManager textureManager;
	private ScreenManager screenManager;
	private SoundManager soundManager;
	private MusicManager musicManager;
	
	private UIBuilder uiBuilder;
	
	private DDKernelConfig config;
	
	/**
	 * Создает ядро с параметрами по умолчанию
	 */
	public DDKernel() {
		setConfig(new DDKernelConfig());
	}
	
	/**
	 * Создает ядро с ранее настроенными параметрами
	 * 
	 * @param config параметры ядра
	 */
	public void setConfig(DDKernelConfig config) {
		this.config = config;
	}
	
	/**
	 * Возвращает конфигурацию, с которой было создано ядро
	 */
	public DDKernelConfig getConfig() {
		return config;
	}

	/**
	 * Начало жизненного цикла ядра. 
	 * Вызывается один раз при старте игры. 
	 * Здесь создаются и настраиваются все менеджеры ресурсов по умолчанию.
	 * 
	 */
	@Override
	public void create() {
		if (Gdx.app.getType() != ApplicationType.Android) {
			config.addUseDirectory();
		}
		Gdx.input.setCatchBackKey(config.catchBackKey);
		
		assets = new AssetManager();
		
		resourceManager = new ResourceManager();
		
		networkCommandManager = new ClientCommandManager();
		
		AbstractScreen.batch = new SpriteBatch();
		screenManager = new ScreenManager(this);
		resourceManager.addManager(screenManager);

		fontManager = new TTFFontManager();
		resourceManager.addManager(fontManager);
		fontManager.setFontDirectory(config.fontDirectory);
		
		textureManager = new TextureManager(assets);
		resourceManager.addManager(textureManager);
		textureManager.setPackDirectory(config.packDirectory);
		
		soundManager = new SoundManager();
		resourceManager.addManager(soundManager);
		soundManager.setSoundDirectory(config.soundDirectory);
		
		musicManager = new MusicManager();
		resourceManager.addManager(musicManager);
		musicManager.setMusicDirectory(config.musicDirectory);
		
		uiBuilder = new UIBuilder(this);
		AbstractScreen.setKernel(this);
		if (config.stopManageTexturesAfterLoading) {
			Texture.setAssetManager(null);
		}
		
		created = true;
	}
	
	/**
	 * Добавляет экран по его классу. Экран должен иметь конструктор по умолчанию (без параметров), 
	 * иначе будет выброшено исключение
	 * @param screen класс, который должен быть субклассом от AbstractScreen
	 */
	public void addScreen(Class<? extends AbstractScreen> screen) {
		screenManager.loadScreen(screen);
	}
	
	/**
	 * Показывает экран. Если экран ранее не был загружен, загружает его
	 */
	public void showScreen(Class<? extends AbstractScreen> screen) {
		screenManager.showScreen(screen);
	}
	
	/**
	 * Возвращает экран по его классу. Если экран ранее не был загружен, загружает его
	 * @return
	 */
	public <T extends AbstractScreen> T getScreen(Class<T> screen) {
		return screenManager.getScreen(screen);
	}
	
	/**
	 * Возвращает звук по его имени. Если звук не был загружен, загружает его.
	 * 
	 * @param soundName имя звука без расширения .mp3
	 */
	public Sound getSound(String soundName) {
		return soundManager.getSound(soundName);
	}
	
	/**
	 * Возвращает музыку по ее имени. Если музыка не была загружена, загружает эту музыку.
	 * @param musicName название музыки без расширения .mp3
	 * @return
	 */
	public Music getMusic(String musicName) {
		return musicManager.getMusic(musicName);
	}
	
	/**
	 * Возвращает шрифт с указанным именем и указанного размера. Шрифт кэшируется для 
	 * дальнейшего использования
	 * @param name имя шрифта без расширения ttf
	 * @param size высота шрифта в пикселах
	 */
	public BitmapFont getFont(String name, int size) {
		return fontManager.getFont(name, size);
	}
	
	/**
	 * Возвращает стандартный шрифт (Roboto-Condensed), который встроен в библиотеку 
	 * с указанным размером. Шрифт кэшируется для дальнейшего использования
	 * @param size высота шрифта в пикселах
	 * @return
	 */
	public BitmapFont getFont(int size) {
		return fontManager.getFont(size);
	}
	
	/**
	 * Возвращает регион текстуры с указанного атласа с указанным именем. 
	 * Например, если есть атлас test и в этом атласе картинка img - вызов будет следующим: 
	 * getRegion("test", "img");
	 * 
	 * @param pack имя атласа
	 * @param region имя региона без расширения
	 * @return
	 */
	public TextureRegion getRegion(String pack, String region) {
		return textureManager.get(pack, region);
	}
	
	/**
	 * Возвращает менеджера для работы с сетью. Перед началом работы 
	 * менеджера необходимо инициализировать
	 */
	public ClientCommandManager getNetworkManager() {
		return networkCommandManager;
	}
	
	/**
	 * Возвращает менеджера ресурсов - класс, который управляет 
	 * всеми доступными менеджерами загрузки (такими, как менеджер текстур, 
	 * звуков и т. д.)
	 */
	public ResourceManager getResourceManager() {
		return resourceManager;
	}
	
	/**
	 * Возвращает вспомогательный класс для облегчения конструирования 
	 * некоторых элементов пользовательського интерфейса - текстовых меток, например
	 */
	public UIBuilder ui() {
		return uiBuilder;
	}

	/**
	 * Инициализировано ли ядро. Вызов этого метода вернет true лишь 
	 * в том случае, если был вызван метод create() - то есть, ядро успешно инициализировалось
	 */
	public boolean isCreated() {
		return created;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		getResourceManager().dispose();
	}
}
