package ua.com.integer.dde.extension.ui.actor;

import ua.com.integer.dde.extension.ui.actor.shadowlabel.ShadowLabel;
import ua.com.integer.dde.extension.ui.actor.shadowlabel.ShadowLabelPropertySupporter;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class DDEExtensionActors {
	private ObjectMap<String, Class<? extends Actor>> actors = new ObjectMap<String, Class<? extends Actor>>();
	private ObjectMap<String, PropertySupporter> suppporters = new ObjectMap<String, PropertySupporter>();
	private ObjectMap<String, String> descriptions = new ObjectMap<String, String>();
	private ObjectMap<String, Array<String>> categories = new ObjectMap<String, Array<String>>();
	
	private static DDEExtensionActors instance = new DDEExtensionActors();
	
	private Array<String> tmpCategories = new Array<String>();
	private Array<String> tmpNamesFromCategories = new Array<String>();
	
	private DDEExtensionActors() {
		register(ShadowLabel.ID, ShadowLabel.class, ShadowLabel.DESCRIPTION, ShadowLabel.CATEGORY, new ShadowLabelPropertySupporter());
	}

	public static DDEExtensionActors getInstance() {
		return instance;
	}
	
	public void register(String id, Class<? extends Actor> actor, String description, String category, PropertySupporter supporter) {
		actors.put(id, actor);
		descriptions.put(id, description);
		suppporters.put(id, supporter);
		
		getCategoryList(category).add(id);
	}
	
	private Array<String> getCategoryList(String category) {
		Array<String> result = categories.get(category);
		if (result == null) {
			result = new Array<String>();
			categories.put(category, result);
		}
		return result;
	}
	
	public Array<String> getCategories() {
		tmpCategories.clear();
		for(String category: categories.keys()) {
			tmpCategories.add(category);
		}
		return tmpCategories;
	}
	
	public Array<String> getActorsFromCategory(String category) {
		tmpNamesFromCategories.clear();
		for(String id: categories.get(category)) {
			tmpNamesFromCategories.add(descriptions.get(id));
		}
		return tmpNamesFromCategories;
	}
	
	public Actor create(String id) {
		System.out.println("create for " + id);
		try {
			return actors.get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

	public PropertySupporter getSupporter(String extensionId) {
		return suppporters.get(extensionId);
	}

	public boolean hasActors() {
		return descriptions.size > 0;
	}

	private Array<String> tmpIdsFromCategory = new Array<String>();
	public Array<String> getIdsFromCategory(String category) {
		tmpIdsFromCategory.clear();
		for(String id: categories.get(category)) {
			tmpIdsFromCategory.add(id);
		}
		return tmpIdsFromCategory;
	}

	public String getDescription(String id) {
		return descriptions.get(id);
	}
}
