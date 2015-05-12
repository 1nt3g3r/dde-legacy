package ua.com.integer.dde.extension.ui.property.imp.pagecontrol;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.slidecontrol.PageControlPropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.ui.actor.PageControl;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class PageControlPropertySupporter extends PropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new PageControlPropertyEditor();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		PageControl touchpad = (PageControl) actor;
		
		if (exists("vertical")) {
			boolean isVertical = getBoolean("vertical");
			touchpad.setVerticalOrientation(isVertical);
		}
		
		if (exists("page-size")) {
			Size pageSize = getSize("page-size");
			touchpad.setScrollSize(pageSize.getValue(actor.getParent()));
		}
	}
}
