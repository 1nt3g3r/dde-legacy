package ua.com.integer.dde.net.file.download;

public interface DownloadListener {
	public void downloadFinished();
	public void downloadPercentFinished(float percent);
	public void downloadFailed(Exception e);
}
