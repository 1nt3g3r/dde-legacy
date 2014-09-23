package ua.com.integer.dde.startpanel.ddestub;

import java.io.File;

import com.badlogic.gdx.utils.Array;

public class ProjectFinder {
	private static String androidProjectPath;
	private static String desktopProjectPath;
	private static String kernelProjectPath;
	
	private static void findProjects() {
		Array<File> projects = new Array<File>();
		File[] topLevelDirectories = new File("../").listFiles();
		
		for(File file : topLevelDirectories) {
			if (file.isDirectory()) {
				for(File content : file.listFiles()) {
					if (content.getName().equals(".project")) {
						projects.add(file);
						continue;
					}
				}
			}
		}
		
		for(File f : projects) {
			if (f.getName().contains("android")) {
				androidProjectPath = "../" + f.getName();
			} else if (f.getName().contains("desktop")) {
				desktopProjectPath = "../" + f.getName();
			} else if (f.getName().contains("core")) {
				kernelProjectPath = "../" + f.getName();
			}
		}
		
		int nameLength = 0;
		for(File f : projects) {
			if (androidProjectPath != null && androidProjectPath.contains(f.getName()) && desktopProjectPath.contains(f.getName())) {
				if (f.getName().length() > nameLength) {
					kernelProjectPath = "../" + f.getName();
					nameLength = f.getName().length();
				}
			}
		}
	}
	
	public static String findAndroidProject() {
		findProjects();
		return androidProjectPath;
	}
	
	public static String findDesktopProject() {
		findProjects();
		return desktopProjectPath;
	}
	
	public static String findKernelProject() {
		findProjects();
		return kernelProjectPath;
	}
}
