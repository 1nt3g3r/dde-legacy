package ua.com.integer.dde.res.load.imp;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;

public class TextureManager extends PathDescriptorLoadManager {
	public TextureManager(PathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("jpg");
		addExtension("jpeg");
		addExtension("png");
	}
	
	public Texture getTexture(String name) {
		return (Texture) loadedObjects.get(name);
	}

	@Override
	protected Object createItem(FileHandle handle) {
		return new Texture(handle);
	}
}