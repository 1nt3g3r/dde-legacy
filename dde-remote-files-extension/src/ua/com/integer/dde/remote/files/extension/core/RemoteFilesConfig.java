package ua.com.integer.dde.remote.files.extension.core;

public class RemoteFilesConfig {
	public static final String DEFAULT_INTERNAL_FOLDER = "data/dde-remote-files-configs";
	
	public String localFolder;
	public String remoteURL;
	public boolean logEnabled;
	public int maxFailCount = 5;
	
	public RemoteFilesConfig() {
	}
	
	public RemoteFilesConfig(String localFolder, String remoteURL) {
		this.localFolder = localFolder;
		this.remoteURL = remoteURL;
	}
}
