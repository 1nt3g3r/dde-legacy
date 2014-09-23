package ua.com.integer.dde.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtils {
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
