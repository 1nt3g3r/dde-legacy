package ua.com.integer.dde.startpanel.ddestub;

import java.io.File;

import javax.swing.JOptionPane;

import ua.com.integer.dde.startpanel.FileUtils;


public class DDEMorpher {
	private boolean morphKernel;
	private boolean morphDesktop;
	private boolean morphAndroid;
	private boolean androidWallpaper;
	
	private String packageName;
	private String mainClassname;
	private String kernelProjectPath;
	private String desktopProject;
	private String androidProject;
	
	public boolean isMorphKernel() {
		return morphKernel;
	}

	public void setMorphKernel(boolean morphKernel) {
		this.morphKernel = morphKernel;
	}

	public boolean isMorphDesktop() {
		return morphDesktop;
	}

	public void setMorphDesktop(boolean morphDesktop) {
		this.morphDesktop = morphDesktop;
	}

	public boolean isMorphAndroid() {
		return morphAndroid;
	}

	public void setMorphAndroid(boolean morphAndroid) {
		this.morphAndroid = morphAndroid;
	}

	public boolean isAndroidWallpaper() {
		return androidWallpaper;
	}

	public void setAndroidWallpaper(boolean androidWallpaper) {
		this.androidWallpaper = androidWallpaper;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getMainClassname() {
		return mainClassname;
	}

	public void setMainClassname(String mainClassname) {
		this.mainClassname = mainClassname;
	}

	public String getKernelProjectPath() {
		return kernelProjectPath;
	}

	public void setKernelProjectPath(String kernelProjectPath) {
		this.kernelProjectPath = kernelProjectPath;
	}

	public String getDesktopProject() {
		return desktopProject;
	}

	public void setDesktopProject(String desktopProject) {
		this.desktopProject = desktopProject;
	}

	public String getAndroidProject() {
		return androidProject;
	}

	public void setAndroidProject(String androidProject) {
		this.androidProject = androidProject;
	}

	public void createStub() {
		System.out.println("Start morphing...");
		if (morphKernel) {
			createKernelProjectStub();
			System.out.println("Kernel project morphed.");
		}
		if (morphDesktop) {
			createDesktopProjectStub();
			System.out.println("Desktop project morphed.");
		}
		if (morphAndroid) {
			createAndroidProjectStub();
			System.out.println("Android project morphed.");
		}
		System.out.println("Morph finished.");
	}
	
	private void createKernelProjectStub() {
		FileUtils.clearDirectory(kernelProjectPath+"/src");
		createPackageInDir(packageName, kernelProjectPath+"/src");
		createKernelClass();
	}
	
	private void createKernelClass() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("srcstub/DDEKernelStub");
		fMaker.set("packageName", packageName);
		fMaker.set("ddeKernelName", mainClassname);
		fMaker.set("androidPath", ProjectFinder.findAndroidProject());
		fMaker.saveToFile(getKernelSrcDir() + "/" + mainClassname + ".java");
	}
	
	private void createDesktopProjectStub() {
		FileUtils.clearDirectory(desktopProject+"/src");
		createPackageInDir(packageName, desktopProject+"/src");
		createDesktopClassStarter();
	}
	
	private void createDesktopClassStarter() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("srcstub/DesktopStarterStub");
		fMaker.set("packageName", packageName);
		fMaker.set("ddeEngine", mainClassname);
		fMaker.saveToFile(getDesktopSrcDir() + "/DesktopStarter.java");
	}
	
	private void createAndroidProjectStub() {
		FileUtils.clearDirectory(androidProject+"/src");
		FileUtils.clearDirectory(androidProject+"/res/layout");
		FileUtils.clearDirectory(androidProject+"/res/xml");
		createPackageInDir(packageName, androidProject+"/src");
		createAntProperitesFile();
		
		if (androidWallpaper) {
			createAndroidLiveWallpaperStub();
			createAndroidManifestForWallpaper();
			System.out.println("\tAndroid live wallpaper created.");
		} else {
			createAndroidApplicationStub();
			createAndroidManifestForActivity();
			System.out.println("\tAndroid activity created.");
		}
	}
	
	private void createAndroidManifestForWallpaper() {
		FileStubMaker manifest = new FileStubMaker();
		manifest.loadFileFromRes("xml/manifest/AndroidWallpaperManifestStub.xml");
		manifest.set("packageName", packageName);
		manifest.set("serviceName", ".WallpaperService");
		manifest.set("fullActivitySettingsName", packageName + ".WallpaperSettings");
		manifest.saveToFile(androidProject+"/AndroidManifest.xml");
	}
	
	private void createAndroidApplicationStub() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("srcstub/AndroidActivityStub");
		fMaker.set("ddeEngineClassName", mainClassname);
		fMaker.set("packageName", packageName);
		fMaker.saveToFile(new File(getAndroidSrcDir() + "/GameActivity.java"));
	}
	
	private void createAndroidManifestForActivity() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("xml/manifest/AndroidActivityManifestStub.xml");
		fMaker.set("packageName", packageName);
		fMaker.saveToFile(new File(androidProject + "/AndroidManifest.xml"));
	}
	
	private void createAndroidLiveWallpaperStub() {
		createClassForAndroidWallpaperService();
		createClassForAndroidWallpaperListenerDecorator();
		createClassforAndroidSettingsActivity();
		createLayoutForSettingForAndroidLiveWallpaper();
		createXmlFileForSettingsActivity();
		createValuesXmlFile();
	}
	
	private void createClassForAndroidWallpaperService() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("srcstub/AndroidWallpaperServiceStub");
		fMaker.set("packageName", packageName);
		fMaker.set("ddeEngine", mainClassname);
		fMaker.saveToFile(getAndroidSrcDir() + "/WallpaperService.java");
	}
	
	private void createClassForAndroidWallpaperListenerDecorator() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("srcstub/AndroidWallpaperListenerDecoratorStub");
		fMaker.set("packageName", packageName);
		fMaker.set("ddeEngine", mainClassname);
		fMaker.saveToFile(getAndroidSrcDir() + "/" + mainClassname + "Decorator.java");
	}
	
	private void createClassforAndroidSettingsActivity() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("srcstub/WallpaperSettingsActivityStub");
		fMaker.set("packageName", packageName);
		fMaker.saveToFile(getAndroidSrcDir() + "/WallpaperSettings.java");
	}
	
	private void createLayoutForSettingForAndroidLiveWallpaper() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("xml/wallpaper_settings.xml");
		fMaker.saveToFile(androidProject + "/res/layout/wallpaper_settings.xml");
	}
	
	private void createXmlFileForSettingsActivity() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("xml/wallpaper.xml");
		fMaker.set("packageName", packageName);
		fMaker.saveToFile(androidProject+"/res/xml/wallpaper.xml");
	}
	
	private void createAntProperitesFile() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("ant.properties");
		fMaker.set("kernelProjectName", kernelProjectPath);
		fMaker.saveToFile(androidProject+"/ant.properties");
	}
	
	private void createValuesXmlFile() {
		FileStubMaker fMaker = new FileStubMaker();
		fMaker.loadFileFromRes("xml/strings.xml");
		fMaker.saveToFile(androidProject+"/res/values/strings.xml");
	}
	
	private String decodePackageIntoDirectory(String packageName) {
		String[] packageNameParts = packageName.split("\\.");
		String fullDirectoryPath = "";
		for(int i = 0; i < packageNameParts.length; i++) {
			fullDirectoryPath += packageNameParts[i];
			if (i != packageNameParts.length - 1) {
				fullDirectoryPath += "/";
			}
		}
		return fullDirectoryPath;
	}
	
	private void createPackageInDir(String packageName, String directory) {
		if(!new File(directory + "/" + decodePackageIntoDirectory(packageName)).mkdirs()) {
			JOptionPane.showMessageDialog(null, "Can't create package!");
			System.exit(0);
		}
	}
	
	private String getAndroidSrcDir() {
		return androidProject+"/src/"+decodePackageIntoDirectory(packageName);
	}
	
	private String getKernelSrcDir() {
		return kernelProjectPath+"/src/"+decodePackageIntoDirectory(packageName);
	}
	
	private String getDesktopSrcDir() {
		return desktopProject+"/src/"+decodePackageIntoDirectory(packageName);
	}
}
