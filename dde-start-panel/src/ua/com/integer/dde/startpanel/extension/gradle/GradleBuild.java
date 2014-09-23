package ua.com.integer.dde.startpanel.extension.gradle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.badlogic.gdx.utils.Array;

public class GradleBuild {
	private Array<String> gradleLines;
	
	public GradleBuild(File gradleBuildFile) {
		try {
			Scanner sc = new Scanner(gradleBuildFile);
			gradleLines = new Array<String>();
			while(sc.hasNextLine()) {
				gradleLines.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void print() {
		for(int i = 0; i < gradleLines.size; i++) {
			System.out.println(i + ": " + gradleLines.get(i));
		}
	}
	
	public int getDependencyLineNumberForProject(String projectName) {
		boolean projectNameFound = false;
		for(int i = 0; i < gradleLines.size; i++) {
			if (gradleLines.get(i).contains("project(\":" + projectName + "\"") && !gradleLines.get(i).contains("compile") && !gradleLines.get(i).contains("native")) {
				projectNameFound = true;
			}
			
			if (gradleLines.get(i).contains("dependencies") && projectNameFound) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void addCompileLibraryDependency(String project, String librayName) {
		int dependencyLineIndex = getDependencyLineNumberForProject(project);
		if (dependencyLineIndex != -1) {
			gradleLines.insert(dependencyLineIndex + 1, "        compile \"" + librayName + "\"");
		}
	}
	
	public void removeCompileLibraryDependency(String project, String libraryName) {
		int dependencyLineIndex = getDependencyLineNumberForProject(project);
		if (dependencyLineIndex != -1) {
			for(int i = dependencyLineIndex; i < gradleLines.size; i++) {
				String line = gradleLines.get(i);
				
				if (line.trim().equals("}")) {
					return;
				}
				
				if (line.contains(libraryName) && !line.contains("project") && !line.contains("files")) {
					gradleLines.removeIndex(i);
					return;
				}
			}
		}
	}
	
	public void addCompileProjectDependency(String project, String dependencyProject) {
		int dependencyLineIndex = getDependencyLineNumberForProject(project);
		if (dependencyLineIndex != -1) {
			gradleLines.insert(dependencyLineIndex + 1, "        compile project(\":" + dependencyProject +"\")");
		}
	}
	
	public void removeCompileProjectDependence(String project, String dependency) {
		int dependencyLineIndex = getDependencyLineNumberForProject(project);
		if (dependencyLineIndex != -1) {
			for(int i = dependencyLineIndex; i < gradleLines.size; i++) {
				if (gradleLines.get(i).trim().equals("}")) {
					return;
				}
				
				if (gradleLines.get(i).contains("compile project") && gradleLines.get(i).contains(dependency)) {
					gradleLines.removeIndex(i);
					return;
				}
			}
		}
	}
	
	public void addCompileJarDependency(String project, String relativeJarPath) {
		int dependencyLineIndex = getDependencyLineNumberForProject(project);
		if (dependencyLineIndex != -1) {
			gradleLines.insert(dependencyLineIndex + 1, "        compile files('" + relativeJarPath + "')");
		}
	}
	
	public void removeCompileJarDependency(String project, String relativeJarPath) {
		int dependencyLineIndex = getDependencyLineNumberForProject(project);
		if (dependencyLineIndex != -1) {
			for(int i = dependencyLineIndex; i < gradleLines.size; i++) {
				String line = gradleLines.get(i);
				if (line.trim().equals("}")) {
					return;
				}
				
				if (line.contains("compile files") && line.contains(relativeJarPath)) {
					gradleLines.removeIndex(i);
					return;
				}
			}
		}
	}
	
	public void insertDependency(GradleDependency dependency) {
		switch(dependency.type) {
		case JAR: addCompileJarDependency(dependency.projectName, dependency.dependencyName);
			break;
		case LIBRARY: addCompileLibraryDependency(dependency.projectName, dependency.dependencyName);
			break;
		case PROJECT: addCompileProjectDependency(dependency.projectName, dependency.dependencyName);
			break;
		default:
			break;
		}
	}
	
	public void removeDependency(GradleDependency dependency) {
		switch(dependency.type) {
		case JAR :
			removeCompileJarDependency(dependency.projectName, dependency.dependencyName);
			break;
		case LIBRARY :
			removeCompileLibraryDependency(dependency.projectName, dependency.dependencyName);
			break;
		case PROJECT :
			removeCompileProjectDependence(dependency.projectName, dependency.dependencyName);
			break;
		}
	}
	
	public static void main(String[] args) {
		GradleBuild gradleBuild = new GradleBuild(new File("../build.gradle"));
		gradleBuild.print();
		
		System.out.println("--");
		
		gradleBuild.addCompileLibraryDependency("core", "xom:xom:2.1");
		gradleBuild.removeCompileLibraryDependency("core", "xom:xom:2.1");
		gradleBuild.print();
	}
}
