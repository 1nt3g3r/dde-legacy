package ua.com.integer.dde.startpanel.util;

import java.io.File;
import java.io.FilenameFilter;

public class AtlasFilenameFilter implements FilenameFilter {
	@Override
	public boolean accept(File file, String name) {
		return name != "" && !name.startsWith(".");
	}
}
