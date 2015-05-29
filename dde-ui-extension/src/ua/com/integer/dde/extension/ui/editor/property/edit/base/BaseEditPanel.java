package ua.com.integer.dde.extension.ui.editor.property.edit.base;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;

public class BaseEditPanel extends JPanel implements PropertyEditComponent {
	public static final int ITEM_WIDTH = 300;
	public static final int ITEM_HEIGHT = 20;

	private static final long serialVersionUID = 4987981822583953069L;
	

	protected UiConfig config;
	protected String uiPropertyName;
	protected String defaultValue;
	protected PropertyChangeListener listener;
	protected String propertyName;
	
	public BaseEditPanel() {
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		setupSize();
	}
	
	protected void setupSize() {
		setSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
		setMinimumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
		setMaximumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
		setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
	}
	
	protected void setSizeForComponent(JComponent component, int width, int height) {
		Dimension size = new Dimension(width, height);
		component.setMinimumSize(size);
		component.setMaximumSize(size);
		component.setPreferredSize(size);
		component.setSize(size);
	}

	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		updateUIFromConfig();
	}
	
	protected void updateUIFromConfig() {
		
	}

	@Override
	public void setUiPropertyName(String propertyName) {
		this.uiPropertyName = propertyName;
		updateUIFromConfig();
	}

	public String getUiPropertyName() {
		return uiPropertyName;
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
