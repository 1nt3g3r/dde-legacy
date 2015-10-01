package ua.com.integer.dde.remote.files.extension.editor;

import java.io.File;

import javax.swing.AbstractListModel;

public class FileListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 4423026504088675196L;
	private File[] files;
	
	public FileListModel(File[] files) {
		this.files = files;
	}
	
	@Override
	public String getElementAt(int index) {
		return files[index].getName().split("\\.")[0];
	}

	@Override
	public int getSize() {
		return files.length;
	}
	
	public File getFileAt(int index) {
		return files[index];
	}
	
	public boolean containsNameWithoutExtension(String name) {
		for(File file: files) {
			if (file.getName().split("\\.")[0].equals(name)) {
				return true;
			}
		}
		
		return false;
	}
}
