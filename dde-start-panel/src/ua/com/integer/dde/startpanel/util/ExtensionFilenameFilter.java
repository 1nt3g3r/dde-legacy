package ua.com.integer.dde.startpanel.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Класс-фильтр для файлов с определенным расширением
 * 
 * @author 1nt3g3r
 */
public class ExtensionFilenameFilter implements FilenameFilter {
	private String extension;
	
	/**
	 * Нужное расширение файла без точки
	 */
	public ExtensionFilenameFilter(String extension) {
		this.extension = extension;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith("." + extension);
	}

}
