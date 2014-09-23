package ua.com.integer.dde.net.file.download;

public interface DownloadSubject {
	public void addDownloadListener(DownloadListener l);
	public void removeDownloadListener(DownloadListener l);
	public void notifyDownloadListeners();
}
