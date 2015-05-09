package ua.com.integer.dde.res.sound;


import ua.com.integer.dde.res.loading.LoadManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Sound manager. Contains game sounds. You can get sound by call
 * getSound(String name) method. Implements "lazy loading" - if some sound isn't
 * loaded, manager try to load it "on the fly".
 * 
 * @author ice
 * 
 */
public class SoundManager implements Disposable, LoadManager {
	private String soundDirectory;
	private Array<String> loadQueue;
	private int totalSoundCount, loadedSoundCount;
	private boolean useSeparateThread = true;

	private ObjectMap<String, Sound> sounds;
	
	private static final String[] SOUND_EXTENSIONS = {
			"ogg",
			"mp3",
			"wav"
	};

	/**
	 * Creates sound manager.
	 */
	public SoundManager() {
		sounds = new ObjectMap<String, Sound>();
		loadQueue = new Array<String>();
	}
	
	public void setUseSeparateThread(boolean useSeparateThread) {
		this.useSeparateThread = useSeparateThread;
	}

	/**
	 * Sets directory which contains sound files.
	 * 
	 * @param soundDirectory relative path - as example, "data/audio/sounds"
	 */
	public void setSoundDirectory(String soundDirectory) {
		this.soundDirectory = soundDirectory;
	}

	@Override
	public void loadAll() {
		if (soundDirectory == null || !Gdx.files.internal(soundDirectory).exists()) {
			Gdx.app.error(getClass() + "", "Sound directory isn't selected or doesn't exists!");
			return;
		}

		loadQueue.clear();
		for (FileHandle soundHandle : Gdx.files.internal(soundDirectory).list()) {
			if (!soundHandle.isDirectory()) {
				String extension = soundHandle.extension();
				if (canLoadSound(extension)) {
					loadQueue.add(soundHandle.nameWithoutExtension());
				}
			}
		}

		totalSoundCount = loadQueue.size;
		
		if (useSeparateThread) {
			loadInSeparateThread();
		}
	}
	
	private void loadInSeparateThread() {
		new Thread() {
			public void run() {
				while(true) {
					if (oneThreadLoadStep()) {
						break;
					}
				}
			}
		}.start();
	}

	
	@Override
	public boolean loadStep() {
		if (useSeparateThread) {
			return loadQueue.size == 0;
		} else {
			return oneThreadLoadStep();
		}
	}
	
	private boolean oneThreadLoadStep() {
		if (loadQueue.size == 0) {
			return true;
		} else {
			tryToLoadSound(loadQueue.pop());
			return false;
		}
	}

	private void tryToLoadSound(String name) {
		for(String extension: SOUND_EXTENSIONS) {
			FileHandle soundFile = Gdx.files.internal(soundDirectory + "/" + name + "." + extension);
			if (soundFile.exists()) {
				sounds.put(name, Gdx.audio.newSound(soundFile));
				loadedSoundCount++;
				return;
			}
		}
		
		Gdx.app.error("Sound manager", "Error during loading sound " + name);
	}

	@Override
	public float getLoadPercent() {
		if (totalSoundCount == 0) {
			return 1;
		}
		return (float) loadedSoundCount / (float) totalSoundCount;
	}

	/**
	 * Returns sound. If sound is not loaded, manager try to load it.
	 */
	public Sound getSound(String name) {
		Sound sound = sounds.get(name);
		if (sound == null) {
			tryToLoadSound(name);
			sound = sounds.get(name);
		}
		return sound;
	}

	/**
	 * @return Set of loaded sound names.
	 */
	public Array<String> getLoadedSounds() {
		Array<String> result = new Array<String>();
		for (String soundName : sounds.keys()) {
			result.add(soundName);
		}

		return result;
	}

	@Override
	public void dispose() {
		for (Sound sound : sounds.values()) {
			sound.dispose();
		}
	}
	
	/**
	 * Disposes sound
	 * @param name name of the sound
	 */
	public void disposeSound(String name) {
		Sound sound = sounds.get(name);
		if (sound != null) {
			sound.dispose();
		}
		sounds.remove(name);
	}
	
	private boolean canLoadSound(String extension) {
		for(String allowed : SOUND_EXTENSIONS) {
			if (extension.toLowerCase().equals(allowed)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getAssetCount() {
		return totalSoundCount;
	}

	@Override
	public int getLoadedAssetCount() {
		return loadedSoundCount;
	}
}