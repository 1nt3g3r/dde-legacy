package ua.com.integer.dde.extension.localize;

import java.io.File;

import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;
import ua.com.integer.dde.startpanel.util.ExtensionFilenameFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Система локализации. Использует json файлы для хранения переводов в виде "ключ-значение". 
 * Каждый язык перевода хранится в отдельном файле.
 * 
 * Реализует паттерн проектирования "Одиночка"
 * 
 * @author 1nt3g3r
 */
public class Localize {
	public static final String INNER_LOCALIZE_FILENAME = "translation.dictionary";
	public String innerLocalizeFolder = "data/dde-translation";
	
	private static Localize instance = new Localize();
	
	private String currentLanguage;
	
	private Array<String> tags = new Array<String>();
	private Array<String> languages = new Array<String>();

	private ObjectMap<String, Dictionary> dictionaries = new ObjectMap<String, Dictionary>();
	
	private StringBuilder tmpTranslateStringBuilder = new StringBuilder();
	private StringBuilder tmpParamStringBuilder = new StringBuilder();
	
	private Localize() {}
	
	/**
	 * Установить путь к директории по умолчанию, где храняться словари
	 * 
	 * @param innerLocalizeFolder
	 */
	public void setInnerLocalizeFolder(String innerLocalizeFolder) {
		this.innerLocalizeFolder = innerLocalizeFolder;
		setLanguage("en");
	}
	
	/**
	 * Получить экземпляр класса
	 */
	public static Localize getInstance() {
		return instance;
	}
	
	/**
	 * Возвращает перевод слова по его тэгу.
	 * 
	 * Если словарь для текущего языка не найден, или же тэг в словаре отсутствует, 
	 * возвращается этот же тэг
	 * 
	 * @param tag имя тега
	 */
	public String translate(String tag) {
		if (currentLanguage == null) {
			return tag;
		}
		
		Dictionary dictionary = dictionaries.get(currentLanguage);
		if (dictionary == null) {
			return tag;
		}
		
		String translation = dictionary.translation.get(tag);
		if (translation == null) {
			return tag;
		}
		
		return translation;
		
	}
	
	/**
	 * Возвращает перевод слова, заменяя при этом параметры на указанные значения.
	 * 
	 * Параметры - это строки вида $param$.
	 * 
	 * Например, есть строка "На дереве висит $count$ яблок". Если вызвать метод с параметрами 
	 * replace.put("count", "5") - мы получим "На дереве висит 5 яблок".
	 * 
	 * @param tag строка для перевода
	 * @param replace карта замены - что на что заменять
	 */
	public String translate(String tag, ObjectMap<String, String> replace) {
		tmpTranslateStringBuilder.setLength(0);
		tmpTranslateStringBuilder.append(translate(tag));
		
		for(String key: replace.keys()) {
			tmpParamStringBuilder.setLength(0);
			tmpParamStringBuilder.append("$").append(key).append("$");
			String escapedKey = tmpParamStringBuilder.toString();
			
			int replaceIndex = tmpTranslateStringBuilder.indexOf(escapedKey);
			tmpTranslateStringBuilder.replace(replaceIndex, replaceIndex + escapedKey.length(), replace.get(key));
		}
		return tmpTranslateStringBuilder.toString();
	}
	
	/**
	 * Устанавливает язык. Список доступных языков можно получить с помощью метод getLanguages() класса Translation. 
	 * Например, Localize.getInstance().getTranslation().getLanguages() 
	 * @param language имя языка
	 */
	public void setLanguage(String language) {
		this.currentLanguage = language;
		
		if (dictionaries.get(language) == null) {
			try {
				loadLocalDictionary(language);
			} catch(Exception ex) {
				System.out.println("Can't load inner localization for language " + language);
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Возвращает список всех доступных тегов. Результат кэшируется, 
	 * при изменении списка тегов кеш обновляется
	 */
	public Array<String> getTags() {
		return tags;
	}
	
	private void updateTags() {
		tags.clear();
		for(Dictionary dict: dictionaries.values()) {
			for(String tag: dict.translation.keys()) {
				if (!tags.contains(tag, false)) {
					tags.add(tag);
				}
			}
		}
		
		for(Dictionary dict: dictionaries.values()) {
			dict.synchronizeTags(tags);
		}
		
		tags.sort();
	}
	
	/**
	 * Возвращает список тегов как массив
	 */
	public String[] getTagsAsArray() {
		Array<String> tags = getTags();
		String[] result = new String[tags.size];
		for(int i = 0; i < tags.size; i++) {
			result[i] = tags.get(i);
		}
		return result;
	}
	
	/**
	 * Возвращает список всех доступных языков. При изменении списка языков 
	 * кеш обновляется
	 * @return
	 */
	public Array<String> getLanguages() {
		languages.clear();
		for(String lang: dictionaries.keys()) {
			languages.add(lang);
		}
		return languages;
	}

	/**
	 * Загружает словарь для указанного языка с внутреннего хранилища
	 */
	public void loadLocalDictionary(String language) {
		String fullFilename = language + "-" + INNER_LOCALIZE_FILENAME;
		String relativeDirectory = "";
		if (AbstractScreen.getKernel() != null) {
			relativeDirectory = AbstractScreen.getKernel().getConfig().relativeDirectory;
			loadDictionary(Gdx.files.internal(relativeDirectory + innerLocalizeFolder + "/" + fullFilename));
		} else {
			new File(innerLocalizeFolder).mkdirs();
			loadDictionary(new File(innerLocalizeFolder + "/" + fullFilename));
		}
	}
	
	/**
	 * Загружает словарь из указанного файла
	 */
	public void loadDictionary(File file) {
		if (file.exists()) {
			addDictionary(Dictionary.fromJson(file));
		}
	}
	
	/**
	 * Загружает словарь из указанного файла
	 */
	public void loadDictionary(FileHandle handle) {
		if (handle.exists()) {
			addDictionary(Dictionary.fromJson(handle));
		}
	}
	
	/**
	 * Добавляет словарь. Если словарь с указанным языком существует, 
	 * то словари обьединяются
	 */
	public void addDictionary(Dictionary dictionary) {
		Dictionary currentDictionary = dictionaries.get(dictionary.language);
		if (currentDictionary != null) {
			for(String tag: dictionary.translation.keys()) {
				currentDictionary.translation.put(tag, dictionary.translation.get(tag));
			}
		} else {
			dictionaries.put(dictionary.language, dictionary);
		}
		
		updateTags();
	}
	
	/**
	 * Сохраняет все словари в указанную папку в формате language-translation.dictionary
	 */
	public void saveAllToDirectory(File relativePath) {
		for(String language: getLanguages()) {
			dictionaries.get(language).saveToFile(new File(relativePath, language + "-" + INNER_LOCALIZE_FILENAME));
		}
	}

	/**
	 * Есть ли такой тэг
	 */
	public boolean containsTag(String tagName) {
		return tags.contains(tagName, false);
	}

	/**
	 * Добавляет тэг во все словари
	 */
	public void addTag(String tagName) {
		for(Dictionary dictionary: dictionaries.values()) {
			dictionary.addTag(tagName);
			dictionary.sort();
		}
		
		updateTags();
	}

	/**
	 * Удаляет тэг со всех словарей
	 */
	public void removeTag(String tagName) {
		for(Dictionary dictionary: dictionaries.values()) {
			dictionary.removeTag(tagName);
		}
		
		updateTags();
	}

	/**
	 * Возвращает словарь для указанного языка
	 */
	public OrderedMap<String, String> getTranslateMap(String language) {
		dictionaries.get(language).translation.orderedKeys().sort();
		return dictionaries.get(language).translation;
	}
	
	/**
	 * Удаляет словарь для указанного языка. Это может быть полезным, 
	 * если вы хотите экономить память
	 */
	public void unloadLanguage(String language) {
		dictionaries.remove(language);
	}

	/**
	 * Возвращает все загруженные языки
	 */
	public String[] getLanguagesAsArray() {
		String[] result = new String[getLanguages().size];
		for(int i = 0; i < result.length; i++) {
			result[i] = getLanguages().get(i);
		}
		return result;
	}
	
	/**
	 * Загружает все словари по умолчанию. Этот метод можно использовать лишь во время разработки
	 */
	public void loadAllInnerDictionaries() {
		if(ProjectFinder.findAndroidProject() != null) {
			String localizePath = ProjectFinder.findAndroidProject() + "/assets/" + innerLocalizeFolder;
			File localizeFolder = new File(localizePath);
			if (!localizeFolder.exists()) {
				return;
			}
			
			File[] translationFiles = localizeFolder.listFiles(new ExtensionFilenameFilter("dictionary"));
			if (translationFiles == null) {
				return;
			}
			
			for(File file: translationFiles) {
				String[] parts = file.getName().split("\\.");
				String lang = parts[0].split("-")[0];
				if (!getLanguages().contains(lang, false)) {
					Localize.getInstance().setLanguage(lang);
				}
			}
		}
	}
	
	/**
	 * Возвращает текущий язык
	 * @return
	 */
	public String getLanguage() {
		return currentLanguage;
	}
}
