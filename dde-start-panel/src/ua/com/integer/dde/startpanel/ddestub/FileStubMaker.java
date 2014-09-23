package ua.com.integer.dde.startpanel.ddestub;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileStubMaker {
	private String content = "";

	public void loadFile(String path) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(path);
		Scanner sc = new Scanner(in, "utf8");

		content = "";
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			content += nextLine;
			
			if (sc.hasNextLine()) {
				content += "\n";
			}
		}
		sc.close();
	}

	public void loadFileFromRes(String filename) {
		loadFile("ua/com/integer/dde/res/" + filename);
	}

	public void set(String key, String value) {
		content = content.replace("$" + key, value);
	}
	
	public void saveToFile(File file) {
		try {
			FileWriter fWriter = new FileWriter(file);
			fWriter.write(content);
			fWriter.flush();
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveToFile(String filename) {
		saveToFile(new File(filename));
	}

	public String getResult() {
		return content;
	}
}
