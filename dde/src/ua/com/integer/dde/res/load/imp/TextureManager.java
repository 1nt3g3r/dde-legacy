package ua.com.integer.dde.res.load.imp;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import ua.com.integer.dde.res.load.FileHandleDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.FilePathDescriptor;

public class TextureManager extends FileHandleDescriptorLoadManager {
	public TextureManager(FilePathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("jpg");
		addExtension("jpeg");
		addExtension("png");
	}
	
	@Override
	public boolean loadStep() {
		if (super.loadStep()) {
			return true;
		}

		FileHandle currentHandle = getNextHandle();
		loadedObjects.put(getCurrentFileHandleName(), new Texture(currentHandle));
		return false;
	}
	
	public Texture getTexture(String name) {
		return (Texture) loadedObjects.get(name);
	}
}
