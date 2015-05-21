package ua.com.integer.dde.extension.ui.editor.property.imp.textlabel;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.ExpandEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue.FloatEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.font.FontNameEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.LocalizedStringEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel;
import ua.com.integer.dde.extension.ui.property.util.font.FontUtils;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class TextLabelPropertyEditor extends ExpandEditPanel {
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
		setTitle("Text label properties");
		
		textPanel = new LocalizedStringEditPanel();
		textPanel.setPropertyChangedListener(this);
		textPanel.setUiPropertyName("text");
		textPanel.setPropertyName("Text:");
		
		wrapTextPanel = new BooleanEditPanel();
		wrapTextPanel.setPropertyChangedListener(this);
		wrapTextPanel.setUiPropertyName("text-wrap");
		wrapTextPanel.setPropertyName("Wrap text:");
		
		fontNameEditPanel = new FontNameEditPanel();
		fontNameEditPanel.setPropertyChangedListener(this);
		fontNameEditPanel.setUiPropertyName("font-name");
		fontNameEditPanel.setPropertyName("Font name:");
		
		fontSizePanel = new SizeEditPanel();
		fontSizePanel.setAllowedSizeTypes(SizeType.SCREEN_WIDTH, SizeType.SCREEN_HEIGHT, SizeType.ABSOLUTE);
		fontSizePanel.setDefaultSize(FontUtils.getDefaultFontSize());
		fontSizePanel.setPropertyChangedListener(this);
		fontSizePanel.setUiPropertyName("font-name-size");
		fontSizePanel.setPropertyName("Font size:");
	
		fontColorPanel = new ColorEditPanel();
		fontColorPanel.setPropertyChangedListener(this);
		fontColorPanel.setUiPropertyName("text-color");
		fontColorPanel.setPropertyName("Font color:");
		
		alignEditPanel = new AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setUiPropertyName("text-align");
		alignEditPanel.setPropertyName("Text align:");
		
		fontScaleX = new FloatEditPanel();
		fontScaleX.setUiPropertyName("font-scale-x");
		fontScaleX.setDefaultValue("1");
		fontScaleX.setPropertyChangedListener(this);
		fontScaleX.setPropertyName("Font scale X:");
		
		fontScaleY = new FloatEditPanel();
		fontScaleY.setUiPropertyName("font-scale-y");
		fontScaleY.setDefaultValue("1");
		fontScaleY.setPropertyChangedListener(this);
		fontScaleY.setPropertyName("Font scale Y:");
		
		backgroundDrawable = new DrawableEditPanel();
		backgroundDrawable.setPropertyChangedListener(this);
		backgroundDrawable.setUiPropertyName("background");
		backgroundDrawable.setPropertyName("Background:");
		
		setContent(
				header("Text"),
					textPanel,
					wrapTextPanel,
					alignEditPanel,
				header("Font"),
					fontNameEditPanel,
					fontSizePanel,
					fontColorPanel,
					fontScaleX,
					fontScaleY,
				header("Background"),
					backgroundDrawable
				);
	}
}
