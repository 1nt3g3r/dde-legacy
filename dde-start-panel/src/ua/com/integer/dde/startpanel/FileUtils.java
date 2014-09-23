package ua.com.integer.dde.startpanel;

import java.io.File;

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
}
