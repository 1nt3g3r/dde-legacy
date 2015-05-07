package ua.com.integer.dde.startpanel.image;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import ua.com.integer.dde.startpanel.util.color.StartsWithFilenameFilter;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class TexturePacker {
	private String imageDirectory = "./data/raw-img";
	private String outDirectory = "./data/image_packs";
	private String customPackName;
	private boolean packCustom;
	private boolean notificationsEnabled = true;
	
	public TexturePacker(String imageDirectory) {
		this();
		this.imageDirectory = imageDirectory;
	}
	
	public TexturePacker() {
		outDirectory = ua.com.integer.dde.startpanel.Settings.getInstance().getString("out-images-directory", "./data/img");
	}
	
	public void setImageDirectory(String directory) {
		this.imageDirectory = directory;
	}
	
	public void setOutDirectory(String outDirectory) {
		this.outDirectory = outDirectory;
	}
	
	private void pack() {
		File imgDirs = new File(imageDirectory);
		File[] resDirs = imgDirs.listFiles(new DirectoryFilter());
		if (resDirs == null) {
			JOptionPane.showMessageDialog(null, "Can't find image directory!");
			return;
		}
		for (File file : resDirs) {
			for (File resDirectory : file.listFiles(new DirectoryFilter())) {
				if (packCustom && !resDirectory.getName().equals(customPackName)) {
					continue;
				}
				
				packCustomDir(resDirectory.getPath(),
						outDirectory + "/" + file.getName() + "/",
						resDirectory.getName());
			}
		}

		if (notificationsEnabled) {
			JOptionPane.showMessageDialog(null, "Pack succesufully finished!");
		}
	}
	
	public void packSelected(String packName) {
		customPackName = packName;
		packCustom = true;
		pack();
	}
	
	public void packAll() {
		packCustom = false;
		pack();
	}

	private void packCustomDir(String inputPath, String outputPath,
			String packName) {
		System.out.println(outputPath);
		File[] toDelete = new File(outputPath).listFiles(new StartsWithFilenameFilter(packName));
		if (toDelete != null) {
			for(File toDelFile: toDelete) {
				toDelFile.delete();
			}
		}
		
		Settings sets = new Settings();
		sets.filterMin = TextureFilter.Linear;
		sets.filterMag = TextureFilter.Linear;
		sets.paddingX = 2;
		sets.paddingY = 2;
		
		File setsFile = new File(inputPath + "/pack.json");
		if (setsFile.exists()) {
			Scanner sc = null;
			try {
				sc = new Scanner(setsFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String text = "";
			while (sc.hasNextLine()) {
				text += sc.nextLine();
				if (sc.hasNextLine()) {
					text += "\n";
				}
			}
			sets = JsonWorker.JSON.fromJson(Settings.class, text);
			sets.stripWhitespaceX = true;
			sets.stripWhitespaceY = true;
		}
		
		sets.atlasExtension = ".pack";
		com.badlogic.gdx.tools.texturepacker.TexturePacker.process(sets, inputPath, outputPath, packName
				+ ".pack");
	}

	class DirectoryFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	}
	
	public void setNotificationsEnabled(boolean enabled) {
		this.notificationsEnabled = enabled;
	}
	
	public boolean isNotificationsEnabled(boolean enabled) {
		return notificationsEnabled;
	}
}
