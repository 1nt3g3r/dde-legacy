package ua.com.integer.dde.extension.ui.editor.property.imp.touchpad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SpinnerNumberModel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.TitlePanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;

public class TouchpadPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = 5980989039623517405L;
	
	private DrawableEditPanel backgroundDrawable;
	private DrawableEditPanel knobDrawable;
	private IntegerEditPanel deadzoneRadius;
	
	public TouchpadPropertyEditor() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(310, 100));
		setMinimumSize(new Dimension(310, 100));
		setMaximumSize(new Dimension(310, 100));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setText("Touchpad properties:");
		add(titlePanel);
		
		backgroundDrawable = new DrawableEditPanel();
		backgroundDrawable.setPropertyChangedListener(this);
		backgroundDrawable.setUiPropertyName("background");
		backgroundDrawable.setPropertyName("Background:");
		add(backgroundDrawable);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		knobDrawable = new DrawableEditPanel();
		knobDrawable.setPropertyChangedListener(this);
		knobDrawable.setUiPropertyName("knob");
		knobDrawable.setPropertyName("Knob:");
		add(knobDrawable);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		add(verticalStrut_1);
		
		deadzoneRadius = new IntegerEditPanel() {
			private static final long serialVersionUID = 4001769279751012417L;
			{
				valueSpinner.setModel(new SpinnerNumberModel(2, 0, 100, 1));
			}
		};
		deadzoneRadius.setPropertyChangedListener(this);
		deadzoneRadius.setUiPropertyName("deadzone");
		deadzoneRadius.setDefaultValue("2");
		deadzoneRadius.setPropertyName("Deadzone:");
		add(deadzoneRadius);
	}

	@Override
	public void setConfig(UiConfig config) {
		backgroundDrawable.setConfig(config);
		knobDrawable.setConfig(config);
		deadzoneRadius.setConfig(config);
	}

	public DrawableEditPanel getBackgroundDrawable() {
		return backgroundDrawable;
	}
	public DrawableEditPanel getKnobDrawable() {
		return knobDrawable;
	}
	public IntegerEditPanel getDeadzoneRadius() {
		return deadzoneRadius;
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
