package ua.com.integer.dde.net.file.sync;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Array;

public class SyncFiles implements HttpResponseListener {
	public Array<String> files;

	private String remoteUrl;
	private String baseFolder;
	private String lastFile;
	
	private int downloadedCount;
	private int toDownloadCount = 0;
	private boolean loadingNow;
	
	private boolean error;
	
	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	
	public void init() {
		mkdirs();
		removeExistingFilesFromDownloadQueue();
		
		toDownloadCount = files.size;
		loadingNow = false;
		downloadedCount = 0;
	}

	
	public void mkdirs() {
		for(String file : files) {
			if (file.contains("/")) {
				Gdx.files.external(baseFolder + "/" + file).parent().mkdirs();
			}
		}
	}
	
	public void removeExistingFilesFromDownloadQueue() {
		Array<String> toRemove = new Array<String>();
		
		for(String file : files) {
			if (Gdx.files.external(baseFolder + "/" + file).exists()) {
				toRemove.add(file);
			}
		}
		
		files.removeAll(toRemove, false);
	}
	
	public boolean loadFile() {
		if (files.size > 0 && !loadingNow) {
			lastFile = files.pop();
			HttpRequest req = new HttpRequest(HttpMethods.GET);
			req.setUrl(remoteUrl + "/" + lastFile);
			loadingNow = true;
			Gdx.net.sendHttpRequest(req, this);
		}
		return toDownloadCount > 0;
	}
	
	public int getFileCountToDownload() {
		return toDownloadCount;
	}
	
	public int getDownloadedFileCount() {
		return downloadedCount;
	}
	
	@Override
	public String toString() {
		return files + "";
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		downloadedCount ++;
		toDownloadCount--;
		if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			FileHandle handle = Gdx.files.external(baseFolder + "/" + lastFile);
			handle.writeBytes(httpResponse.getResult(), false);
		} else {
			Gdx.app.error("net", "File " + lastFile + " not found on remote server!");
		}
		loadingNow = false;
	}

	@Override
	public void failed(Throwable t) {
		error = true;
		Gdx.app.error("net", "Error during loading file " + lastFile, t);
	}
	
	public boolean isError() {
		return error;
	}

	@Override
	public void cancelled() {
		
	}
}
