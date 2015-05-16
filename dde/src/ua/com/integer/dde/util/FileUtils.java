package ua.com.integer.dde.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Helper class for file operations
 * 
 * @author 1nt3g3r
 */
public class FileUtils {
	/** 
	 * Converts classpath resource into a file
	 * 
	 * @param classpathFileName full classpath resource. Example: /com/test/icon.png
	 * 
	 * @return read-only file for future use
	 */
	public static File convertClasspathToFile(String classpathFileName) {
		URL url = FileUtils.class.getClassLoader().getResource(classpathFileName);
		try {
			if (url == null) {
				return null;
			}
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
