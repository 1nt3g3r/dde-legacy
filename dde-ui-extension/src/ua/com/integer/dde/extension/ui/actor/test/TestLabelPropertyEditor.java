package ua.com.integer.dde.extension.ui.actor.test;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;

public class TestLabelPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = -7142523358663070870L;
	
	private BooleanEditPanel testValue;
	
	public TestLabelPropertyEditor() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(310, 180));
		setMinimumSize(new Dimension(310, 180));
		setMaximumSize(new Dimension(310, 180));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		testValue = new BooleanEditPanel();
		testValue.setPropertyChangedListener(this);
		testValue.setUiPropertyName("test-value");
		testValue.setPropertyName("Test value");
		add(testValue);
	}

	@Override
	public void propertyChanged() {
		
	}

	@Override
	public void setConfig(UiConfig config) {
		
	}
}
