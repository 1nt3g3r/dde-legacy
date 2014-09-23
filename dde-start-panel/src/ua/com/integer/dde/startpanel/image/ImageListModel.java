package ua.com.integer.dde.startpanel.image;

import java.io.File;
import java.util.Arrays;

import javax.swing.AbstractListModel;

public class ImageListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = -9047858709001533820L;
	private File[] imageFiles;
	
	public ImageListModel(File[] imageFiles) {
		this.imageFiles = imageFiles;
		Arrays.sort(imageFiles);
	}
	
	@Override
	public String getElementAt(int index) {
		return cutExtension(imageFiles[index].getName());
	}
	
	private String cutExtension(String filename) {
		String[] filenameItems = filename.split("\\.");
		String toReturn = "";
		for(int i = 0; i < filenameItems.length-1; i++) {
			toReturn += filenameItems[i];
			if (i != filenameItems.length-2) {
				toReturn += ".";
			}
		}
		return toReturn;
	}

	@Override
	public int getSize() {
		return imageFiles.length;
	}
	
	public File getFileAt(int index) {
		return imageFiles[index];
	}

}
