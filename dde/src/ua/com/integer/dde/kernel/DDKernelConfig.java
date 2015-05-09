package ua.com.integer.dde.kernel;

/**
 * Вспомогательный конфигурационный файл для ядра. В большинстве случаев 
 * здесь ничего не нужно менять\настраивать
 * 
 * @author 1nt3g3r
 */
public class DDKernelConfig {
	/**
	 * Относительная директория, на которую нужно "сдвинуться", чтобы получить доступ 
	 * к остальным директориям с ресурсами. Например, если мы запускаем desktop-проект из-под среды разработки Eclise, 
	 * ресурсы находятся в каталоге ../android-project/assets/data - таким образом, относительный путь будет 
	 * ../android-project/assets. Если вы создаете проект с помощью мастера создания проекта DDE, 
	 * вам не нужно беспокоиться о значении этой переменной - для разных проектов она автоматически инициализируется 
	 * нужным значением. ВНИМАНИЕ: при переименовании проектов вам нужно будет вручную поменять в "запускалках" для разных 
	 * платформ значения этой переменной.
	 */
	public String relativeDirectory = "";
	/**
	 * Каталог, где хранятся упакованные в атласы текстуры
	 */
	public String packDirectory = "data/dde-atlases";
	/**
	 * Каталог для хранения TrueType шрифтов (ttf)
	 */
	public String fontDirectory = "data/dde-fonts";
	/**
	 * Каталог для хранения звуков в форматах ogg, mp3, wav
	 */
	public String soundDirectory = "data/dde-sounds";
	/**
	 * Каталог для хранения музыки в ogg, mp3, wav. Звук и музыка отличаются способом хранения в памяти - 
	 * звук целиком загружается в память, музыка же по мере проигрывания загружается с диска (streaming)
	 */
	public String musicDirectory = "data/dde-musics";
	/**
	 * Нужно ли обрабатывать клавишу "Назад" на android телефонах (на компьютере мы считаем, что 
	 * это кнопка Escape). По умолчанию обработка включена
	 */
	public boolean catchBackKey = true;
	/**
	 * Отключить управление текстурами с помощью AssetManager после полной их загрузки. 
	 * Если выставить в true, то текстуры будут автоматически восстанавливаться после сворачивания\разворачивания 
	 * приложения на android устройствах. По умолчанию - true
	 */
	public boolean stopManageTexturesAfterLoading = true;
	
	/**
	 * Добавить к каталогам ресурсов относительный путь. Нужно в случае запуска игры 
	 * из среды разработки. Ядро само беспокоится о вызове этого метода, в большинстве случаев 
	 * вам не нужно думать об этом
	 */
	public void addUseDirectory() {
		packDirectory = relativeDirectory + packDirectory;
		fontDirectory = relativeDirectory + fontDirectory;
		soundDirectory = relativeDirectory + soundDirectory;
		musicDirectory = relativeDirectory + musicDirectory;
	}
}
