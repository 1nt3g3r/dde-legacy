package ua.com.integer.dde.res.load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

import ua.com.integer.dde.res.load.descriptor.CompositePathDescriptor;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;

public abstract class PathDescriptorLoadManager implements LoadManager {
	protected PathDescriptor descriptor;
	protected Array<String> objectsToLoad = new Array<String>();
	protected ObjectMap<String, Object> loadedObjects = new ObjectMap<String, Object>();
	protected Array<String> extensions = new Array<String>();
	
	private boolean loadingInSeparateThread;
	
	private boolean logEnabled;

	private String currentName;
	
	public void setDescriptor(PathDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public PathDescriptor getDescriptor() {
		return descriptor;
	}
	
	public void addExtension(String extension) {
		extensions.add(extension);
	}
	
	@Override
	public void dispose() {
		for(Object object : loadedObjects.values()) {
			if (object != null && object instanceof Disposable) {
				((Disposable) object).dispose();
			}
		}
		
		loadedObjects.clear();
		objectsToLoad.clear();
	}
	
	/**
	 * Unloads object by his name if it was loaded. 
	 * If it was in load queue, this object just removes from loading queue.
	 * 
	 * @param name of object to unload
	 */
	public void unload(String name) {
		Object object = loadedObjects.get(name);
		if (object != null && object instanceof Disposable) {
			((Disposable) object).dispose();
		}
		
		loadedObjects.remove(name);
		objectsToLoad.removeValue(name, false);
	}

	@Override
	public void loadAll() {
		objectsToLoad.clear();
		loadedObjects.clear();
		
		if(descriptor instanceof CompositePathDescriptor) {
			CompositePathDescriptor compositePathDescriptor = (CompositePathDescriptor) descriptor;
			for(PathDescriptor descriptor: compositePathDescriptor.getDescriptors()) {
				loadAllFromPathDescriptor(descriptor);
			}
		} else {
			loadAllFromPathDescriptor(descriptor);
		}
	}
	
	public void loadAllFromPathDescriptor(PathDescriptor descriptor) {
		for (FileHandle handle : descriptor.getDirectory().list()) {
			if (!handle.isDirectory()) {
				String extension = handle.extension();
				if (extensions.contains(extension, false)) {
					objectsToLoad.add(handle.nameWithoutExtension());
				}
			}
		}
	}

	@Override
	public boolean loadStep() {
		if (getObjectsToLoad().size <= 0) {
			return true;
		}
		
		loadObject(getNextHandle());
		return false;
	}
	
	protected FileHandle getNextHandle() {
		currentName = objectsToLoad.first();
		objectsToLoad.removeIndex(0);
		return getHandle(currentName);
	}
	
	protected String getCurrentName() {
		return currentName;
	}
	
	protected FileHandle getHandle(String name) {
		if (extensions.size == 0) {
			return descriptor.getFile(name);
		} else {
			for(int i = 0; i < extensions.size; i++) {
				String extension = extensions.get(i);
				String fullFileName = name + "." + extension;
				FileHandle result = descriptor.getFile(fullFileName);
				if (result != null && result.exists()) {
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
		if (!objectsToLoad.contains(name, false)) {
			objectsToLoad.add(name);
		}
	}
	
	public void unloadAll(Array<String> assets) {
		for(String name: assets) {
			unload(name);
		}
	}
	
	public void load(Array<String> names) {
		for(String name: names) {
			load(name);
		}
	}
	
	public void add(String name, Object item) {
		loadedObjects.put(name, item);
	}

	@Override
	public boolean isLoaded(String name) {
		return loadedObjects.containsKey(name);
	}
	
	public void startLoadingInSeparateThread() {
		if (!loadingInSeparateThread) {
			loadingInSeparateThread = true;
			new Thread() {
				public void run() {
					while(!loadStep()) {
						log("load step in separate thread");
					}
					loadingInSeparateThread = false;
				}
			}.start();
		}
	}
	
	public boolean isLoadingInSeparateThread() {
		return loadingInSeparateThread;
	}
	
	protected synchronized void loadObject(FileHandle handle) {
		Object item = createItem(handle);
		loadedObjects.put(currentName, item);
		objectsToLoad.removeValue(currentName, false);
	}
	
	protected Object get(String name) {
		if (!loadedObjects.containsKey(name)) {
			objectsToLoad.insert(0, name);
			loadStep();
		}
		
		return loadedObjects.get(name);
	}
	
	public ObjectMap<String, Object> getLoadedObjects() {
		return loadedObjects;
	}
	
	public Array<String> getObjectsToLoad() {
		return objectsToLoad;
	}
	
	public void log(String message) {
		if (logEnabled) {
			Gdx.app.log(getClass().toString(), message);
		}
	}
	
	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}
	
	protected abstract Object createItem(FileHandle handle);
}
