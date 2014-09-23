package ua.com.integer.dde.extension.config.editor;

import javax.swing.AbstractListModel;

import ua.com.integer.dde.extension.config.Config;

public class PropertyListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 1665129404281530915L;
	
	private Config config;

	public PropertyListModel(Config config) {
		this.config = config;
	}
	
	@Override
	public String getElementAt(int index) {
		return config.getValues().orderedKeys().get(index);
	}

	@Override
	public int getSize() {
		return config.getValues().size;
	}
	
	public String getValueAt(int index) {
		return config.getValues().get(config.getValues().orderedKeys().get(index));
	}
	
	public String getDescriptionAt(int index) {
		return config.getValues().get(config.getValues().orderedKeys().get(index));
	}
	
	public String getDescriptionFor(String key) {
		return config.getDescription(key);
	}
}
