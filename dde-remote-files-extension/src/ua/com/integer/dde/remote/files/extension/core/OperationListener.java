package ua.com.integer.dde.remote.files.extension.core;

public interface OperationListener {
	public void finished(String message);
	public void progress(int progress);
	public void failed(String message);
}
