package ua.com.integer.dde.startpanel.start;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.image.TexturePacker;
import ua.com.integer.dde.startpanel.util.AtlasFilenameFilter;
import ua.com.integer.dde.startpanel.util.Modify;
import ua.com.integer.dde.startpanel.util.ResolutionPackFilenameFilter;

import com.badlogic.gdx.utils.Array;

public class StartRunnable implements Runnable {
	@Override
	public void run() {
		Array<File> packsToRepack = new Array<File>();
		
		boolean needCheckSourceImagesModify = Settings.getInstance().getBoolean("check-source-images-modified", true);
		if (needCheckSourceImagesModify) {
			File[] packDirectories = getPackDirectories();
			for(File file: packDirectories) {
				boolean needRepack = isTextureAtlasModified(file.getAbsolutePath());
				if (needRepack) {
					packsToRepack.add(file);
				}
			}
		}
		
		if (shouldRepackChangedPacks(packsToRepack)) {
			TexturePacker packer = new TexturePacker(getImageDirectory());
			packer.setNotificationsEnabled(false);
			for(File file : packsToRepack) {
				String packName = file.getName();
				packer.packSelected(packName);
				saveAtlasCRC(file.getAbsolutePath());
			}

			JOptionPane.showMessageDialog(null, "All atlases were repacked!");
		}
	}
	
	private boolean shouldRepackChangedPacks(Array<File> changedPacks) {
		if (changedPacks.size <= 0) {
			return false;
		}
		
		int result = JOptionPane.showConfirmDialog(null, createQueryReport(changedPacks), "Atlases were changed", JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION;
	}
	
	private String createQueryReport(Array<File> changedPacks) {
		String result = "Some image atlas were changed. List of changed atlases:\n\n";
		int index = 0;
		for(File changedPack: changedPacks) {
			String line = (++index) + ". " + changedPack.getName() + "\n";
			result += line;
		}
		
		result += "\nRepack changed atlases?";
		return result;
	}

	private String getImageDirectory() {
		final String imageDirectory = Settings.getInstance().getString("raw-images-directory", "../res/raw-images");
		return imageDirectory;
	}
	
	public boolean isTextureAtlasModified(String atlasDirectory) {
		try {
			String oldCRC = Settings.getInstance().getString("raw-images-directory-crc-" + new File(atlasDirectory).getName(), "");
			String newCRC = new Modify().getFolderCrc(new File(atlasDirectory)) + "";
			return !oldCRC.equals(newCRC);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	private void saveAtlasCRC(String atlas) {
		try {
			String newCRC = new Modify().getFolderCrc(new File(atlas)) + "";
			Settings.getInstance().putString("raw-images-directory-crc-" + new File(atlas).getName(), newCRC);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private File[] getPackDirectories() {
		if (!new File(getImageDirectory()).exists()) {
			return new File[0];
		}
		
		File[] imageDirectories = new File(getImageDirectory()).listFiles(new ResolutionPackFilenameFilter());
		if (imageDirectories.length > 0) {
			return imageDirectories[0].listFiles(new AtlasFilenameFilter());
		} else {
			return new File[0];
		}
	}
}
