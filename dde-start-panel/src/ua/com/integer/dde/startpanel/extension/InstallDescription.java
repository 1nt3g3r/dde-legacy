package ua.com.integer.dde.startpanel.extension;

import com.badlogic.gdx.utils.Array;

public class InstallDescription {
	public Array<String> mainJars;
	public Array<String> desktopJars;
	public Array<String> androidJars;
	public Array<String> mainExportLibs;
	public Array<String> desktopExportLibs;
	public Array<String> androidExportLibs;
	public Array<String> androidSoLibs;
	
	public InstallDescription() {
		mainJars = new Array<String>();
		desktopJars = new Array<String>();
		androidJars = new Array<String>();
		mainExportLibs = new Array<String>();
		desktopExportLibs = new Array<String>();
		androidExportLibs = new Array<String>();
		androidSoLibs = new Array<String>();
	}
}
