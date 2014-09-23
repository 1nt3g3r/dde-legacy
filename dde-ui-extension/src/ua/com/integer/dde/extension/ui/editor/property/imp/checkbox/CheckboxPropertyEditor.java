package ua.com.integer.dde.extension.ui.editor.property.imp.checkbox;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.textbutton.TextButtonPropertyEditor;

import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Color;

import ua.com.integer.dde.extension.ui.editor.property.edit.drawable.DrawableEditPanel;

import java.awt.Component;

import javax.swing.Box;

public class CheckboxPropertyEditor extends TextButtonPropertyEditor {
	private static final long serialVersionUID = -3818876315664034009L;
	private DrawableEditPanel checkboxOverDrawable;
	private DrawableEditPanel checkboxDisabledOffDrawable;
	private DrawableEditPanel checkboxDisabledOnDrawable;
	private DrawableEditPanel checkboxOffDrawable;
	private DrawableEditPanel checkboxOnDrawable;
	
	public CheckboxPropertyEditor() {
		setPreferredSize(new Dimension(310, 700));
		setMinimumSize(new Dimension(310, 700));
		setMaximumSize(new Dimension(310, 700));
		
		JLabel lblCheckboxProperties = new JLabel("Checkbox drawables:");
		lblCheckboxProperties.setForeground(Color.BLUE);
		add(lblCheckboxProperties);
		
		checkboxOnDrawable = new DrawableEditPanel();
		checkboxOnDrawable.setPropertyChangedListener(this);
		checkboxOnDrawable.setUiPropertyName("checkbox-on-drawable");
		checkboxOnDrawable.setPropertyName("On:");
		add(checkboxOnDrawable);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		checkboxOffDrawable = new DrawableEditPanel();
		checkboxOffDrawable.setPropertyChangedListener(this);
		checkboxOffDrawable.setPropertyName("Off:");
		checkboxOffDrawable.setUiPropertyName("checkbox-off-drawable");
		add(checkboxOffDrawable);
		
		Component verticalStrut_1 = Box.createVerticalStrut(2);
		add(verticalStrut_1);
		
		checkboxOverDrawable = new DrawableEditPanel();
		checkboxOverDrawable.setUiPropertyName("checkbox-over-drawable");
		checkboxOverDrawable.setPropertyChangedListener(this);
		checkboxOverDrawable.setPropertyName("Over:");
		add(checkboxOverDrawable);
		
		Component verticalStrut_2 = Box.createVerticalStrut(2);
		add(verticalStrut_2);
		
		checkboxDisabledOnDrawable = new DrawableEditPanel();
		checkboxDisabledOnDrawable.setUiPropertyName("checkbox-disabled-on-drawable");
		checkboxDisabledOnDrawable.setPropertyChangedListener(this);
		checkboxDisabledOnDrawable.setPropertyName("Disabled on:");
		add(checkboxDisabledOnDrawable);
		
		Component verticalStrut_3 = Box.createVerticalStrut(2);
		add(verticalStrut_3);
		
		checkboxDisabledOffDrawable = new DrawableEditPanel();
		checkboxDisabledOffDrawable.setUiPropertyName("checkbox-disabled-off-drawable");
		checkboxDisabledOffDrawable.setPropertyChangedListener(this);
		checkboxDisabledOffDrawable.setPropertyName("Disabled off:");
		add(checkboxDisabledOffDrawable);
	}
	
	@Override
	public void setConfig(UiConfig config) {
		super.setConfig(config);
		
		checkboxOnDrawable.setConfig(config);
		checkboxOffDrawable.setConfig(config);
		checkboxDisabledOffDrawable.setConfig(config);
		checkboxDisabledOnDrawable.setConfig(config);
		checkboxOverDrawable.setConfig(config);
	}
}
