package ua.com.integer.dde.extension.ui.editor.property.imp.textureregiongroupactor;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.textureregion.TextureRegionEditPanel;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Color;
import java.awt.Dimension;

public class TextureRegionGroupActorPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = -974408993230468701L;
	private TextureRegionEditPanel textureRegionEditPanel;

	public TextureRegionGroupActorPropertyEditor() {
		setPreferredSize(new Dimension(310, 45));
		setMinimumSize(new Dimension(310, 50));
		setMaximumSize(new Dimension(310, 50));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(Color.GRAY);
		labelPanel.setPreferredSize(new Dimension(300, 20));
		labelPanel.setMinimumSize(new Dimension(300, 20));
		labelPanel.setMaximumSize(new Dimension(300, 20));
		add(labelPanel);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		labelPanel.add(horizontalStrut);
		
		JLabel lblTextureregiongroupProperties = new JLabel("TextureRegionGroup properties");
		lblTextureregiongroupProperties.setForeground(Color.GREEN);
		labelPanel.add(lblTextureregiongroupProperties);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		labelPanel.add(horizontalGlue);
		
		textureRegionEditPanel = new TextureRegionEditPanel();
		textureRegionEditPanel.setPropertyChangedListener(this);
		textureRegionEditPanel.setUiPropertyName("image");
		textureRegionEditPanel.setPropertyName("Texture region:");
		add(textureRegionEditPanel);
	}

	@Override
	public void setConfig(UiConfig config) {
		textureRegionEditPanel.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
