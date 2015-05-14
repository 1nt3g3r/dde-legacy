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
		return null;
	};
	
	@Override
	public FileHandle getFile(String name) {
		for(PathDescriptor descriptor: descriptors) {
			FileHandle dir = descriptor.getDirectory();
			if (dir != null) {
				FileHandle handle = dir.child(name);
				if (handle.exists()) {
					return handle;
				}
			}
		}

		return null;
	}
	
	public static CompositePathDescriptor multiple(PathDescriptor ... multipleDescriptors) {
		CompositePathDescriptor result = new CompositePathDescriptor();
		result.descriptors.addAll(multipleDescriptors);
		return result;
	}
}
