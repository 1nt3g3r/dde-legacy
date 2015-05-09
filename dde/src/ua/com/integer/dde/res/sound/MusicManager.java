package ua.com.integer.dde.res.sound;

import ua.com.integer.dde.res.LoadManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Music manager. Contains game music. You can get music by call
 * getMusic(String name) method. Implements "lazy loading" - if some music isn't
 * loaded, manager try to load it "on the fly".
 * 
 * @author ice
 * 
 */
public class MusicManager implements Disposable, LoadManager {
	private String musicDirectory;
	private Array<String> loadQueue;
	private int totalMusicCount, loadedMusicCount;
	private boolean useSeparateThread = true;

	private ObjectMap<String, Music> musics;
	
	private static final String[] MUSIC_EXTENSIONS = {
		"ogg",
		"mp3",
		"wav"
	};

	/**
	 * Creates music manager.
	 */
	public MusicManager() {
		musics = new ObjectMap<String, Music>();
		loadQueue = new Array<String>();
	}
	
	public void setUseSeparateThread(boolean useSeparateThread) {
		this.useSeparateThread = useSeparateThread;
	}

	/**
	 * Sets directory which contains music files.
	 * 
	 * @param musicDirectory relative path - as example, "data/audio/music"
	 */
	public void setMusicDirectory(String musicDirectory) {
		this.musicDirectory = musicDirectory;
	}

	@Override
	public void loadAll() {
		if (musicDirectory == null || !Gdx.files.internal(musicDirectory).exists()) {
			Gdx.app.error(getClass() + "", "Music directory isn't selected or doesn't exists!");
			return;
		}

		loadQueue.clear();
		for (FileHandle musicHandle : Gdx.files.internal(musicDirectory).list()) {
			if (!musicHandle.isDirectory()) {
				String extension = musicHandle.extension();
				if (canLoadSound(extension)) {
					loadQueue.add(musicHandle.nameWithoutExtension());
				}
			}
		}

		totalMusicCount = loadQueue.size;
		
		if (useSeparateThread) {
			startLoadingMusicInSeparateThread();
		}
	}
	
	private void startLoadingMusicInSeparateThread() {
		new Thread() {
			public void run() {
				while (true) {
					if (loadQueue.size == 0) {
						break;
					} else {
						tryToLoadMusic(loadQueue.pop());
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
			if (loadQueue.size == 0) {
				return true;
			} else {
				tryToLoadMusic(loadQueue.pop());
				return false;
			}
		}
	}

	private void tryToLoadMusic(String name) {
		for(String extension: MUSIC_EXTENSIONS) {
			FileHandle musicFile = Gdx.files.internal(musicDirectory + "/" + name + "." + extension);
			if (musicFile.exists()) {
				musics.put(name, Gdx.audio.newMusic(musicFile));
				loadedMusicCount++;
				return;
			}
		}
		Gdx.app.error("Music manager", "Error during loading music " + name);
	}

	@Override
	public float getLoadPercent() {
		if (totalMusicCount == 0) {
			return 1;
		}
		return (float) loadedMusicCount / (float) totalMusicCount;
	}

	/**
	 * Returns music. If music file is not loaded, manager try to load it.
	 */
	public Music getMusic(String name) {
		Music musicFile = musics.get(name);
		if (musicFile == null) {
			tryToLoadMusic(name);
			musicFile = musics.get(name);
		}
		return musicFile;
	}

	/**
	 * @return Set of loaded music names.
	 */
	public Array<String> getLoadedMusic() {
		Array<String> result = new Array<String>();
		for (String musicName : musics.keys()) {
			result.add(musicName);
		}
		return result;
	}

	@Override
	public void dispose() {
		for (Music musicFile : musics.values()) {
			musicFile.dispose();
		}
	}
	
	/**
	 * Disposes music
	 * @param name name of the music
	 */
	public void disposeMusic(String name) {
		Music music = musics.get(name);
		if (music != null) {
			music.dispose();
		}
		musics.remove(name);
	}

	private boolean canLoadSound(String extension) {
		for(String allowed : MUSIC_EXTENSIONS) {
			if (extension.toLowerCase().equals(allowed)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getAssetCount() {
		return totalMusicCount;
	}

	@Override
	public int getLoadedAssetCount() {
		return loadedMusicCount;
	}
	
	public void addMusic(String name, Music music) {
		musics.put(name, music);
	}
}
