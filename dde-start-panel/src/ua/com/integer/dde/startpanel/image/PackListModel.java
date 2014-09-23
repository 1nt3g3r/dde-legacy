package ua.com.integer.dde.startpanel.image;

import java.io.File;
import java.util.Arrays;

import javax.swing.AbstractListModel;

public class PackListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 7629206081571938028L;
	private File[] packFiles;
	
	public PackListModel(File[] packFiles) {
		this.packFiles = packFiles;
		Arrays.sort(packFiles);
	}
	
	@Override
	public String getElementAt(int index) {
		return packFiles[index].getName();
	}

	@Override
	public int getSize() {
		return packFiles.length;
	}
	
	public File getFileAt(int index) {
		return packFiles[index];
	}

}
