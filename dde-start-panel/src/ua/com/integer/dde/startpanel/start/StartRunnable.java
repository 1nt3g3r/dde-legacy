package ua.com.integer.dde.startpanel.start;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ua.com.integer.dde.startpanel.FileUtils;
import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.WaitWindow;
import ua.com.integer.dde.startpanel.image.TexturePacker;
import ua.com.integer.dde.startpanel.util.Modify;

public class StartRunnable implements Runnable {
	private WaitWindow waitWindow;
	
	@Override
	public void run() {
		boolean needCheckSourceImagesModify = Settings.getInstance()
				.getBoolean("check-source-images-modified", true);
		final String imageDirectory = Settings.getInstance().getString(
				"raw-images-directory", "./");
		if (needCheckSourceImagesModify && new File(imageDirectory).exists()
				&& !imageDirectory.equals("./")) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					waitWindow = new WaitWindow();
					waitWindow.setVisible(true);
				}
			});
			String oldCrc = Settings.getInstance().getString(
					"raw-images-directory-crc", "");

			try {
				String newCrc = new Modify().getFolderCrc(new File(
						imageDirectory)) + "";

				if (!newCrc.equals(oldCrc)) {
					if (JOptionPane
							.showConfirmDialog(
									null,
									"Source image directory was modified. Do you want to repack all packs?",
									"Source images checker",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						Settings.getInstance().putString(
								"raw-images-directory-crc", newCrc);

						FileUtils
								.clearDirectory(Settings.getInstance()
										.getString("out-images-directory",
												"./data/img"));
						TexturePacker packer = new TexturePacker(imageDirectory);
						packer.packAll();
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Error during checking source images directory modifying!\n"
								+ e.getMessage());
			}
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					waitWindow.dispose();
				}
			});

		}

	}

}
