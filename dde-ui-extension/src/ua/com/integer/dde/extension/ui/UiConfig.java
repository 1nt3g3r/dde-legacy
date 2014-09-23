package ua.com.integer.dde.extension.ui;

import java.io.File;

import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Конфиг виджета. Из обязательных параметров - имя, 
 * размеры и положение на экране. 
 * Остальные настройки специфические для актеров
 * 
 * @author 1nt3g3r
 */
public class UiConfig {
	public String name;
	
	public WidgetType widgetType = WidgetType.TEXTURE_REGION_GROUP_ACTOR;
	public Side parentCorner = Side.CENTER;
	public Side targetCorner = Side.CENTER;
	public Size horizontalDistance = new Size();
	public Size verticalDistance = new Size();
	public Size width = new Size();
	public Size height = new Size();
	
	public Array<UiConfig> children = new Array<UiConfig>();
	
	public ObjectMap<String, String> properties = new ObjectMap<String, String>();
	
	public UiConfig() {
		width.setSizeValue(0.5f);
		height.setSizeValue(0.5f);
	}
	
	public void saveToFile(File file) {
		JsonWorker.toJson(this, file);
	}

	public static UiConfig fromFileHandle(FileHandle handle) {
		return JsonWorker.JSON.fromJson(UiConfig.class, handle);
	}
	
	public static UiConfig fromFile(File file) {
		return JsonWorker.fromJson(UiConfig.class, file);
	}
	
	@Override
	public String toString() {
		return name + "";
	}
	
	/**
	 * Установить значение свойства
	 * @param name имя
	 * @param value значение
	 */
	public void set(String name, String value) {
		properties.put(name, value);
	}
	
	/**
	 * Возвращает значение свойства
	 * @param name имя свойства
	 * @param defValue строка по умолчанию - возвращается, если нет значения для заданного имени свойства
	 */
	public String get(String name, String defValue) {
		return properties.get(name, defValue);
	}
	
	/**
	 * Возвращает значение свойства с указанным именем. Если нет значения для заданного свойства, 
	 * возвращается пустая строка
	 * 
	 * @param name имя свойства
	 */
	public String get(String name) {
		return get(name, "");
	}

	public void print() {
		System.out.println("Config: " + name);
		System.out.println(properties);
	}
}
