package ua.com.integer.dde.extension.ui.actor.shadowlabel;

import java.awt.Color;

import javax.swing.SpinnerNumberModel;

import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.textlabel.TextLabelPropertyEditor;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class ShadowLabelPropertyEditor extends TextLabelPropertyEditor {
	private static final long serialVersionUID = 4313828412941313458L;
	
	private SizeEditPanel offsetX, offsetY;
	private ColorEditPanel shadowColor;
	
	public ShadowLabelPropertyEditor() {
		offsetX = new SizeEditPanel();
		offsetX.setDefaultSize(getPxSize(4));
		offsetX.setPropertyChangedListener(this);
		offsetX.setPropertyName("Offset X");
		offsetX.setUiPropertyName("shadow-offset-x");
		offsetX.getMultSpinner().setModel(new SpinnerNumberModel(new Float(1), null, null, new Float(1f)));

		offsetY = new SizeEditPanel();
		offsetY.setPropertyChangedListener(this);
		offsetY.setPropertyName("Offset Y");
		offsetY.setUiPropertyName("shadow-offset-y");
		offsetY.setDefaultSize(getPxSize(-4));
		offsetY.getMultSpinner().setModel(new SpinnerNumberModel(new Float(1), null, null, new Float(1f)));
		
		shadowColor = new ColorEditPanel();
		shadowColor.setPropertyChangedListener(this);
		shadowColor.setPropertyName("Shadow color");
		shadowColor.setUiPropertyName("shadow-color");
		shadowColor.setDefaultColor(Color.BLACK);
		
		addContent(
				subHeader("Shadow offset"),
					offsetX, 
					offsetY,
				subHeader("Shadow color"),
					shadowColor
				);
	}
	
	private Size getPxSize(int size) {
		Size result = new Size();
		result.setSizeValue(size);
		result.setType(SizeType.ABSOLUTE);
		return result;
	}
}
