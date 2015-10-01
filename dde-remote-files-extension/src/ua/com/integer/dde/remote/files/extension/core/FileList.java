package ua.com.integer.dde.remote.files.extension.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class FileList {
	public int version;
	public Array<RemoteFile> files;
	
	public void print() {
		for(RemoteFile file : files) {
			System.out.println(file);
		}
	}
	
	public String toJson() {
		Json json = new Json();
		return json.prettyPrint(this);
	}
	
	public void saveToFile(File file) {
		try {
			FileWriter fWriter = new FileWriter(file);
			fWriter.write(toJson());
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FileList fromJson(String filelistInJson) {
		Json json = new Json();
		return json.fromJson(FileList.class, filelistInJson);
	}
	
	public static void generateFilelist(File folder) {
		if (folder.isFile()) throw new IllegalArgumentException(folder.getAbsolutePath() + " isn't folder!");
		
		FileList filelist = FileUtils.generateFileList(folder);
		filelist.saveToFile(new File(folder, "filelist.json"));
	}
	
	public static void main(String[] args) {
		generateFilelist(new File("/home/integer/farm-game/data"));
	}
}
