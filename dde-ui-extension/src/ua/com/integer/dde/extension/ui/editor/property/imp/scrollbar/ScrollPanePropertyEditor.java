package ua.com.integer.dde.extension.ui.editor.property.imp.scrollbar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;

public class ScrollPanePropertyEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = -1238877391829379789L;
	private BooleanEditPanel smoothScroll;
	private BooleanEditPanel allowScrollX;
	private BooleanEditPanel allowScrollY;
	private BooleanEditPanel fadeScrollbars;
	private BooleanEditPanel topScrollbars;
	private BooleanEditPanel allowOverscrollX;
	private BooleanEditPanel allowOverscrollY;
	
	public ScrollPanePropertyEditor() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(310, 180));
		setMinimumSize(new Dimension(310, 180));
		setMaximumSize(new Dimension(310, 180));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.GRAY);
		titlePanel.setPreferredSize(new Dimension(300, 20));
		titlePanel.setMinimumSize(new Dimension(300, 20));
		titlePanel.setMaximumSize(new Dimension(300, 20));
		add(titlePanel);
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		titlePanel.add(horizontalStrut);
		
		JLabel lblScrollpaneProperties = new JLabel("Scrollpane properties:");
		lblScrollpaneProperties.setForeground(Color.GREEN);
		titlePanel.add(lblScrollpaneProperties);
		
		allowScrollX = new BooleanEditPanel();
		allowScrollX.setPropertyChangedListener(this);
		allowScrollX.setUiPropertyName("allow-scroll-x");
		allowScrollX.setPropertyName("Allow scroll X:");
		add(allowScrollX);
		
		allowScrollY = new BooleanEditPanel();
		allowScrollY.setPropertyChangedListener(this);
		allowScrollY.setUiPropertyName("allow-scroll-y");
		allowScrollY.setPropertyName("Allow scroll Y:");
		add(allowScrollY);
		
		allowOverscrollX = new BooleanEditPanel();
		allowOverscrollX.setPropertyChangedListener(this);
		allowOverscrollX.setUiPropertyName("allow-overscroll-x");
		allowOverscrollX.setDefaultValue("false");
		allowOverscrollX.setPropertyName("Overscroll X:");
		add(allowOverscrollX);
		
		allowOverscrollY = new BooleanEditPanel();
		allowOverscrollY.setPropertyChangedListener(this);
		allowOverscrollY.setUiPropertyName("allow-overscroll-y");
		allowOverscrollY.setDefaultValue("false");
		allowOverscrollY.setPropertyName("Overscroll Y:");
		add(allowOverscrollY);
		
		fadeScrollbars = new BooleanEditPanel();
		fadeScrollbars.setPropertyChangedListener(this);
		fadeScrollbars.setUiPropertyName("fade-scrollbars");
		fadeScrollbars.setDefaultValue("false");
		fadeScrollbars.setPropertyName("Fade scrollbars:");
		add(fadeScrollbars);
		
		topScrollbars = new BooleanEditPanel();
		topScrollbars.setPropertyChangedListener(this);
		topScrollbars.setUiPropertyName("scrollbars-on-top");
		topScrollbars.setPropertyName("Top scrollbars:");
		add(topScrollbars);
		
		smoothScroll = new BooleanEditPanel();
		smoothScroll.setPropertyChangedListener(this);
		smoothScroll.setUiPropertyName("smooth-scrolling");
		smoothScroll.setPropertyName("Smooth scroll:");
		add(smoothScroll);

	}

	@Override
	public void setConfig(UiConfig config) {
		allowScrollX.setConfig(config);
		allowScrollY.setConfig(config);
		allowOverscrollX.setConfig(config);
		allowOverscrollY.setConfig(config);
		fadeScrollbars.setConfig(config);
		smoothScroll.setConfig(config);
		topScrollbars.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}

	public BooleanEditPanel getSmoothScroll() {
		return smoothScroll;
	}
	public BooleanEditPanel getAllowScrollX() {
		return allowScrollX;
	}
	public BooleanEditPanel getAllowScrollY() {
		return allowScrollY;
	}
	public BooleanEditPanel getFadeScrollbars() {
		return fadeScrollbars;
	}
	public BooleanEditPanel getTopScrollbars() {
		return topScrollbars;
	}
	public BooleanEditPanel getAllowOverscrollX() {
		return allowOverscrollX;
	}
	public BooleanEditPanel getAllowOverscrollY() {
		return allowOverscrollY;
	}
}
