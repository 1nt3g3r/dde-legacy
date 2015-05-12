package ua.com.integer.dde.res.load.descriptor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class CompositePathDescriptor extends PathDescriptor {
	private Array<PathDescriptor> descriptors = new Array<PathDescriptor>();
	
	public void addDescriptor(PathDescriptor descriptor) {
		descriptors.add(descriptor);
	}
	
	public Array<PathDescriptor> getDescriptors() {
		return descriptors;
	}
	
	public FileHandle getDirectory() {
		for(PathDescriptor descriptor: descriptors) {
			FileHandle handle = descriptor.getDirectory();
			if (handle != null) {
				return handle;
			}
		}
		
		return null;
	};
	
	public static CompositePathDescriptor multiple(PathDescriptor ... multipleDescriptors) {
		CompositePathDescriptor result = new CompositePathDescriptor();
		result.descriptors.addAll(multipleDescriptors);
		return result;
	}
}
