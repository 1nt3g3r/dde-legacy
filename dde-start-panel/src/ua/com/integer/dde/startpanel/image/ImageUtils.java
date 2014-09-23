package ua.com.integer.dde.startpanel.image;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ImageUtils {
	public static final String DEF_RAW_IMAGES_PATH = "./data/raw-img";
	
	public static void setSelectedImageAsIcon(File imageFile, JButton button) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't preview selected image!");
			return;
		}
		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();
		
		if (imgWidth <= button.getWidth() && imgHeight <= button.getHeight()) {
			ImageIcon icon = new ImageIcon(img);
			button.setIcon(icon);
		} else {
			int max = imgWidth;
			if (imgHeight > max) {
				max = imgHeight;
			}
			
			float scaleCoeff = (float) button.getWidth() / (float) max;
			BufferedImage newImage = resize(img, (int) (scaleCoeff*imgWidth), (int) (scaleCoeff*imgHeight));
			ImageIcon icon = new ImageIcon(newImage);
			button.setIcon(icon);
		}
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	
	public static File getFileForPackAndRegion(String pack, String region) {
		if (pack == null || region == null) {
			return null;
		}
		File packDir = new File(DEF_RAW_IMAGES_PATH).listFiles()[0];
		File regionFile = null;
		try {
			regionFile = new File(packDir.getPath()+"/" + pack + "/" + region+".png");
		} catch (Exception e) {
			return null;
		}
		return regionFile;
	}
}
