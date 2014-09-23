package ua.com.integer.dde.extension.ui.editor.property.imp.textlabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.font.FontNameEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.LocalizedStringEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.SizeType;
import ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue.FloatEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;

public class TextLabelPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = -4029545087867195370L;
	private SizeEditPanel fontSizePanel;
	private FontNameEditPanel fontNameEditPanel;
	private ColorEditPanel fontColorPanel;
	private BooleanEditPanel wrapTextPanel;
	private LocalizedStringEditPanel textPanel;
	private AlignEditPanel alignEditPanel;
	private FloatEditPanel fontScaleX;
	private FloatEditPanel fontScaleY;
	private DrawableEditPanel backgroundDrawable;

	/**
	 * Create the panel.
	 */
	public TextLabelPropertyEditor() {
		setPreferredSize(new Dimension(310, 270));
		setMinimumSize(new Dimension(310, 270));
		setMaximumSize(new Dimension(310, 270));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.GRAY);
		titlePanel.setMaximumSize(new Dimension(310, 20));
		titlePanel.setMinimumSize(new Dimension(310, 20));
		titlePanel.setPreferredSize(new Dimension(310, 20));
		add(titlePanel);
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		titlePanel.add(horizontalStrut);
		
		JLabel lblTextLabelProperties = new JLabel("Text label properties:");
		lblTextLabelProperties.setForeground(Color.GREEN);
		lblTextLabelProperties.setFont(new Font("Dialog", Font.BOLD, 12));
		titlePanel.add(lblTextLabelProperties);
		
		Component verticalStrut_4 = Box.createVerticalStrut(5);
		add(verticalStrut_4);
		
		textPanel = new LocalizedStringEditPanel();
		textPanel.setPropertyChangedListener(this);
		textPanel.setUiPropertyName("text");
		textPanel.setPropertyName("Text:");
		add(textPanel);
		
		wrapTextPanel = new BooleanEditPanel();
		wrapTextPanel.setPropertyChangedListener(this);
		wrapTextPanel.setUiPropertyName("text-wrap");
		wrapTextPanel.setPropertyName("Wrap text:");
		add(wrapTextPanel);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		add(verticalStrut);
		
		fontNameEditPanel = new FontNameEditPanel();
		fontNameEditPanel.setPropertyChangedListener(this);
		fontNameEditPanel.setUiPropertyName("font-name");
		fontNameEditPanel.setPropertyName("Font name:");
		add(fontNameEditPanel);
		
		Component verticalStrut_1 = Box.createVerticalStrut(2);
		add(verticalStrut_1);
		
		fontSizePanel = new SizeEditPanel();
		fontSizePanel.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		fontSizePanel.setDefaultSize(FontUtils.getDefaultFontSize());
		fontSizePanel.setPropertyChangedListener(this);
		fontSizePanel.setUiPropertyName("font-name-size");
		fontSizePanel.setPropertyName("Font size:");
		add(fontSizePanel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(2);
		add(verticalStrut_2);
		
		fontColorPanel = new ColorEditPanel();
		fontColorPanel.setPropertyChangedListener(this);
		fontColorPanel.setUiPropertyName("text-color");
		fontColorPanel.setPropertyName("Font color:");
		add(fontColorPanel);
		
		Component verticalStrut_3 = Box.createVerticalStrut(5);
		add(verticalStrut_3);
		
		alignEditPanel = new AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setUiPropertyName("text-align");
		alignEditPanel.setPropertyName("Text align:");
		add(alignEditPanel);
		
		JLabel lblFontScaling = new JLabel("Font scaling:");
		lblFontScaling.setForeground(Color.GREEN);
		add(lblFontScaling);
		
		fontScaleX = new FloatEditPanel();
		fontScaleX.setUiPropertyName("font-scale-x");
		fontScaleX.setDefaultValue("1");
		fontScaleX.setPropertyChangedListener(this);
		fontScaleX.setPropertyName("Font scale X:");
		add(fontScaleX);
		
		Component verticalStrut_5 = Box.createVerticalStrut(2);
		add(verticalStrut_5);
		
		fontScaleY = new FloatEditPanel();
		fontScaleY.setUiPropertyName("font-scale-y");
		fontScaleY.setDefaultValue("1");
		fontScaleY.setPropertyChangedListener(this);
		fontScaleY.setPropertyName("Font scale Y:");
		add(fontScaleY);
		
		JLabel lblDrawable = new JLabel("Drawable:");
		lblDrawable.setForeground(Color.GREEN);
		add(lblDrawable);
		
		backgroundDrawable = new DrawableEditPanel();
		backgroundDrawable.setPropertyChangedListener(this);
		backgroundDrawable.setUiPropertyName("background");
		backgroundDrawable.setPropertyName("Background:");
		add(backgroundDrawable);
	}

	@Override
	public void setConfig(UiConfig config) {
		textPanel.setConfig(config);
		wrapTextPanel.setConfig(config);
		fontNameEditPanel.setConfig(config);
		fontSizePanel.setConfig(config);
		fontColorPanel.setConfig(config);
		alignEditPanel.setConfig(config);
		fontScaleX.setConfig(config);
		fontScaleY.setConfig(config);
		backgroundDrawable.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
	public FloatEditPanel getFontScaleX() {
		return fontScaleX;
	}
	public FloatEditPanel getFontScaleY() {
		return fontScaleY;
	}
	public DrawableEditPanel getBackgroundDrawable() {
		return backgroundDrawable;
	}
}
