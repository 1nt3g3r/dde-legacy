package ua.com.integer.dde.extension.ui.editor.property.imp.slidecontrol;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.TitlePanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class PageControlPropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = 8791399763793327280L;
	private SizeEditPanel pageSize;
	private BooleanEditPanel orientation;
	
	public PageControlPropertyEditor() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(310, 100));
		setMinimumSize(new Dimension(310, 100));
		setMaximumSize(new Dimension(310, 100));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setText("Page scroll properties:");
		add(titlePanel);
		
		pageSize = new SizeEditPanel();
		pageSize.setUiPropertyName("page-size");
		pageSize.setDefaultSize(Size.create(1f, SizeType.PARENT_WIDTH));
		pageSize.setPropertyName("Page size");
		add(pageSize);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut);
		
		orientation = new BooleanEditPanel();
		orientation.setDefaultValue("false");
		orientation.setUiPropertyName("vertical");
		orientation.setPropertyName("Vertical");
		add(orientation);
	}
	
	@Override
	public void setConfig(UiConfig config) {
		orientation.setConfig(config);
		pageSize.setConfig(config);
	}
	
	public BooleanEditPanel getOrientation() {
		return orientation;
	}
	
	public SizeEditPanel getPageSize() {
		return pageSize;
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
