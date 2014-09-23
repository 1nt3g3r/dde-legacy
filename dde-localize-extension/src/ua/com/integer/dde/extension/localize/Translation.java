package ua.com.integer.dde.extension.localize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Система локализации текста. 
 * Являет собой список тегов, и набор словарей. 
 * Вся система сохраняется в одном файле
 *  
 * @author 1nt3g3r
 *
 */
public class Translation {
	private Array<String> tags;
	private ObjectMap<String, OrderedMap<String, String>> dictionaries;
	private String currentLanguage;
	
	public Translation() {
		dictionaries = new ObjectMap<String, OrderedMap<String,String>>();
		tags = new Array<String>();
	}
	
	/**
	 * Устанавливает текущий язык. 
	 * @param language язык
	 */
	public void setLanguage(String language) {
		this.currentLanguage = language;
	}
	
	/**
	 * Возвращает текущий язык
	 */
	public String getCurrentLanguage() {
		return currentLanguage;
	}
	
	/**
	 * Удаляет неиспользуемые языки. Если перед этим язык не был 
	 * установлен с помощью метода setCurrentLanguage() - вызов этого 
	 * метода не дает никакого эффекта.
	 */
	public void removeUnusedLanguages() {
		if (currentLanguage == null) return;
		
		Array<String> allLanguages = getLanguages();
		allLanguages.removeValue(currentLanguage, false);
		for(String lang : allLanguages) {
			dictionaries.remove(lang);
		}
	}
	
	/**
	 * Возвращает перевод слова для тега. Если перевода для тега нет - 
	 * возвращает этот же тег.
	 * 
	 * @param tag
	 * @return
	 */
	public String translate(String tag) {
		if (!tags.contains(tag, false)) {
			throw new IllegalArgumentException("Tag " + tag + " is not in tag list!");
		}
		
		if (currentLanguage == null) {
			return tag;
		}
		
		ObjectMap<String, String> dictionary = dictionaries.get(currentLanguage);
		if (dictionary == null) {
			return tag;
		} else {
			String translation = dictionary.get(tag);
			if (translation == null) {
				return tag;
			} else {
				return translation;
			}
		}
	}
	
	/**
	 * Добавить тег. После добавления тега сортирует список тегов в алфавитном порядке.
	 */
	public void addTag(String tag) {
		tags.add(tag);
		for(ObjectMap<String, String> dict : dictionaries.values()) {
			if (!dict.containsKey(tag)) {
				dict.put(tag, tag);
			}
		}
		
		tags.sort();
	}
	
	/**
	 * Удалить тег. Также удаляет все переводы для этого тега
	 * @param tag
	 */
	public void removeTag(String tag) {
		tags.removeValue(tag, false);
		for(ObjectMap<String, String> dict : dictionaries.values()) {
			dict.remove(tag);
		}
	}
	
	/**
	 * Возвращает словарь для выбранного языка. Если словаря нет, он создается
	 * 
	 * @param language язык
	 */
	public ObjectMap<String, String> getDictionary(String language) {
		OrderedMap<String, String> dictionary = dictionaries.get(language);
		if (dictionary == null) {
			dictionary = new OrderedMap<String, String>();
			dictionaries.put(language, dictionary);
			for(String tag : tags) {
				dictionary.put(tag, tag);
			}
		}
		return dictionary;
	}
	
	/**
	 * Добавляет перевод слова
	 * @param language языковый словарь
	 * @param tag тэг
	 * @param translate переведенное слово
	 */
	public void addTranslation(String language, String tag, String translate) {
		if (!tags.contains(tag, false)) {
			throw new IllegalArgumentException("Tag " + tag + " is not in tags!");
		}
		getDictionary(language).put(tag, translate);
	}
	
	/**
	 * Список доступных языков
	 */
	public Array<String> getLanguages() {
		Array<String> languages = new Array<String>();
		for(String language : dictionaries.keys()) {
			languages.add(language);
		}
		return languages;
	}
	
	/**
	 * Сохраняет текущий перевод в указанный файл
	 * @throws IOException 
	 */
	public void saveToFile(File file) throws IOException {
		BufferedWriter fWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		fWriter.write(JsonWorker.JSON.prettyPrint(this));
		fWriter.flush();
		fWriter.close();
	}
	
	/**
	 * Загружает перевод из файла
	 */
	public static Translation loadFromFile(File file) {
		return JsonWorker.fromJson(Translation.class, file);
	}

	/**
	 * Загружает перевод из указателя на файл. 
	 * Использовать только в комлекте из libgdx
	 */
	public static Translation loadFromFile(FileHandle internal) {
		if (!internal.file().exists() && Gdx.app.getType() == ApplicationType.Desktop) {
			JsonWorker.toJson(new Translation(), internal.file());
		}
		return JsonWorker.JSON.fromJson(Translation.class, internal);
	}

	/**
	 * Возвращает список тегов
	 */
	public Array<String> getTags() {
		return tags;
	}

	/**
	 * Есть ли такой тег
	 */
	public boolean containsTag(String tagName) {
		return tags.contains(tagName, false);
	}

	/**
	 * Добавляет новый язык
	 */
	public void addLanguage(String language) {
		getDictionary(language);
	}

	/**
	 * Удалить выбранный язык
	 */
	public void deleteLanguage(String selectedLanguage) {
		dictionaries.remove(selectedLanguage);
	}

	/**
	 * Возвращает упорядоченный словарь для выбранного языка
	 * @param language язык
	 * @return упорядоченный словарь или null, если нет перевода для выбранного языка
	 */
	public OrderedMap<String, String> getTranslateMap(String language) {
		return dictionaries.get(language);
	}
}
