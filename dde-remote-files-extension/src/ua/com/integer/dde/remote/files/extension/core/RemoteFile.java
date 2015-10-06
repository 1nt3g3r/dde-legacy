package ua.com.integer.dde.remote.files.extension.core;

public class RemoteFile {
	public String folder;
	public String filename;
	public int checksum;
	
	public RemoteFile() {
	}
	
	public RemoteFile(String folder, String filename, int checksum) {
		this.folder = folder;
		this.filename = filename;
		this.checksum = checksum;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RemoteFile) {
			RemoteFile other = (RemoteFile) obj;
			
			return other.folder.equals(folder) && 
				   other.filename.equals(filename) && 
				   other.checksum == checksum;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return folder + "/" + filename + ", crc: " + checksum;
	}
}
