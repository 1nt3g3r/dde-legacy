package ua.com.integer.dde.net.file.sync;

import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpStatus;

public class FolderSynchronizer {
	private static final String MASTER_FILENAME = "files.txt";
	private String folder;
	private String remoteUrl;
	private SyncFiles files;
	private SyncListener sListener;
	
	class FilesHttpReponceListener implements HttpResponseListener {
		@Override
		public void handleHttpResponse(HttpResponse httpResponse) {
			if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
				files = JsonWorker.JSON.fromJson(SyncFiles.class, httpResponse.getResultAsStream());
				files.setBaseFolder(folder);
				files.setRemoteUrl(remoteUrl);
				files.init();
				float startCount = files.getFileCountToDownload();
				if (startCount == 0) {
					sListener.dontNeedSync();
					return;
				}
				sListener.beginSync();
				while(files.loadFile()) {
					if (files.isError()) {
						sListener.failSync();
						return;
					}
					sListener.percentSync((float) files.getDownloadedFileCount() / startCount);
				}
				sListener.endSync();
			} else {
				sListener.failSync();
				throw new IllegalStateException("Can't get master list file!");
			}
		}
		@Override
		public void failed(Throwable t) {
			sListener.noInet();
		}
		@Override
		public void cancelled() {
			// TODO Auto-generated method stub
			
		}
	}
	
	public FolderSynchronizer(String folder, String remoteUrl) {
		this.folder = folder;
		this.remoteUrl = remoteUrl;
	}
	
	public void synchronize(SyncListener sListener) {
		getFiles();
		this.sListener = sListener;
	}
	
	private void getFiles() {
		HttpRequest req = new HttpRequest(HttpMethods.GET);
		req.setUrl(remoteUrl + "/" + MASTER_FILENAME);
		Gdx.net.sendHttpRequest(req, new FilesHttpReponceListener());
	}
	
	public static void main(String[] args) {
		FolderSynchronizer fSync = new FolderSynchronizer("async", "http://www.integer-labs.com/alphabet");
		fSync.synchronize(null);
	}

}
