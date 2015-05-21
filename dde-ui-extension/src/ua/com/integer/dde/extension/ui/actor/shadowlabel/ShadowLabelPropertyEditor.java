package ua.com.integer.dde.extension.ui.actor.shadowlabel;

import java.awt.Dimension;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.textlabel.TextLabelPropertyEditor;

public class ShadowLabelPropertyEditor extends TextLabelPropertyEditor {
	private static final long serialVersionUID = 4313828412941313458L;
	private CustomShadowPropertiesEditor customShadowProperties;
	
	private SizeEditPanel offsetX, offsetY;
	
	public ShadowLabelPropertyEditor() {
		//offsetX = new SizeEditPanel();
		//add(offsetX);
		
		customShadowProperties = new CustomShadowPropertiesEditor();
		add(customShadowProperties);

//		Dimension newSize = new Dimension(getPreferredSize());
//		newSize.height += customShadowProperties.getHeight();
//		setPreferredSize(newSize);
//		setMinimumSize(newSize);
//		setMaximumSize(newSize);
		
		//System.out.println(newSize.height);
		//setSize(newSize);
	}
	
	public void setConfig(UiConfig config) {
		super.setConfig(config);
	};
}
