package ua.com.integer.dde.res.load.descriptor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;

public class FilePathDescriptor {
	public String path;
	public FileType fileType;
	
	public FilePathDescriptor(String path, FileType fileType) {
		this.path = path;
		this.fileType = fileType;
	}
	
	public FilePathDescriptor() {
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	
	public FileHandle getDirectory() {
		FileHandle handle = Gdx.files.getFileHandle(path, fileType);
		if (handle.exists() && handle.isDirectory()) {
			return handle;
		} else {
			return null;
		}
	};
	
	public FileHandle getFile(String name) {
		return getDirectory().child(name);
	}
	
	public static FilePathDescriptor internal(String path) {
		return new FilePathDescriptor(path, FileType.Internal);
	}
	
	public static FilePathDescriptor external(String path) {
		return new FilePathDescriptor(path, FileType.External);
	}
	
	public static FilePathDescriptor classpath(String path) {
		return new FilePathDescriptor(path, FileType.Classpath);
	}
	
	public static FilePathDescriptor absolute(String path) {
		return new FilePathDescriptor(path, FileType.Absolute);
	}
	
	public static FilePathDescriptor local(String path) {
		return new FilePathDescriptor(path, FileType.Local);
	}
	
	public static FilePathDescriptor multiple(String path, FileType ... types) {
		CompositeFilePathDescriptor result = new CompositeFilePathDescriptor();
		for(FileType type : types) {
			result.addDescriptor(new FilePathDescriptor(path, type));
		}
		return result;
	}
}
