package ua.com.integer.dde.extension.resource.monitor;

import javax.swing.AbstractListModel;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;

public class ResourceListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 383933269860710228L;
	private PathDescriptorLoadManager manager;
	private Array<String> items = new Array<String>();
	
	public ResourceListModel(PathDescriptorLoadManager manager) {
		this.manager = manager;
		update();
	}
	
	@Override
	public int getSize() {
		return items.size;
	}

	@Override
	public String getElementAt(int index) {
		return items.get(index);
	}
	
	public void update() {
		items.clear();
		ObjectMap<String, Object> loadedItems = manager.getLoadedObjects();
		for(String item: loadedItems.keys()) {
			items.add(item);
		}
	}

}
