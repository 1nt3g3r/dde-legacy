package ua.com.integer.dde.res.load.descriptor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;

public class PathDescriptor {
	public String path;
	public FileType fileType;
	
	public PathDescriptor(String path, FileType fileType) {
		this.path = path;
		this.fileType = fileType;
	}
	
	public PathDescriptor() {
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
	
	public static PathDescriptor internal(String path) {
		return new PathDescriptor(path, FileType.Internal);
	}
	
	public static PathDescriptor external(String path) {
		return new PathDescriptor(path, FileType.External);
	}
	
	public static PathDescriptor classpath(String path) {
		return new PathDescriptor(path, FileType.Classpath);
	}
	
	public static PathDescriptor absolute(String path) {
		return new PathDescriptor(path, FileType.Absolute);
	}
	
	public static PathDescriptor local(String path) {
		return new PathDescriptor(path, FileType.Local);
	}
	
	public static PathDescriptor multiple(String path, FileType ... types) {
		CompositePathDescriptor result = new CompositePathDescriptor();
		for(FileType type : types) {
			result.addDescriptor(new PathDescriptor(path, type));
		}
		return result;
	}
}
