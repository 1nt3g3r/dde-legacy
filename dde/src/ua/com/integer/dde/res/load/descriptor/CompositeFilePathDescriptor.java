package ua.com.integer.dde.res.load.descriptor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class CompositeFilePathDescriptor extends FilePathDescriptor {
	private Array<FilePathDescriptor> descriptors = new Array<FilePathDescriptor>();
	
	public void addDescriptor(FilePathDescriptor descriptor) {
		descriptors.add(descriptor);
	}
	
	public Array<FilePathDescriptor> getDescriptors() {
		return descriptors;
	}
	
	public FileHandle getDirectory() {
		for(FilePathDescriptor descriptor: descriptors) {
			FileHandle handle = descriptor.getDirectory();
			if (handle != null) {
				return handle;
			}
		}
		
		return null;
	};
}
