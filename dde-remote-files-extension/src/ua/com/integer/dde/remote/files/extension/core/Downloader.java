package ua.com.integer.dde.remote.files.extension.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

public class Downloader {
	public static final String FILELIST_NAME = "filelist.json";
	public static final String LOG_TAG = "synchronized-store";
	
	private FileList files;
	private String localFolder;
	private String remoteURL;
	private boolean log;

	private boolean stop;
	private int failCount;
	private int maxFailCount = 5;
	
	private boolean downloadingNow = false;

	public Downloader(RemoteFilesConfig config) {
		this(config.localFolder, config.remoteURL);
		this.log = config.logEnabled;
		this.maxFailCount = config.maxFailCount;
	}

	public Downloader(String localFolder, String remoteURL) {
		this.localFolder = slashed(localFolder);
		this.remoteURL = slashed(remoteURL);

		File folder = new File(localFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	private void log(String message) {
		if (log) {
			Gdx.app.log(LOG_TAG, message);
		}
	}

	public boolean isSynchronized(FileList list) {
		for (RemoteFile file : list.files) {
			if (!isFileSynchronized(file)) {
				return false;
			}
		}

		return true;
	}

	public void start(final OperationListener listener) {
		stop = false;
		failCount = 0;
		downloadingNow = false;

		updateFileList(new OperationListener() {
			@Override
			public void progress(int progress) {
			}

			@Override
			public void finished(String message) {
				listener.progress(0);
				log("file list obtained");

				if (getSynchronizedPercent() >= 100) {
					log("all finished");
					listener.finished("all downloaded");
				} else {
					update(listener);
				}
			}

			@Override
			public void failed(String message) {
				log("can't get file list: " + message);
				listener.failed("can't get file list: " + message);
			}
		});
	}

	private void update(final OperationListener listener) {
		if (failCount >= maxFailCount) {
			log("stop updating, too much fails");
			listener.failed("too much fails: " + failCount);
			return;
		}
		
		if (stop) {
			listener.failed("cancelled");
			return;
		}

		for (final RemoteFile file : files.files) {
			log("checking file <" + file.filename);
			
			if (!isFileSynchronized(file)) {
				log("file <" + file.filename + " isn't synchronized");
				
				downloadFile(file, new OperationListener() {
					@Override
					public void progress(int progress) {
					}

					@Override
					public void finished(String message) {
						int progress = getSynchronizedPercent();
						if (progress == 100) {
							listener.finished("ok");
						} else {
							listener.progress(progress);
							update(listener);
						}
					}

					@Override
					public void failed(String message) {
						log("fail <" + message + ">");
						
						failCount++;
						update(listener);
					}
				});

				break;
			}
		}
	}

	private void updateFileList(final OperationListener listener) {
		HttpRequest req = new HttpRequest(HttpMethods.GET);
		req.setUrl(remoteURL + FILELIST_NAME);
		
		log("send request: <" + remoteURL + FILELIST_NAME + ">");

		Gdx.net.sendHttpRequest(req, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				try {
					files = FileList.fromJson(httpResponse.getResultAsString());
					listener.finished("ok");
				} catch (Exception e) {
					listener.failed(e.getMessage());
				}
			}

			@Override
			public void failed(Throwable t) {
				listener.failed(t.getMessage());
			}

			@Override
			public void cancelled() {
				listener.failed("cancelled");
			}
		});
	}

	private boolean isFileSynchronized(RemoteFile remoteFile) {
		String path = localFolder + remoteFile.folder;
		path = slashed(path) + remoteFile.filename;
		
		File handle = new File(path);
		if (handle.exists() && !handle.isDirectory() && remoteFile.checksum == FileUtils.getCRCChecksum(handle)) {
			return true;
		} else {
			return false;
		}
	}

	public int getSynchronizedPercent() {
		if (files == null) {
			return 0;
		}

		int synchronizedFileCount = 0;
		for (int i = 0, end = files.files.size; i < end; i++) {
			RemoteFile file = files.files.get(i);
			if (isFileSynchronized(file)) {
				synchronizedFileCount++;
			}
		}

		return 100 * synchronizedFileCount / files.files.size;
	}

	private void downloadFile(final RemoteFile remoteFile, final OperationListener listener) {
		if (downloadingNow) {
			return;
		}
		
		String url = slashed(remoteURL + remoteFile.folder);
		url += remoteFile.filename;
		
		HttpRequest req = new HttpRequest(HttpMethods.GET);
		req.setUrl(url);

		log("send request: <" + url + ">");
		Gdx.net.sendHttpRequest(req, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				downloadingNow = true;
				new File(localFolder + remoteFile.folder).mkdirs();

				File file = new File(slashed(localFolder + remoteFile.folder) + remoteFile.filename);

				FileOutputStream output = null;
				try {

					int totalRead = 0;
					output = new FileOutputStream(file);

					InputStream input = httpResponse.getResultAsStream();
					int bufferSize = 8192;

					byte[] buffer = new byte[bufferSize];
					int bytesRead;
					while ((bytesRead = input.read(buffer)) != -1) {
						output.write(buffer, 0, bytesRead);

						totalRead += bytesRead;
						if (totalRead >= 1024) {
							totalRead = 0;
						}
					}

					output.close();
					downloadingNow = false;
					
					if (isFileSynchronized(remoteFile)) {
						listener.finished("ok");
					} else {
						listener.failed("downloaded wrong file");
					}
				} catch (Exception e) {
					e.printStackTrace();
					listener.failed(e.getMessage());
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException noCatch) {
						}
					}
				}

				downloadingNow = false;
			}

			@Override
			public void failed(Throwable t) {
				log("failed in downloading file, reason: <" + t + ">");
				downloadingNow = false;
				listener.failed(t.getMessage());
			}

			@Override
			public void cancelled() {
				log("file downloading is cancelled");
				downloadingNow = false;
				listener.failed("cancelled");
			}
		});
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public boolean isLog() {
		return log;
	}

	public void stop() {
		stop = true;
		log("Synchronize stopped!");
	}

	public FileList getFiles() {
		return files;
	}

	public void setFiles(FileList files) {
		this.files = files;
	}
	
	private String slashed(String str) {
		if (str.endsWith("/")) {
			return str;
		} else {
			return str + "/";
		}
	}
}
