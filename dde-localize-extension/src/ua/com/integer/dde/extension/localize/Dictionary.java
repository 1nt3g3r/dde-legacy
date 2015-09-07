package ua.com.integer.dde.extension.localize;

import java.io.File;

import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public class Dictionary {
	public String language = "";
	public OrderedMap<String, String> translation = new OrderedMap<String, String>();
	
	public void synchronizeTags(Array<String> tags) {
		for(String tag: tags) {
			if (!translation.containsKey(tag)) {
				translation.put(tag, tag);
			}
		}
	}
	
	public void addTag(String tag) {
		if (translation.containsKey(tag)) {
			return;
		}
		
		translation.put(tag, tag);
	}
	
	public String toJson() {
		return JsonWorker.JSON.toJson(this);
	}
	
	public void saveToFile(File file) {
		JsonWorker.toJson(this, file);
	}
	
	public static Dictionary fromJson(File file) {
		return JsonWorker.fromJson(Dictionary.class, file);
	}
	
	public static Dictionary fromJson(FileHandle handle) {
		return JsonWorker.JSON.fromJson(Dictionary.class, handle);
	}

	public void removeTag(String tagName) {
		translation.remove(tagName);
	}
}
