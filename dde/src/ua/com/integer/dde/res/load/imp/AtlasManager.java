package ua.com.integer.dde.res.load.imp;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;

public class AtlasManager extends PathDescriptorLoadManager {
	public AtlasManager(PathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("pack");
		addExtension("atlas");
	}
	
	public TextureAtlas getAtlas(String name) {
		return (TextureAtlas) get(name);
	}

	@Override
	protected Object createItem(FileHandle handle) {
		return new TextureAtlas(handle);
	}
}
