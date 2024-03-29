package ua.com.integer.dde.res.load;

import com.badlogic.gdx.utils.Disposable;

/**
 * Интерфейс менеджера загрузки ресурсов.
 * Если ваш менеджер ресурсов хочет правильно работать с {@link ResourceManager}, 
 * он должен реализовать данный интерфейс
 * @author 1nt3g3r
 */
public interface LoadManager extends Disposable {
	/**
	 * Должен добавить все ресурсы в очередь на загрузку, но 
	 * фактически не загружать их
	 */
	public void loadAll();
	/**
	 * Загружает часть ресурсов. Если при этом метод возвращает true, это 
	 * значит, что все ресурсы из данного менеджер успешно загрузились
	 */
	public boolean loadStep();
	/**
	 * Возвращает число в пределах 0..1 - прогресс загрузки
	 */
	public float getLoadPercent();
	/**
	 * Возвращает общее количество элементов в этом менеджере
	 */
	public int getAssetCount();
	/**
	 * Возвращает количество загруженных элементов
	 */
	public int getLoadedAssetCount();
	/**
	 * Загружен ли элемент
	 */
	public boolean isLoaded(String name);
}
