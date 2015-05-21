package ua.com.integer.dde.extension.ui.editor.property.imp.image;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.ExpandEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.scaling.ScalingEditPanel;

public class ImagePropertyEditor extends ExpandEditPanel {
	private static final long serialVersionUID = 320977260364332927L;
	private DrawableEditPanel drawableEditPanel;
	private ScalingEditPanel scalingEditPanel;
	private ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel alignEditPanel;

	/**
	 * Create the panel.
	 */
	public ImagePropertyEditor() {
		setTitle("Image properties");
		
		drawableEditPanel = new DrawableEditPanel();
		drawableEditPanel.setPropertyChangedListener(this);
		drawableEditPanel.setPropertyName("Drawable:");
		drawableEditPanel.setUiPropertyName("drawable");
		
		alignEditPanel = new ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setPropertyName("Image align:");
		alignEditPanel.setUiPropertyName("align");
		
		scalingEditPanel = new ScalingEditPanel();
		scalingEditPanel.setPropertyChangedListener(this);
		scalingEditPanel.setUiPropertyName("scaling");
		scalingEditPanel.setPropertyName("Scaling:");
		
		setContent(
				header("Texture region to draw"),
					drawableEditPanel, 
				header("Align and scaling"),
					alignEditPanel, scalingEditPanel);
	}
}
