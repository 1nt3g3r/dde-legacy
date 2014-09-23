package ua.com.integer.dde.extension.ui.editor.property.imp.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.TitlePanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;

public class ButtonPropertyEditor extends ConfigEditor implements PropertyChangeListener {
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
		setPreferredSize(new Dimension(310, 400));
		setMinimumSize(new Dimension(310, 400));
		setMaximumSize(new Dimension(310, 400));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setText("Button properties:");
		add(titlePanel);
		
		JLabel lblDrawables = new JLabel("Drawables:");
		lblDrawables.setForeground(Color.GREEN);
		add(lblDrawables);
		
		upDrawable = new DrawableEditPanel();
		upDrawable.setPropertyChangedListener(this);
		upDrawable.setUiPropertyName("up-drawable");
		upDrawable.setPropertyName("Up:");
		add(upDrawable);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		downDrawable = new DrawableEditPanel();
		downDrawable.setPropertyChangedListener(this);
		downDrawable.setPropertyName("Down:");
		downDrawable.setUiPropertyName("down-drawable");
		add(downDrawable);
		
		Component verticalStrut_1 = Box.createVerticalStrut(2);
		add(verticalStrut_1);
		
		overDrawable = new DrawableEditPanel();
		overDrawable.setPropertyChangedListener(this);
		overDrawable.setUiPropertyName("over-drawable");
		overDrawable.setPropertyName("Over:");
		add(overDrawable);
		
		Component verticalStrut_2 = Box.createVerticalStrut(2);
		add(verticalStrut_2);
		
		checkedDrawable = new DrawableEditPanel();
		checkedDrawable.setPropertyChangedListener(this);
		checkedDrawable.setPropertyName("Checked:");
		checkedDrawable.setUiPropertyName("checked-drawable");
		add(checkedDrawable);
		
		Component verticalStrut_3 = Box.createVerticalStrut(2);
		add(verticalStrut_3);
		
		checkedOverDrawable = new DrawableEditPanel();
		checkedOverDrawable.setPropertyChangedListener(this);
		checkedOverDrawable.setPropertyName("Checked over:");
		checkedOverDrawable.setUiPropertyName("checked-over-drawable");
		add(checkedOverDrawable);
		
		Component verticalStrut_4 = Box.createVerticalStrut(2);
		add(verticalStrut_4);
		
		disabledDrawable = new DrawableEditPanel();
		disabledDrawable.setPropertyChangedListener(this);
		disabledDrawable.setUiPropertyName("disabled-drawable");
		disabledDrawable.setPropertyName("Disabled:");
		add(disabledDrawable);
		
		Component verticalStrut_6 = Box.createVerticalStrut(5);
		add(verticalStrut_6);
		
		JLabel lblPressedOffsets = new JLabel("Pressed offsets:");
		lblPressedOffsets.setForeground(Color.GREEN);
		add(lblPressedOffsets);
		
		pressedOffsetX = new IntegerEditPanel();
		pressedOffsetX.setPropertyChangedListener(this);
		pressedOffsetX.setUiPropertyName("pressed-offset-x");
		pressedOffsetX.setPropertyName("Offset X:");
		add(pressedOffsetX);
		
		Component verticalStrut_7 = Box.createVerticalStrut(2);
		add(verticalStrut_7);
		
		pressedOffsetY = new IntegerEditPanel();
		pressedOffsetY.setPropertyChangedListener(this);
		pressedOffsetY.setUiPropertyName("pressed-offset-y");
		pressedOffsetY.setPropertyName("Offset Y:");
		add(pressedOffsetY);
		
		Component verticalStrut_8 = Box.createVerticalStrut(5);
		add(verticalStrut_8);
		
		JLabel lblUnpressedOffsets = new JLabel("Unpressed offsets:");
		lblUnpressedOffsets.setForeground(Color.GREEN);
		add(lblUnpressedOffsets);
		
		unpressedOffsetX = new IntegerEditPanel();
		unpressedOffsetX.setPropertyChangedListener(this);
		unpressedOffsetX.setUiPropertyName("unpressed-offset-x");
		unpressedOffsetX.setPropertyName("Offset X:");
		add(unpressedOffsetX);
		
		Component verticalStrut_9 = Box.createVerticalStrut(2);
		add(verticalStrut_9);
		
		unpressedOffsetY = new IntegerEditPanel();
		unpressedOffsetY.setPropertyChangedListener(this);
		unpressedOffsetY.setUiPropertyName("unpressed-offset-y");
		unpressedOffsetY.setPropertyName("Offset Y:");
		add(unpressedOffsetY);
		
		Component verticalStrut_5 = Box.createVerticalStrut(5);
		add(verticalStrut_5);
		
		disabledButton = new BooleanEditPanel();
		disabledButton.setPropertyChangedListener(this);
		disabledButton.setPropertyName("Disabled:");
		disabledButton.setUiPropertyName("disabled");
		disabledButton.setDefaultValue("false");
		add(disabledButton);
	}
	
	@Override
	public void setConfig(UiConfig config) {
		upDrawable.setConfig(config);
		downDrawable.setConfig(config);
		checkedDrawable.setConfig(config);
		disabledDrawable.setConfig(config);
		overDrawable.setConfig(config);
		checkedOverDrawable.setConfig(config);
		
		pressedOffsetX.setConfig(config);
		pressedOffsetY.setConfig(config);
		unpressedOffsetX.setConfig(config);
		unpressedOffsetY.setConfig(config);
		
		disabledButton.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}

	public IntegerEditPanel getPressedOffsetX() {
		return pressedOffsetX;
	}
	public DrawableEditPanel getCheckedOverDrawable() {
		return checkedOverDrawable;
	}
	public IntegerEditPanel getPressedOffsetY() {
		return pressedOffsetY;
	}
	public DrawableEditPanel getDisabledDrawable() {
		return disabledDrawable;
	}
	public BooleanEditPanel getDisabledButton() {
		return disabledButton;
	}
	public DrawableEditPanel getUpDrawable() {
		return upDrawable;
	}
	public IntegerEditPanel getUnpressedOffsetX() {
		return unpressedOffsetX;
	}
	public DrawableEditPanel getDownDrawable() {
		return downDrawable;
	}
	public DrawableEditPanel getCheckedDrawable() {
		return checkedDrawable;
	}
	public DrawableEditPanel getOverDrawable() {
		return overDrawable;
	}
	public IntegerEditPanel getUnpressedOffsetY() {
		return unpressedOffsetY;
	}
}
