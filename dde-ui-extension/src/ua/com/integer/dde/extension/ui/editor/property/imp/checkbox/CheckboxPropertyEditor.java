package ua.com.integer.dde.extension.ui.editor.property.imp.checkbox;

import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.textbutton.TextButtonPropertyEditor;

public class CheckboxPropertyEditor extends TextButtonPropertyEditor {
	private static final long serialVersionUID = -3818876315664034009L;
	private DrawableEditPanel checkboxOverDrawable;
	private DrawableEditPanel checkboxDisabledOffDrawable;
	private DrawableEditPanel checkboxDisabledOnDrawable;
	private DrawableEditPanel checkboxOffDrawable;
	private DrawableEditPanel checkboxOnDrawable;
	
	public CheckboxPropertyEditor() {
		setTitle("Checkbox properties");
		
		checkboxOnDrawable = new DrawableEditPanel();
		checkboxOnDrawable.setPropertyChangedListener(this);
		checkboxOnDrawable.setUiPropertyName("checkbox-on-drawable");
		checkboxOnDrawable.setPropertyName("On:");
		
		checkboxOffDrawable = new DrawableEditPanel();
		checkboxOffDrawable.setPropertyChangedListener(this);
		checkboxOffDrawable.setPropertyName("Off:");
		checkboxOffDrawable.setUiPropertyName("checkbox-off-drawable");
		
		checkboxOverDrawable = new DrawableEditPanel();
		checkboxOverDrawable.setUiPropertyName("checkbox-over-drawable");
		checkboxOverDrawable.setPropertyChangedListener(this);
		checkboxOverDrawable.setPropertyName("Over:");
		
		checkboxDisabledOnDrawable = new DrawableEditPanel();
		checkboxDisabledOnDrawable.setUiPropertyName("checkbox-disabled-on-drawable");
		checkboxDisabledOnDrawable.setPropertyChangedListener(this);
		checkboxDisabledOnDrawable.setPropertyName("Disabled on:");
		
		checkboxDisabledOffDrawable = new DrawableEditPanel();
		checkboxDisabledOffDrawable.setUiPropertyName("checkbox-disabled-off-drawable");
		checkboxDisabledOffDrawable.setPropertyChangedListener(this);
		checkboxDisabledOffDrawable.setPropertyName("Disabled off:");

		addContent(subHeader("Checkbox drawables"),
						checkboxOnDrawable,
						checkboxOffDrawable,
						checkboxOverDrawable,
						checkboxDisabledOnDrawable,
						checkboxDisabledOffDrawable);
	}
}
