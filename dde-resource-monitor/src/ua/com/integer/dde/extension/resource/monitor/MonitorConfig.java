package ua.com.integer.dde.extension.resource.monitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

import ua.com.integer.dde.util.JsonWorker;

public class MonitorConfig {
	public ObjectMap<String, String> managers;
	
	@SuppressWarnings("unchecked")
	public MonitorConfig(FileHandle handle) {
		try {
			managers = JsonWorker.JSON.fromJson(ObjectMap.class, handle);
		} catch(Exception ex) {
			useDefaults();
		}
	}
	
	private void useDefaults() {
		managers = new ObjectMap<String, String>();
		managers.put("Sounds", "ua.com.integer.dde.res.load.imp.SoundManager");
		managers.put("Music", "ua.com.integer.dde.res.load.imp.MusicManager");
	}
	
	public MonitorConfig() {
		this(Gdx.files.internal("data/dde-res-mon/config.json"));
	}
}
