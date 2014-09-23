package ua.com.integer.dde.extension.ui.editor.property.imp.box;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Dimension;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.box.AlignEditPanel;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;

import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class BoxPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = 8004306707819739535L;
	
	private AlignEditPanel alignEditPanel;
	private SizeEditPanel padSizePanel;

	/**
	 * Create the panel.
	 */
	public BoxPropertyEditor() {
		setPreferredSize(new Dimension(310, 65));
		setMinimumSize(new Dimension(310, 65));
		setMaximumSize(new Dimension(310, 65));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setMinimumSize(new Dimension(300, 20));
		panel.setMaximumSize(new Dimension(300, 20));
		panel.setPreferredSize(new Dimension(300, 20));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		JLabel lblBoxProperties = new JLabel("Box properties:");
		lblBoxProperties.setForeground(Color.GREEN);
		panel.add(lblBoxProperties);
		
		alignEditPanel = new AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setPropertyName("Layout:");
		alignEditPanel.setUiPropertyName("alignment");
		add(alignEditPanel);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		padSizePanel = new SizeEditPanel();
		padSizePanel.setPropertyChangedListener(this);
		Size defaultPad = new Size();
		defaultPad.setType(SizeType.ABSOLUTE);
		defaultPad.setSizeValue(5);
		padSizePanel.setDefaultSize(defaultPad);
		padSizePanel.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		padSizePanel.setUiPropertyName("box-pad");
		padSizePanel.setPropertyName("Pad:");
		add(padSizePanel);

	}

	@Override
	public void setConfig(UiConfig config) {
		alignEditPanel.setConfig(config);
		padSizePanel.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
	public SizeEditPanel getPadSizePanel() {
		return padSizePanel;
	}
}
