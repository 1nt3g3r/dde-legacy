package ua.com.integer.dde.res.sound;

import ua.com.integer.dde.res.loading.LoadManager;

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

	private ObjectMap<String, Music> music;

	/**
	 * Creates music manager.
	 */
	public MusicManager() {
		music = new ObjectMap<String, Music>();
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
			if (!musicHandle.isDirectory() && musicHandle.extension().equals("mp3")) {
				loadQueue.add(musicHandle.nameWithoutExtension());
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
		FileHandle musicFile = Gdx.files.internal(musicDirectory + "/" + name + ".mp3");
		if (musicFile.exists()) {
			music.put(name, Gdx.audio.newMusic(musicFile));
			loadedMusicCount++;
		} else {
			Gdx.app.error("Music manager", "Error during loading music " + name);
			System.exit(0);
		}
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
		Music musicFile = music.get(name);
		if (musicFile == null) {
			tryToLoadMusic(name);
			musicFile = music.get(name);
		}
		return musicFile;
	}

	/**
	 * @return Set of loaded music names.
	 */
	public Array<String> getLoadedMusic() {
		Array<String> result = new Array<String>();
		for (String musicName : music.keys()) {
			result.add(musicName);
		}
		return result;
	}

	@Override
	public void dispose() {
		for (Music musicFile : music.values()) {
			musicFile.dispose();
		}
	}

}
