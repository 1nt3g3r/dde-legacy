package ua.com.integer.dde.res.load.imp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import ua.com.integer.dde.res.load.FileHandleDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.FilePathDescriptor;

public class MusicManager extends FileHandleDescriptorLoadManager {
	public MusicManager(FilePathDescriptor descriptor) {
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
		
		FileHandle musicHandle = getNextHandle();
		String musicName = getCurrentFileHandleName();
		loadedObjects.put(musicName, Gdx.audio.newMusic(musicHandle));
		return false;
	}

	public Music getMusic(String name) {
		return (Music) loadedObjects.get(name);
	}
}
