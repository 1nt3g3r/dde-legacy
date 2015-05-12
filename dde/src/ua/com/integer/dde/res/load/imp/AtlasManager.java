package ua.com.integer.dde.res.load.imp;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ua.com.integer.dde.res.load.FileHandleDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.FilePathDescriptor;

public class AtlasManager extends FileHandleDescriptorLoadManager {
	public AtlasManager(FilePathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("pack");
		addExtension("atlas");
	}
	
	@Override
	public boolean loadStep() {
		if (super.loadStep()) {
			return true;
		}
		
		FileHandle atlasHandle = getNextHandle();
		String name = atlasHandle.nameWithoutExtension();
		loadedObjects.put(name, new TextureAtlas(atlasHandle));
		return false;
	}
	
	public TextureAtlas getAtlas(String name) {
		return (TextureAtlas) loadedObjects.get(name);
	}
}
