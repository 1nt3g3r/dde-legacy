package ua.com.integer.dde.extension.ui.editor.property.imp.slidecontrol;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.ExpandableConfigEditor;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

public class PageControlPropertyEditor extends ExpandableConfigEditor {
	private static final long serialVersionUID = 8791399763793327280L;
	private SizeEditPanel pageSize;
	private BooleanEditPanel orientation;
	
	public PageControlPropertyEditor() {
		pageSize = new SizeEditPanel();
		pageSize.setUiPropertyName("page-size");
		pageSize.setDefaultSize(Size.create(1f, SizeType.PARENT_WIDTH));
		pageSize.setPropertyName("Page size");
		pageSize.setPropertyChangedListener(this);
		
		orientation = new BooleanEditPanel();
		orientation.setDefaultValue("false");
		orientation.setUiPropertyName("vertical");
		orientation.setPropertyName("Vertical");
		orientation.setPropertyChangedListener(this);
		
		setContent(header("Size of scrolling"),
						pageSize,
					header("Scrolling orientation"),
						orientation);
	}
	
	@Override
	public void setConfig(UiConfig config) {
		orientation.setConfig(config);
		pageSize.setConfig(config);
	}
}
