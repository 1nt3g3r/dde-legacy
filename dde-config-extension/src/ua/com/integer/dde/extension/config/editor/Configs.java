package ua.com.integer.dde.extension.config.editor;

import java.io.File;

import ua.com.integer.dde.extension.config.Config;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;

import com.badlogic.gdx.utils.OrderedMap;

public class Configs {
	private static Configs instance = new Configs();
	private String configDirectory;
	private OrderedMap<String, Config> configs;

	private Configs() {
		initConfigDirectory();
		loadConfigs();
	}
	
	private void initConfigDirectory() {
		String androidProject = ProjectFinder.findAndroidProject();
		if (ProjectFinder.findDesktopProject() != null) {
			configDirectory = androidProject + "/assets/data/configs";
		} else {
			configDirectory = "data/configs";
		}
		
		if (!new File(configDirectory).exists()) {
			new File(configDirectory).mkdirs();
		}
	}
	
	private void loadConfigs() {
		configs = new OrderedMap<String, Config>();
		for(File file : new File(configDirectory).listFiles(new ConfigFileFilter())) {
			String configName = file.getName();
			Config config = Config.loadFromFile(file);
			configs.put(configName.split("\\.")[0], config);
		}
	}
	
	public static Configs getInstance() {
		return instance;
	}

	public Config getConfig(String name) {
		if (configs.get(name) == null) {
			configs.put(name, new Config());
		}
		return configs.get(name);
	}
	
	public void saveConfig(Config config, String name) {
		File fileToSave = new File(configDirectory + "/" + name + ".config");
		if(config == null) {
			config = new Config();
		}
		config.saveToFile(fileToSave);
		
		configs.put(name, config);
	}
	
	public OrderedMap<String, Config> getConfigs() {
		return configs;
	}

	public void deleteConfig(String name) {
		File fileToDelete = new File(configDirectory + "/" + name + ".config");
		fileToDelete.delete();
		
		configs.remove(name);
	}
}
