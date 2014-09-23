package ua.com.integer.dde.extension.config.editor;

import javax.swing.AbstractListModel;

import ua.com.integer.dde.extension.config.Config;

import com.badlogic.gdx.utils.OrderedMap;

public class ConfigListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 4628649694794932869L;
	
	private OrderedMap<String, Config> configs;
	
	public ConfigListModel(OrderedMap<String, Config> configs) {
		this.configs = configs;
	}

	@Override
	public String getElementAt(int index) {
		return configs.orderedKeys().get(index);
	}

	@Override
	public int getSize() {
		return configs.size;
	}
	
	public Config getConfigAt(int index) {
		return configs.get(configs.orderedKeys().get(index));
	}
	
	public String getConfigNameAt(int index) {
		return configs.orderedKeys().get(index);
	}
}
