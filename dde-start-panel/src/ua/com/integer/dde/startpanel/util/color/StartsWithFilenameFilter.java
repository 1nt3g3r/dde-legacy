package ua.com.integer.dde.startpanel.util.color;

import java.io.File;
import java.io.FilenameFilter;

public class StartsWithFilenameFilter implements FilenameFilter {
	private String startString;
	
	public StartsWithFilenameFilter(String startString) {
		this.startString = startString;
	}
	
	@Override
	public boolean accept(File file, String name) {
		return name.startsWith(startString);
	}
}
