package ua.com.integer.dde.extension.ui.editor.property.imp.touchpad;

import javax.swing.SpinnerNumberModel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.ExpandableConfigEditor;

public class TouchpadPropertyEditor extends ExpandableConfigEditor {
	private static final long serialVersionUID = 5980989039623517405L;
	
	private DrawableEditPanel backgroundDrawable;
	private DrawableEditPanel knobDrawable;
	private IntegerEditPanel deadzoneRadius;
	
	public TouchpadPropertyEditor() {
		
		backgroundDrawable = new DrawableEditPanel();
		backgroundDrawable.setPropertyChangedListener(this);
		backgroundDrawable.setUiPropertyName("background");
		backgroundDrawable.setPropertyName("Background:");
		backgroundDrawable.setPropertyChangedListener(this);
		
		knobDrawable = new DrawableEditPanel();
		knobDrawable.setPropertyChangedListener(this);
		knobDrawable.setUiPropertyName("knob");
		knobDrawable.setPropertyName("Knob:");
		knobDrawable.setPropertyChangedListener(this);
		
		deadzoneRadius = new IntegerEditPanel();
		deadzoneRadius.getValueSpinner().setModel(new SpinnerNumberModel(2, 0, 100, 1));
		deadzoneRadius.setPropertyChangedListener(this);
		deadzoneRadius.setUiPropertyName("deadzone");
		deadzoneRadius.setDefaultValue("2");
		deadzoneRadius.setPropertyName("Deadzone:");
		
		setContent(header("Background"),
					backgroundDrawable,
				header("Knob"),
					knobDrawable,
				header("Dead zone"),
					deadzoneRadius);
	}

	@Override
	public void setConfig(UiConfig config) {
		backgroundDrawable.setConfig(config);
		knobDrawable.setConfig(config);
		deadzoneRadius.setConfig(config);
	}
}
