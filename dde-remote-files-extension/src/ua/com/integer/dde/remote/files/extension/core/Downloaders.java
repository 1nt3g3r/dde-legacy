package ua.com.integer.dde.remote.files.extension.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;

import ua.com.integer.dde.remote.files.extension.manager.RemoteFilesLoadManager;
import ua.com.integer.dde.util.JsonWorker;

public class Downloaders {
	private static Downloaders instance = new Downloaders();
	private ObjectMap<String, Downloader> downloaders =new ObjectMap<String, Downloader>();
	
	private Downloaders() {
	}
	
	public static Downloaders getInstance() {
		return instance;
	}
	
	public Downloader get(String name) {
		Downloader downloader = downloaders.get(name);
		if (downloader == null) {
			downloader = new Downloader(JsonWorker.JSON.fromJson(RemoteFilesConfig.class, Gdx.files.internal("data/dde-remote-files-configs/" + name + ".config")));
			downloaders.put(name, downloader);
		}
		
		return downloader;
	}
	
	public void startLoading(String name) {
		get(name).start(new OperationListener() {
			@Override
			public void progress(int progress) {
			}
			
			@Override
			public void finished(String message) {
			}
			
			@Override
			public void failed(String message) {
			}
		});
	}
	
	public boolean isLoaded(String name) {
		return get(name).isSynchronized(get(name).getFiles());
	}
	
	public RemoteFilesLoadManager getLoadManager(String name) {
		return new RemoteFilesLoadManager(get(name));
	}
}
