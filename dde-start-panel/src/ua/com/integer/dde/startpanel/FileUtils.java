package ua.com.integer.dde.startpanel;

import java.io.File;

import com.badlogic.gdx.utils.Array;

public class FileUtils {
	public static void clearDirectory(String dir) {
		File srcDir = new File(dir);
		srcDir.mkdirs();
		if (srcDir != null) {
			for(File file : srcDir.listFiles()) {
				deleteDirectory(file);
			}
		}
	}
	
	/**
	 * Delets a dir recursively deleting anything inside it.
	 * @param dir The dir to delete
	 * @return true if the dir was successfully deleted
	 */
	public static boolean deleteDirectory(File dir) {
	    if(! dir.exists() || !dir.isDirectory())    {
	        return false;
	    }

	    String[] files = dir.list();
	    for(int i = 0, len = files.length; i < len; i++)    {
	        File f = new File(dir, files[i]);
	        if(f.isDirectory()) {
	            deleteDirectory(f);
	        }else   {
	            f.delete();
	        }
	    }
	    return dir.delete();
	}
	
	public static void getFilesRecursivelyByExtensions(File startFile, Array<File> result, String extension) {
		if (startFile.isFile() && startFile.getName().endsWith(extension)) {
			result.add(startFile);
		}
		
		if (startFile.isDirectory()) {
			for(File file : startFile.listFiles()) {
				getFilesRecursivelyByExtensions(file, result, extension);
			}
		}
	}
	
	public static String getFilenameRelativeToParent(File file, File parent) {
		String name = file.getName();
		try {
			File tmpFile = new File(file.getAbsolutePath()).getParentFile();
			
			String parentPath = parent.getCanonicalPath();
			String currentPath = tmpFile.getCanonicalPath();
			
			while(!parentPath.equals(currentPath)) {
				name = tmpFile.getName() + "/" + name;
				parent = file.getParentFile();
				
				tmpFile = tmpFile.getParentFile();
				currentPath = tmpFile.getCanonicalPath();
			}
		} catch(Exception ex) {
			return name;
		}
		
		return name;
	}
	
	public static void main(String[] args) {
		
	}
}
