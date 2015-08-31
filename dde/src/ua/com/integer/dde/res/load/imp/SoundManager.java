package ua.com.integer.dde.res.load.imp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;

public class SoundManager extends PathDescriptorLoadManager {
	public SoundManager() {
		this(null);
	}
	
	public SoundManager(PathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("ogg");
		addExtension("mp3");
		addExtension("wav");
	}
	
	public Sound getSound(String name) {
		return (Sound) get(name);
	}

	@Override
	protected Object createItem(FileHandle handle) {
		return Gdx.audio.newSound(handle);
	}
}
