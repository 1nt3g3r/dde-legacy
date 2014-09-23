package ua.com.integer.dde.extension.config;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Точка доступа - синглтон для всех конфигов
 * 
 * @author 1nt3g3r
 */
public class Configs {
	private static Configs instance = new Configs();
	private String configDirectory;
	private ObjectMap<String, Config> configs;

	private Configs() {
		setKernel(AbstractScreen.getKernel());
	}
	
	/**
	 * Устанавливает ядро. Это инициализирует загрузку всех конфигов. 
	 * В нормальных условиях ядро берется при первом создании экземпляра данного класса 
	 * вызовом метода AbstractScreen.getKerne(). Таким образом, в обычных условиях нет надобности 
	 * вызывать данный метод
	 * 
	 * @param kernel ядро. Если будет null, вызов данного метода игнорируется
	 */
	public void setKernel(DDKernel kernel) {
		if (kernel == null) return;
		
		configDirectory = kernel.getConfig().relativeDirectory + "data/configs";
		loadConfigs();
	}
	
	private void loadConfigs() {
		configs = new ObjectMap<String, Config>();
		for(FileHandle file : Gdx.files.internal(configDirectory).list("config")) {
			String configName = file.nameWithoutExtension();
			Config config = Config.loadFromFile(file);
			configs.put(configName, config);
		}
	}
	
	/**
	 * Возвращает экземпляр данного класса
	 */
	public static Configs getInstance() {
		return instance;
	}

	/**
	 * Возвращает конфиг по его имени. Если конфига нет, 
	 * автоматически создается пустой конфиг с указанным именем.
	 * 
	 * @param name имя конфига
	 * @return существующий или автоматически созданный конфиг
	 */
	public Config getConfig(String name) {
		if (configs == null) {
			throw new IllegalStateException("call setKernel() method before");
		}
		if (configs.get(name) == null) {
			configs.put(name, new Config());
		}
		return configs.get(name);
	}
}
