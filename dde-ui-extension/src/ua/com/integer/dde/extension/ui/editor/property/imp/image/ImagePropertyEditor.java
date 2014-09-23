package ua.com.integer.dde.extension.ui.editor.property.imp.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.TitlePanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.scaling.ScalingEditPanel;

public class ImagePropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = 320977260364332927L;
	private DrawableEditPanel drawableEditPanel;
	private ScalingEditPanel scalingEditPanel;
	private ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel alignEditPanel;

	/**
	 * Create the panel.
	 */
	public ImagePropertyEditor() {
		setPreferredSize(new Dimension(310, 100));
		setMinimumSize(new Dimension(310, 100));
		setMaximumSize(new Dimension(310, 100));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setText("Image properties:");
		add(titlePanel);
		
		drawableEditPanel = new DrawableEditPanel();
		drawableEditPanel.setPropertyChangedListener(this);
		drawableEditPanel.setPropertyName("Drawable:");
		drawableEditPanel.setUiPropertyName("drawable");
		add(drawableEditPanel);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		add(verticalStrut);
		
		alignEditPanel = new ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setPropertyName("Image align:");
		alignEditPanel.setUiPropertyName("align");
		add(alignEditPanel);
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		add(verticalStrut_1);
		
		scalingEditPanel = new ScalingEditPanel();
		scalingEditPanel.setPropertyChangedListener(this);
		scalingEditPanel.setUiPropertyName("scaling");
		scalingEditPanel.setPropertyName("Scaling:");
		add(scalingEditPanel);
	}

	@Override
	public void setConfig(UiConfig config) {
		drawableEditPanel.setConfig(config);
		scalingEditPanel.setConfig(config);
		alignEditPanel.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
