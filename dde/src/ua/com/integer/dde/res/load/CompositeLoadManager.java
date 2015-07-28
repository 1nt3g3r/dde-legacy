package ua.com.integer.dde.res.load;

import com.badlogic.gdx.utils.Array;

public class CompositeLoadManager implements LoadManager {
	private Array<LoadManager> loadManagers = new Array<LoadManager>();
	
	public CompositeLoadManager() {
	}
	
	public CompositeLoadManager(LoadManager ... managers) {
		loadManagers.addAll(managers);
	}
	
	@Override
	public void dispose() {
		for(int i = 0; i < loadManagers.size; i++) {
			loadManagers.get(i).dispose();
		}
	}

	@Override
	public void loadAll() {
		for(int i = 0; i < loadManagers.size; i++) {
			loadManagers.get(i).loadAll();
		}
	}

	@Override
	public boolean loadStep() {
		for(int i = 0; i < loadManagers.size; i++) {
			LoadManager manager = loadManagers.get(i);
			if (manager.getLoadedAssetCount() < manager.getAssetCount()) {
				manager.loadStep();
				return false;
			}
		}
		
		return true;
	}

	@Override
	public float getLoadPercent() {
		return (float) getLoadedAssetCount() / (float) getAssetCount();
	}

	@Override
	public int getAssetCount() {
		int totalAssetCount = 0;
		for(int i = 0; i < loadManagers.size; i++) {
			totalAssetCount += loadManagers.get(i).getAssetCount();
		}
		return totalAssetCount;
	}

	@Override
	public int getLoadedAssetCount() {
		int totalLoadedAssetCount = 0;
		for(int i = 0; i < loadManagers.size; i++) {
			totalLoadedAssetCount += loadManagers.get(i).getLoadedAssetCount();
		}
		return totalLoadedAssetCount;
	}
	
	/**
	 * Возвращает первый LoadManager по его классу. Например, если мы хотим получить экземпляр TextureManager - 
	 * вызываем getManager(TextureManager.class), и он вернет нам экземпляр TextureManager. Если такого менеджера нет, 
	 * он создается, добавляется в список менеджеров, и возвращается
	 */
	@SuppressWarnings("unchecked")
	public <T extends LoadManager> T getManager(Class<T> type) {
		for(int i = 0; i < loadManagers.size; i++) {
			LoadManager loadManager = loadManagers.get(i);
			if (loadManager.getClass() == type) {
				return (T) loadManager;
			}
		}
		
		return addManager(type);
	}
	
	/**
	 * Возвращает все экземпляры менеджеров типа type. Если таких нет, возвращается пустой массив
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends LoadManager> Array<T> getManagers(Class<T> type) {
		Array<T> result = new Array<T>();
		
		for(int i = 0; i < loadManagers.size; i++) {
			LoadManager loadManager = loadManagers.get(i);
			if (loadManager.getClass() == type) {
				result.add((T) loadManager);
			}
		}
		return result;
	}
	
	/**
	 * Добавляет менеджер загрузки в этот менеджер ресурсов. 
	 * Вы передаете лишь класс менеджера загрузки, в этом методе создается его экземпляр. 
	 * Следует заметить, что в этому случае у вашего менеджера загрузки должен быть конструктор по умолчанию - без параметров. 
	 * Позже вы можете обратиться к добавленному менеджеру загрузки через вызов {@link #getManager(Class)}
	 */
	@SuppressWarnings("unchecked")
	public <T extends LoadManager> T addManager(Class<T> type) {
		try {
			LoadManager loadManager = type.newInstance();
			loadManagers.add(loadManager);
			return (T) loadManager;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error while adding " + type + " manager!");
		}
	}

	public void addManager(LoadManager manager) {
		loadManagers.add(manager);
	}

	@Override
	public boolean isLoaded(String name) {
		throw new IllegalStateException("You can't call this method in composite manager!");
	}
	
	public LoadManager getManager(String fullClassName) {
		for(int i = 0; i < loadManagers.size; i++) {
			LoadManager manager = loadManagers.get(i);
			if (manager.getClass().getName().equals(fullClassName)) {
				return manager;
			}
		}
		
		return null;
	}
}
