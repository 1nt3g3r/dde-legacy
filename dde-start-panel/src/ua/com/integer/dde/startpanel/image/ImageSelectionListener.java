package ua.com.integer.dde.startpanel.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public abstract class ImageSelectionListener implements ActionListener {
	private boolean customImageButtonVisible;
	private String pack;
	private String region;
	private ImageChooser imChooser;
	
	public void setCustomImageButtonVisible(boolean customImageButtonVisible) {
		this.customImageButtonVisible = customImageButtonVisible;
	}
	
	public String getPack() {
		return pack;
	}
	
	public String getRegion() {
		return region;
	}
	
	public ImageChooser getImageChooser() {
		return imChooser;
	}
	
	class OkClickedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(imChooser.packAndRegionSelected()) {
				pack = imChooser.getPack();
				region = imChooser.getRegion();
				imageSelected();
				imChooser.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Select correct pack and image!");
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		imChooser = new ImageChooser();
		imChooser.setCustomImageButtonVisible(customImageButtonVisible);
		imChooser.setModal(true);
		imChooser.updatePacks();
		imChooser.getOkButton().addActionListener(new OkClickedListener());
		imChooser.setVisible(true);
	}
	
	public abstract void imageSelected();
}
