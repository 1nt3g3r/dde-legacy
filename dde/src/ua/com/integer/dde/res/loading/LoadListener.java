package ua.com.integer.dde.res.loading;

public interface LoadListener {
	public void beginLoad();
	public void loadPercent(float percent);
	public void endLoad();
}
