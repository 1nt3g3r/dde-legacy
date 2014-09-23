package ua.com.integer.dde.startpanel.image;

import java.io.File;
import java.io.FileFilter;

public class ImagesDirectoryFilter implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory() && pathname.getName().contains("images-");
	}
}