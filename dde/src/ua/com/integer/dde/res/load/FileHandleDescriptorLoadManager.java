package ua.com.integer.dde.res.load;

import ua.com.integer.dde.res.load.descriptor.FilePathDescriptor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class FileHandleDescriptorLoadManager implements LoadManager {
	protected FilePathDescriptor descriptor;
	protected Array<String> objectsToLoad = new Array<String>();
	protected ObjectMap<String, Object> loadedObjects = new ObjectMap<String, Object>();
	protected Array<String> extensions = new Array<String>();
	
	private String currentName;
	
	public void setDescriptor(FilePathDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public FilePathDescriptor getDescriptor() {
		return descriptor;
	}
	
	public void addExtension(String extension) {
		extensions.add(extension);
	}
	
	@Override
	public void dispose() {
		for(Object object : loadedObjects) {
			if (object != null && object instanceof Disposable) {
				((Disposable) object).dispose();
			}
		}
		
		loadedObjects.clear();
	}

	@Override
	public void loadAll() {}

	@Override
	public boolean loadStep() {
		return false;
	}
	
	protected FileHandle getNextHandle() {
		currentName = objectsToLoad.first();
		objectsToLoad.removeIndex(0);
		return getHandle(currentName);
	}
	
	protected String getCurrentFileHandleName() {
		return currentName;
	}
	
	protected FileHandle getHandle(String name) {
		if (extensions.size == 0) {
			return descriptor.getFile(name);
		} else {
			for(String extension: extensions) {
				FileHandle result = descriptor.getFile(name + "." + extension);
				if (result != null) {
					return result;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public float getLoadPercent() {
		return (float) getLoadedAssetCount() / (float) getAssetCount();
	}

	@Override
	public int getAssetCount() {
		return objectsToLoad.size + loadedObjects.size;
	}

	@Override
	public int getLoadedAssetCount() {
		return loadedObjects.size;
	}
	
	public void load(String name) {
		objectsToLoad.add(name);
	}
	
	public void add(String name, Object item) {
		loadedObjects.put(name, item);
	}

	@Override
	public boolean isLoaded(String name) {
		return loadedObjects.containsKey(name);
	}
}
