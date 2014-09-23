package ua.com.integer.dde.startpanel.extension;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JOptionPane;

import ua.com.integer.dde.util.JsonWorker;

public class ExtensionInfo {
	public static final String PATH = "./extensions/";
	public String jarName;
	public String startClass;
	public String name;
	public String description;
	private URLClassLoader classLoader;
	
	public Extension getExtension() throws Exception {
		URL moduleURL = new URL("file:" + PATH +jarName);
		URL altURL = new URL(new URL("file:"), "./" + jarName);
		classLoader = new URLClassLoader(
				new URL[] { moduleURL, altURL });
		@SuppressWarnings("unchecked")
		Class<? extends Extension> moduleClass = (Class<? extends Extension>) classLoader
				.loadClass(startClass);
		Extension module = moduleClass.newInstance();
		return module;
	}
	
	public static ExtensionInfo getFromFile(File file) {
		ExtensionInfo toReturn = JsonWorker.fromJson(ExtensionInfo.class, file);
		return toReturn;
	}
	
	public void launchNewInstance() {
		try {
			getExtension().launch();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can't launch extension " + name);
			e.printStackTrace();
		}
	}
	
	public static void launchExtension(String extensionName) {
		getFromFile(new File(PATH + extensionName + ".dde")).launchNewInstance();
	}
	
	public String getExtensionName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
