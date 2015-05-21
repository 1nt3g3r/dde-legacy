package ua.com.integer.dde.extension.ui.actor.shadowlabel;

import javax.swing.JComponent;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.ExpandEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;

public class CustomShadowPropertiesEditor extends ExpandEditPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1786996880315761182L;

	private SizeEditPanel offsetX, offsetY;
	
	public CustomShadowPropertiesEditor() {
		setTitle("Shadow label properties");
		
		offsetX = new SizeEditPanel();
		offsetX.setPropertyName("Shadow offset x");
		offsetX.setUiPropertyName("shadow-offset-x");
		
		offsetY = new SizeEditPanel();
		offsetY.setPropertyName("Shadow offset y");
		offsetY.setUiPropertyName("shadow-offset-y");
		
		JComponent[] components = new JComponent[] {
			offsetX,
			offsetY
		};
		
		setContent(components);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
	
	@Override
	public void setConfig(UiConfig config) {
		offsetX.setConfig(config);
		offsetY.setConfig(config);
	}
}
