package ua.com.integer.dde.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import com.badlogic.gdx.utils.Json;

public class JsonWorker {
	public static final Json JSON = new Json();
	
	public static <T> T fromJson(Class<T> type, File file) {
		try {
			Scanner sc = new Scanner(file, "utf-8");
			StringBuilder result = new StringBuilder();
			while(sc.hasNextLine()) {
				result.append(sc.nextLine());
				if (sc.hasNextLine()) {
					result.append("\n");
				}
			}
			sc.close();
			return JSON.fromJson(type, result.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void toJson(Object object, File file) {
		BufferedWriter fWriter;
		try {
			fWriter =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			fWriter.write(JSON.prettyPrint(object));
			fWriter.flush();
			fWriter.close();
		} catch (Exception e) {
		}
	}
	
	public static void createEmptyJson(File file) {
		toJson(new Object(), file);
	}
}
