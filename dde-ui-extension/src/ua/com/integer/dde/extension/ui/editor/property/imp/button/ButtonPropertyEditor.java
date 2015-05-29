package ua.com.integer.dde.extension.ui.editor.property.imp.button;

import javax.swing.JComponent;

import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.ExpandableConfigEditor;

public class ButtonPropertyEditor extends ExpandableConfigEditor {
	private static final long serialVersionUID = 4678870679226617989L;
	private IntegerEditPanel pressedOffsetX;
	private DrawableEditPanel checkedOverDrawable;
	private IntegerEditPanel pressedOffsetY;
	private DrawableEditPanel disabledDrawable;
	private BooleanEditPanel disabledButton;
	private DrawableEditPanel upDrawable;
	private IntegerEditPanel unpressedOffsetX;
	private DrawableEditPanel downDrawable;
	private DrawableEditPanel checkedDrawable;
	private DrawableEditPanel overDrawable;
	private IntegerEditPanel unpressedOffsetY;
	
	public ButtonPropertyEditor() {
		setTitle("Button properties:");
		
		upDrawable = new DrawableEditPanel();
		upDrawable.setPropertyChangedListener(this);
		upDrawable.setUiPropertyName("up-drawable");
		upDrawable.setPropertyName("Up:");
		
		downDrawable = new DrawableEditPanel();
		downDrawable.setPropertyChangedListener(this);
		downDrawable.setPropertyName("Down:");
		downDrawable.setUiPropertyName("down-drawable");
		
		overDrawable = new DrawableEditPanel();
		overDrawable.setPropertyChangedListener(this);
		overDrawable.setUiPropertyName("over-drawable");
		overDrawable.setPropertyName("Over:");
		
		checkedDrawable = new DrawableEditPanel();
		checkedDrawable.setPropertyChangedListener(this);
		checkedDrawable.setPropertyName("Checked:");
		checkedDrawable.setUiPropertyName("checked-drawable");
		
		checkedOverDrawable = new DrawableEditPanel();
		checkedOverDrawable.setPropertyChangedListener(this);
		checkedOverDrawable.setPropertyName("Checked over:");
		checkedOverDrawable.setUiPropertyName("checked-over-drawable");
		
		disabledDrawable = new DrawableEditPanel();
		disabledDrawable.setPropertyChangedListener(this);
		disabledDrawable.setUiPropertyName("disabled-drawable");
		disabledDrawable.setPropertyName("Disabled:");
		
		pressedOffsetX = new IntegerEditPanel();
		pressedOffsetX.setPropertyChangedListener(this);
		pressedOffsetX.setUiPropertyName("pressed-offset-x");
		pressedOffsetX.setPropertyName("Offset X:");
	
		pressedOffsetY = new IntegerEditPanel();
		pressedOffsetY.setPropertyChangedListener(this);
		pressedOffsetY.setUiPropertyName("pressed-offset-y");
		pressedOffsetY.setPropertyName("Offset Y:");
		
		unpressedOffsetX = new IntegerEditPanel();
		unpressedOffsetX.setPropertyChangedListener(this);
		unpressedOffsetX.setUiPropertyName("unpressed-offset-x");
		unpressedOffsetX.setPropertyName("Offset X:");
		
		unpressedOffsetY = new IntegerEditPanel();
		unpressedOffsetY.setPropertyChangedListener(this);
		unpressedOffsetY.setUiPropertyName("unpressed-offset-y");
		unpressedOffsetY.setPropertyName("Offset Y:");
	
		disabledButton = new BooleanEditPanel();
		disabledButton.setPropertyChangedListener(this);
		disabledButton.setPropertyName("Disabled");
		disabledButton.setUiPropertyName("disabled");
		disabledButton.setDefaultValue("false");
		
		setContent(getContent());
	}
	
	public JComponent[] getContent() {
		return new JComponent[] {
				header("Drawables"),
				upDrawable,
				downDrawable,
				overDrawable,
				checkedDrawable,
				checkedOverDrawable,
				disabledDrawable,
			header("Pressed offset"),
				pressedOffsetX,
				pressedOffsetY,
			header("Unpressed offset"),
				unpressedOffsetX,
				unpressedOffsetY,
			header("Button state"),
				disabledButton
		};
	}
}
