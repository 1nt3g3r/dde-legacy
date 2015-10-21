package ua.com.integer.dde.remote.files.extension.manager;

import com.badlogic.gdx.Gdx;

import ua.com.integer.dde.remote.files.extension.core.Downloader;
import ua.com.integer.dde.remote.files.extension.core.FileList;
import ua.com.integer.dde.remote.files.extension.core.OperationListener;
import ua.com.integer.dde.remote.files.extension.core.RemoteFilesConfig;
import ua.com.integer.dde.res.load.LoadManager;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.util.JsonWorker;

public class RemoteFilesLoadManager implements LoadManager, OperationListener {
	private Downloader downloader;
	private boolean failed;
	private boolean loaded;
	
	public RemoteFilesLoadManager(Downloader downloader) {
		this.downloader = downloader;
	}
	
	public RemoteFilesLoadManager(String innerConfigName) {
		this(JsonWorker.JSON.fromJson(RemoteFilesConfig.class, Gdx.files.internal("data/dde-remote-files-configs/" + innerConfigName + ".config")));
	}
	
	public RemoteFilesLoadManager(RemoteFilesConfig config) {
		downloader = new Downloader(config);
	}

	@Override
	public void dispose() {
		downloader.stop();
	}

	@Override
	public void loadAll() {
		FileList filelist = getFilelist();
		if (filelist == null) {
			downloader.start(this);
		} else {
			loaded = downloader.isSynchronized(filelist);
			if (loaded) {
				downloader.setFiles(filelist);
			} else {
				downloader.start(this);
			}
		}
	}

	@Override
	public boolean loadStep() {
		if (loaded || failed) {
			return true;
		}
		
		return downloader.getSynchronizedPercent() >= 100;
	}

	@Override
	public float getLoadPercent() {
		if (loaded) {
			return 1f;
		}
		return (float) (downloader.getSynchronizedPercent()) / 100f;
	}

	@Override
	public int getAssetCount() {
		return downloader.getFiles().files.size;
	}

	@Override
	public int getLoadedAssetCount() {
		return downloader.getSynchronizedPercent() * getAssetCount() / 100;
	}

	@Override
	public boolean isLoaded(String name) {
		return false;
	}

	@Override
	public void finished(String message) {
		if (downloader.getFiles() != null) {
			AbstractScreen.getKernel().getSets().saveJsonObject(getFilelistPrefsName(), downloader.getFiles()).flush();
		}
	}
	
	private FileList getFilelist() {
		return AbstractScreen.getKernel().getSets().getJsonObject(getFilelistPrefsName(), FileList.class);
	}
	
	private String getFilelistPrefsName() {
		return downloader.getLocalFolder() + "+" + downloader.getRemoteURL();
	}

	@Override
	public void progress(int progress) {
	}

	@Override
	public void failed(String message) {
		failed = true;
	}
	
	public boolean isFailed() {
		return failed;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public void removeFilelist() {
		AbstractScreen.getKernel().getSets().removeKey(getFilelistPrefsName());
	}
}
