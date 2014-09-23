package ua.com.integer.dde.extension.localize;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.Gdx;

/**
 * Класс-обертка над переводом - классом Translation. 
 * Загружает файл перевода по стандартному пути. 
 * Реализует паттерн проектирования "Одиночка"
 * 
 * @author 1nt3g3r
 */
public class Localize {
	public static final String LOCALIZE_FILENAME = "translation.localize";
	
	private static Localize instance = new Localize();
	private Translation translation;
	
	private String[] tags;
	private String[] languages;
	
	private Localize() {
		try {
			setKernel(AbstractScreen.getKernel());
			if (translation.getLanguages().size > 0) {
				translation.setLanguage(translation.getLanguages().first());
			}
		} catch(Exception ex) {
			Gdx.app.error("localize", "Can't load AbstractScreen.getKernel()!");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Получить экземпляр класса
	 */
	public static Localize getInstance() {
		return instance;
	}
	
	/**
	 * Получить экземпляр класса-перевода. Перед вызовом этого метода нужно вызвать метод setKernel, 
	 * в противном случае будет выброшено исключение
	 */
	public Translation getTranslation() {
		if (translation == null) throw new IllegalStateException("Call setKernel() method before!");
		return translation;
	}
	
	/**
	 * Получает ядро, из ядра получает относительный путь к файлу с переводом. 
	 * Этот метод должен быть вызван перед использованием данного класса
	 */
	public void setKernel(DDKernel kernel) {
		String relativePath = kernel.getConfig().relativeDirectory;
		translation = Translation.loadFromFile(Gdx.files.internal(relativePath + "data/" + LOCALIZE_FILENAME));
	}
	
	/**
	 * Возвращает перевод слова по его тэгу
	 * @param tag имя тега
	 */
	public String translate(String tag) {
		return getTranslation().translate(tag);
	}
	
	/**
	 * Устанавливает язык. Список доступных языков можно получить с помощью метод getLanguages() класса Translation. 
	 * Например, Localize.getInstance().getTranslation().getLanguages() 
	 * @param language имя языка
	 */
	public void setLanguage(String language) {
		getTranslation().setLanguage(language);
	}
	
	/**
	 * Возвращает список всех доступных тегов. Результат кэшируется, 
	 * при изменении списка тегов кеш обновляется
	 */
	public String[] getTags() {
		if (tags == null || tags.length != getTranslation().getTags().size) {
			tags = new String[getTranslation().getTags().size];
			int index = -1;
			for(String tag : getTranslation().getTags()) {
				tags[++index] = tag;
			}
		}
		
		return tags;
	}
	
	/**
	 * Возвращает список всех доступных языков. При изменении списка языков 
	 * кеш обновляется
	 * @return
	 */
	public String[] getLanguages() {
		if (languages == null || getTranslation().getLanguages().size != languages.length) {
			languages = new String[getTranslation().getLanguages().size];
			int index = -1;
			
			for(String lang : getTranslation().getLanguages()) {
				languages[++index] = lang;
			}
		}
		
		return languages;
	}
}
