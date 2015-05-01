package ua.com.integer.dde.extension.ui.property.imp.scrollbar;

import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.imp.scrollbar.ScrollPanePropertyEditor;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

/**
 * Класс для настройки свойств ScrollPane
 * 
 * @author 1nt3g3r
 */
public class ScrollPanelPropertySupporter extends PropertySupporter {
	@Override
	public JPanel createSetupPanel(UiConfig config, Actor actor) {
		return new ScrollPanePropertyEditor();
	}

	@Override
	public void setup(UiConfig config, Actor actor, DDKernel kernel) {
		super.setup(config, actor, kernel);
		
		ScrollPane scroll = (ScrollPane) actor;
		
		scroll.setScrollingDisabled(!getBoolean("allow-scroll-x", true), !getBoolean("allow-scroll-y", true));
		scroll.setOverscroll(getBoolean("allow-overscroll-x", false), getBoolean("allow-overscroll-y", false));
		scroll.setFadeScrollBars(getBoolean("fade-scrollbars", false));
		scroll.setScrollbarsOnTop(getBoolean("scrollbars-on-top", true));
		scroll.setSmoothScrolling(getBoolean("smooth-scrolling", true));
	}
}
