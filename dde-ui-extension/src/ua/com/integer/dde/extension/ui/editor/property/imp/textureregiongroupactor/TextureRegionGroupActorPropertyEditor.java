package ua.com.integer.dde.extension.ui.editor.property.imp.textureregiongroupactor;

import java.awt.Color;

import javax.swing.JLabel;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.ExpandEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.textureregion.TextureRegionEditPanel;

public class TextureRegionGroupActorPropertyEditor extends ExpandEditPanel {
	private static final long serialVersionUID = -974408993230468701L;
	private TextureRegionEditPanel textureRegionEditPanel;

	public TextureRegionGroupActorPropertyEditor() {
		setTitle("Texture region group properties");
		
		JLabel lblTextureregiongroupProperties = new JLabel("TextureRegionGroup properties");
		lblTextureregiongroupProperties.setForeground(Color.GREEN);
		
		textureRegionEditPanel = new TextureRegionEditPanel();
		textureRegionEditPanel.setPropertyChangedListener(this);
		textureRegionEditPanel.setUiPropertyName("image");
		textureRegionEditPanel.setPropertyName("Image:");
		setContent(textureRegionEditPanel);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
