package ua.com.integer.dde.res.load;

import com.badlogic.gdx.Gdx;
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
		for(LoadManager manager : loadManagers) {
			manager.dispose();
		}
	}

	@Override
	public void loadAll() {
		for(LoadManager manager: loadManagers) {
			manager.loadAll();
		}
	}

	@Override
	public boolean loadStep() {
		for(LoadManager manager: loadManagers) {
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
		for(LoadManager manager : loadManagers) {
			totalAssetCount += manager.getAssetCount();
		}
		return totalAssetCount;
	}

	@Override
	public int getLoadedAssetCount() {
		int totalLoadedAssetCount = 0;
		for(LoadManager manager: loadManagers) {
			totalLoadedAssetCount += manager.getLoadedAssetCount();
		}
		return totalLoadedAssetCount;
	}
	
	/**
	 * Возвращает первый LoadManager по его классу. Например, если мы хотим получить экземпляр TextureManager - 
	 * вызываем getManager(TextureManager.class), и он вернет нам экземпляр TextureManager. Если же нет обьекта для 
	 * запрошенного класса (вы не добавили ранее этот менеджер загрузки), будет брошено исключение IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public <T extends LoadManager> T getManager(Class<T> type) {
		for(LoadManager loadManager : loadManagers) {
			if (loadManager.getClass() == type) {
				return (T) loadManager;
			}
		}
		throw new IllegalArgumentException("No load manager for this class!");
	}
	
	/**
	 * Возвращает все экземпляры типа type. Если таких нет, возвращается пустой массив
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends LoadManager> Array<T> getManagers(Class<T> type) {
		Array<T> result = new Array<T>();
		for(LoadManager loadManager : loadManagers) {
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
	public <T extends LoadManager> void addManager(Class<T> type) {
		try {
			LoadManager loadManager = type.newInstance();
			loadManagers.add(loadManager);
		} catch (Exception e) {
			Gdx.app.error("ResourceManager", "Error during loading " + type + " manager");
		}
	}

	public void addManager(LoadManager manager) {
		loadManagers.add(manager);
	}

	@Override
	public boolean isLoaded(String name) {
		throw new IllegalStateException("You can't call this method in composite manager!");
	}
}
