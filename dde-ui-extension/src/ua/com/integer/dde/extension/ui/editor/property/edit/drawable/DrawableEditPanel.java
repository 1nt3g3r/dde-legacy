package ua.com.integer.dde.extension.ui.editor.property.edit.drawable;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;
import ua.com.integer.dde.startpanel.FrameTools;

public class DrawableEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -1367789460763803249L;

	/**
	 * Create the panel.
	 */
	public DrawableEditPanel() {
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearDrawableListener());
		btnClear.setBackground(Color.LIGHT_GRAY);
		add(btnClear);
		
		JButton btnSetupDrawable = new JButton("Choose...");
		btnSetupDrawable.addActionListener(new DrawableListener());
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		btnSetupDrawable.setBackground(Color.LIGHT_GRAY);
		add(btnSetupDrawable);

	}

	class ClearDrawableListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.properties.remove(uiPropertyName);
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
	
	class DrawableListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null && listener != null) {
				DrawableEditor editor = new DrawableEditor();
				editor.setConfig(config);
				editor.setUiPropertyName(uiPropertyName);
				editor.setPropertyChangedListener(listener);
				editor.setModal(true);
				FrameTools.situateOnCenter(editor);
				editor.setVisible(true);
			}
		}
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		setLabel(propertyName);
	}

	@Override
	public String getDefaultValue() {
		return null;
	}
}
