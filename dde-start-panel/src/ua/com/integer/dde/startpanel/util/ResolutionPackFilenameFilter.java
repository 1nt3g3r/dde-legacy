package ua.com.integer.dde.startpanel.util;

import java.io.File;
import java.io.FilenameFilter;

public class ResolutionPackFilenameFilter implements FilenameFilter {
	@Override
	public boolean accept(File file, String name) {
		return file.isDirectory() && name.startsWith("images-");
	}
}
