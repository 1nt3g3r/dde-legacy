package ua.com.integer.dde.res.load.imp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.load.descriptor.PathDescriptor;

public class MusicManager extends PathDescriptorLoadManager {
	public MusicManager() {
		this(null);
	}
	
	public MusicManager(PathDescriptor descriptor) {
		setDescriptor(descriptor);
		
		addExtension("ogg");
		addExtension("mp3");
		addExtension("wav");
	}

	public Music getMusic(String name) {
		return (Music) get(name);
	}
	
	@Override
	protected Object createItem(FileHandle handle) {
		return Gdx.audio.newMusic(handle);
	}
}
