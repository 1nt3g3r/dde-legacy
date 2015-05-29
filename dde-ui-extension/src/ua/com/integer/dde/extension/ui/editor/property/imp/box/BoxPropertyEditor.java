package ua.com.integer.dde.extension.ui.editor.property.imp.box;

import ua.com.integer.dde.extension.ui.editor.property.edit.box.AlignEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.ExpandableConfigEditor;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class BoxPropertyEditor extends ExpandableConfigEditor {
	private static final long serialVersionUID = 8004306707819739535L;
	
	private AlignEditPanel alignEditPanel;
	private SizeEditPanel padSizePanel;

	/**
	 * Create the panel.
	 */
	public BoxPropertyEditor() {
		setTitle("Box properties");
		
		alignEditPanel = new AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setPropertyName("Layout:");
		alignEditPanel.setUiPropertyName("alignment");
		
		padSizePanel = new SizeEditPanel();
		padSizePanel.setPropertyChangedListener(this);
		
		Size defaultPad = new Size();
		defaultPad.setType(SizeType.ABSOLUTE);
		defaultPad.setSizeValue(5);
		padSizePanel.setDefaultSize(defaultPad);
		padSizePanel.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		padSizePanel.setUiPropertyName("box-pad");
		padSizePanel.setPropertyName("Pad:");
		
		setContent(
				header("How to layout widgets"),
					alignEditPanel,
				header("Distance between widgets"),
					padSizePanel);

	}
}
