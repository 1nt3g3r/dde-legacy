package ua.com.integer.dde.net.file.sync;

public interface SyncListener {
	public void noInet();
	public void failSync();
	public void dontNeedSync();
	public void beginSync();
	public void percentSync(float percent);
	public void endSync();
	
}
