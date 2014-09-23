package ua.com.integer.dde.net.file.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.utils.Array;

public class FileDownloader implements DownloadSubject, Runnable {
	private Array<DownloadListener> downloadListeners;
	
	private static final int DOWNLOAD_COMPLETE = 0;
	private static final int DOWNLOAD_PERCENT = 1;
	private static final int DOWNLOAD_ERROR = 2;
	private boolean needContinue;
	private boolean checkFileExists = true;
	private boolean checkFileSize;
	
	private int downloadState;
	
	private String url;
	private File outFile;
	private long totalSize, loadedSize;
	private float oldPercent;
	private Exception errorException;
	
	public FileDownloader() {
		downloadListeners = new Array<DownloadListener>();
	}
	
	public void setCheckFileExists(boolean checkFileExists) {
		this.checkFileExists = checkFileExists;
	}
	
	public void setCheckFileSize(boolean checkFileSize) {
		this.checkFileSize = checkFileSize;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}
	
	public void startDownloading() {
		downloadState = -1;
		needContinue = true;
		oldPercent = 0;
		new Thread(this).start();
	}
	
	public void stopDownloading() {
		needContinue = false;
	}
	
	public void downloadFile(String strURL, File outFile) {
		if (checkFileExists && !checkFileSize) {
			if (outFile.exists()) {
				downloadState = DOWNLOAD_COMPLETE;
				notifyDownloadListeners();
				return;
			}
		}
        try {
            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            totalSize = Long.parseLong(urlconn.getHeaderField("Content-Length"));
            if (checkFileExists && checkFileSize && outFile.exists() && outFile.length() == totalSize) {
            	downloadState = DOWNLOAD_COMPLETE;
            	notifyDownloadListeners();
            	return;
            }
            InputStream in = null;
            in = urlconn.getInputStream();
            OutputStream writer = new FileOutputStream(outFile);
            byte buffer[] = new byte[1024];
            int c = in.read(buffer);
            while (c > 0 && needContinue) {
            	loadedSize += c;
            	float percent = (float) (loadedSize)/(totalSize);
            	if (percent - oldPercent >= 0.01f) {
            		oldPercent = percent;
            		downloadState = DOWNLOAD_PERCENT;
            		notifyDownloadListeners();
            	}
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
            
            downloadState = DOWNLOAD_COMPLETE;
            notifyDownloadListeners();
        } catch (IOException e) {
        	this.errorException = e;
        	downloadState = DOWNLOAD_ERROR;
        	notifyDownloadListeners();
        }
    }

	@Override
	public void addDownloadListener(DownloadListener l) {
		downloadListeners.add(l);
	}

	@Override
	public void removeDownloadListener(DownloadListener l) {
		downloadListeners.removeValue(l, true);
	}

	@Override
	public void notifyDownloadListeners() {
		for(DownloadListener l : downloadListeners) {
			switch(downloadState) {
			case DOWNLOAD_COMPLETE:
				l.downloadFinished();
				break;
			case DOWNLOAD_PERCENT:
				l.downloadPercentFinished(oldPercent);
				break;
			case DOWNLOAD_ERROR:
				l.downloadFailed(errorException);
				break;
			}
		}
	}

	@Override
	public void run() {
		downloadFile(url, outFile);
	}
}
