package ua.com.integer.dde.res.load.imp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import ua.com.integer.dde.res.load.FileHandleDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.FilePathDescriptor;

public class SoundManager extends FileHandleDescriptorLoadManager {
	public SoundManager(FilePathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("ogg");
		addExtension("mp3");
		addExtension("wav");
	}
	
	@Override
	public boolean loadStep() {
		if (super.loadStep()) {
			return true;
		}
		
		FileHandle soundHandle = getNextHandle();
		String soundName = getCurrentFileHandleName();
		loadedObjects.put(soundName, Gdx.audio.newSound(soundHandle));
		return false;
	}
	
	public Sound getSound(String name) {
		return (Sound) loadedObjects.get(name);
	}
}
