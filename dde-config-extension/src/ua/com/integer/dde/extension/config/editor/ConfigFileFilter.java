package ua.com.integer.dde.extension.config.editor;

import java.io.File;
import java.io.FileFilter;

public class ConfigFileFilter implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		return pathname.getName().endsWith("config");
	}
}
