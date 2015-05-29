package ua.com.integer.dde.extension.ui.editor.property.edit.textureregion;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;
import ua.com.integer.dde.startpanel.DDEStartPanel;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.image.ImageSelectionListener;

public class TextureRegionEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -7630890627138520749L;
	private JButton chooseImageButton;

	private PropertyChangeListener listener;
	private JButton clearImageButton;
	
	/**
	 * Create the panel.
	 */
	public TextureRegionEditPanel() {
		defaultValue = "";
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		
		chooseImageButton = new JButton("Choose...");
		chooseImageButton.addActionListener(new ChooseImageListener());
		
		clearImageButton = new JButton("Clear");
		clearImageButton.addActionListener(new ClearRegionListener());
		clearImageButton.setBackground(Color.LIGHT_GRAY);
		add(clearImageButton);
		
		add(Box.createHorizontalStrut(20));
		add(chooseImageButton);
		chooseImageButton.setBackground(Color.LIGHT_GRAY);

		if (DDEStartPanel.isInitialized()) {
			Settings.getInstance().setSettingsClass(DDEStartPanel.getInstance().getKernel().getClass());
		}
	}

	class ChooseImageListener extends ImageSelectionListener {
		@Override
		public void imageSelected() {
			if (config != null && uiPropertyName != null) {
				String result = getPack() + ";" + getRegion();
				config.set(uiPropertyName, result);
				
				if (listener != null) {
					listener.propertyChanged();
				}
			}
		}
	}
	
	class ClearRegionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.properties.remove(uiPropertyName);
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}

	public static void main(String[] args) {
		FrameTools.testingFrame(new TextureRegionEditPanel());
	}
}
