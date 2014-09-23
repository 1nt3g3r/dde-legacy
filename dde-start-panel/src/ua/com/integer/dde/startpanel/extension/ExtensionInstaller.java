package ua.com.integer.dde.startpanel.extension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;
import ua.com.integer.dde.util.JsonWorker;

public class ExtensionInstaller {
	private InstallDescription description;
	private ZipFile zip;
	
	public ExtensionInstaller(File archive) throws ZipException, IOException {
		zip = new ZipFile(archive);
		ZipEntry descriptionEntry = zip.getEntry("description.json");
		if (descriptionEntry == null) {
			throw new IllegalStateException("Bad extension archive format: description.json not found!");
		}
		
		description = JsonWorker.JSON.fromJson(InstallDescription.class, zip.getInputStream(descriptionEntry));
	}
	
	public void install() {
		if (ProjectFinder.findDesktopProject() == null) throw new IllegalStateException("Desktop project not found!");
		if (ProjectFinder.findKernelProject() == null) throw new IllegalStateException("Kernel project not found");
		if (ProjectFinder.findAndroidProject() == null) throw new IllegalStateException("Android project not found");
		
		copyMainLibs();
		copyDesktopLibs();
		copyAndroidLibs();
	}
	
	private void copyMainLibs() {
		String kernelPath = ProjectFinder.findKernelProject();
		
		for(String mainLibName : description.mainJars){
			copyFile(mainLibName, kernelPath + "/libs/" + mainLibName);
		}
	}
	
	private void copyDesktopLibs() {
		String desktopPath = ProjectFinder.findDesktopProject();
		
		for(String mainLibName : description.desktopJars){
			copyFile(mainLibName, desktopPath + "/libs/" + mainLibName);
		}
	}
	
	private void copyAndroidLibs() {
		String androidPath = ProjectFinder.findAndroidProject();
		
		for(String mainLibName : description.desktopJars){
			copyFile(mainLibName, androidPath + "/libs/" + mainLibName);
		}
	}
	
	private void copyFile(String fileInZip, String path) {
		try {
			new File(path).getParentFile().mkdirs();
			new File(path).createNewFile();
			
			InputStream inStream = zip.getInputStream(zip.getEntry(fileInZip));
			System.out.println(path);
			
			FileOutputStream outStream = new FileOutputStream(new File(path));
			byte[] buffer = new byte[1024];
			int readCount;
			while((readCount = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, readCount);
			}
			
			outStream.flush();
			outStream.close();
			
			inStream.close();
			
		} catch (Exception e) {
			throw new IllegalStateException("Error duing copy " + fileInZip + " to " + path + "!\n" + e.getMessage());
		}
	}
	

	public static void main(String[] args) throws ZipException, IOException {
		ExtensionInstaller libManager = new ExtensionInstaller(new File("dde-ext-lua.zip"));
		libManager.install();
		
	}
}
