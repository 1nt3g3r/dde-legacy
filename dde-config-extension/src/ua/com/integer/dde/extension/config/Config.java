package ua.com.integer.dde.extension.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Конфигурационный файл, который хранит в себе настройки. 
 * Настройки хранятся в виде json файла.
 * Настройки хранятся в виде пар ключ-значение. 
 * Ключи и значения - лишь строкового типа, что связано с ограничением сериализации в json
 * 
 * @author 1nt3g3r
 */
public class Config {
	public OrderedMap<String, String> values;
	public OrderedMap<String, String> descriptions;
	
	/**
	 * Создает пустой конфиг
	 */
	public Config() {
		values = new OrderedMap<String, String>();
		descriptions = new OrderedMap<String, String>();
	}
	
	/**
	 * Устанавливает описание для выбранного свойства. У описания лишь одна цель - 
	 * обьяснить, для чего нужно данное свойство. То есть, оно используется лишь человеком 
	 * для его удобства и может отсутствовать
	 * 
	 * @param name имя свойство
	 * @param description описание свойства
	 */
	public void setDescription(String name, String description) {
		descriptions.put(name, description);
	}
	
	/**
	 * Возвращает описание для выбранного свойства или пустую строку, если описания нет
	 * @param name имя свойства
	 */
	public String getDescription(String name) {
		return descriptions.get(name, "");
	}
	
	/**
	 * Возвращает значение выбранного свойства как строку
	 * @param name имя свойства
	 * @param defValue значение, которое будет возвращено в случае отсутствия свойства с указанным именем
	 */
	public String getString(String name, String defValue) {
		return values.get(name, defValue);
	}
	
	/**
	 * Возвращает значение выбранного свойства как строку
	 * @param name имя свойства
	 */
	public String getString(String name) {
		return getString(name, "");
	}
	
	/**
	 * Устанавливает значения для выбранного свойства как строку
	 */
	public void setString(String name, String value) {
		values.put(name, value);
	}
	
	/**
	 * Возвращает значение выбранного свойства как целочисельное значение
	 * @param name имя свойства
	 * @param defValue значение, которое будет возвращено в случае отсутствия значения для свойства
	 */
	public int getInt(String name, int defValue) {
		return (int) Float.parseFloat(getString(name, defValue + ""));
	}
	
	/**
	 * Возвращает значение выбранного свойства как целочисельное значение
	 * @param name имя свойства
	 */
	public int getInt(String name) {
		return getInt(name, 0);
	}
	
	/**
	 * Устанавливает значение свойства как целочисельный параметр
	 * @param name имя свойства
	 * @param value значение
	 */
	public void setInt(String name, int value) {
		setString(name, value + "");
	}
	
	/**
	 * Возвращает свойство как число с плавающей точкой (дробное)
	 * @param name имя свойства
	 * @param defValue значение, которое будет возвращено в случае отсутствия значения для свойства
	 */
	public float getFloat(String name, float defValue) {
		return Float.parseFloat(getString(name, defValue + ""));
	}
	
	/**
	 * Возвращает свойство как число с плавающей точкой (дробное)
	 * @param name имя свойства
	 */
	public float getFloat(String name) {
		return getFloat(name, 0);
	}
	
	/**
	 * Устанавливает значение для свойства как число с плавающей точкой (дробное)
	 * @param name имя свойства
	 * @param value значение в виде числа с плавающей точкой
	 */
	public void setFloat(String name, float value) {
		setString(name, value + "");
	}
	
	/**
	 * Возвращает значение свойства в виде булевого значения
	 * @param name имя свойства
	 * @param defValue значение, которое будет возвращено в случае отсутсвия значения для свойства
	 */
	public boolean getBoolean(String name, boolean defValue) {
		return Boolean.parseBoolean(getString(name, defValue + ""));
	}
	
	/**
	 * Возвращает значение свойства в виде булевого значения
	 * @param name имя свойства
	 */
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}
	
	/**
	 * Устанавливает значение для выбранного свойства как булево значение
	 * @param name имя свойства
	 * @param value значение свойства в виде булевого (true, false) значения
	 */
	public void setBoolean(String name, boolean value) {
		setString(name, value + "");
	}
	
	/**
	 * Возвращает количество свойств для данного конфига
	 * @return
	 */
	public int getPropertyCount() {
		return values.size;
	}
	
	/**
	 * Сохраняет данный конфиг файл в указанный файл в кодировке utf-8
	 * @param file файл, куда сохраняется конфиг
	 */
	public void saveToFile(File file) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			out.write(JsonWorker.JSON.toJson(this));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Загружает конфиг из существующего файла. Конфиг должен быть в кодировке utf-8
	 * @param file файл с конфигом
	 * @return конфиг или null в случае ошибки
	 */
	public static Config loadFromFile(File file) {
		return JsonWorker.fromJson(Config.class, file);
	}
	
	/**
	 * Загружает конфиг из указателя на файл
	 * @param fileHandle указатель на файл
	 * @return конфиг или null в случае ошибки
	 */
	public static Config loadFromFile(FileHandle fileHandle) {
		return JsonWorker.JSON.fromJson(Config.class, fileHandle.readString("utf-8"));
	}
	
	/**
	 * Возвращает упорядоченный словарь вида "ключ-значени"
	 */
	public OrderedMap<String, String> getValues() {
		return values;
	}

	/**
	 * Удаляет свойство с указанным именем. Также удаляет, если оно было, описание 
	 * для этого свойства
	 * @param selectedPropertyName имя свойства
	 */
	public void deleteProperty(String selectedPropertyName) {
		values.remove(selectedPropertyName);
		descriptions.remove(selectedPropertyName);
	}
}
