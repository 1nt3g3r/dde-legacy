package ua.com.integer.dde.extension.ui.editor.command.imp;

import java.awt.Toolkit;

import ua.com.integer.dde.extension.ui.editor.command.CommandHandler;
import ua.com.integer.dde.extension.ui.editor.main.UiEditorDialog;
import ua.com.integer.dde.startpanel.FrameTools;

public class ResolutionCommandHandler implements CommandHandler {
	private int width, height;
	private String multiplier;
	private UiEditorDialog screen;
	
	@Override
	public void executeCommand(String command, String[] arguments, UiEditorDialog screen) {
		if (arguments.length < 2) {
			screen.setConsoleOutput("Error in syntax. Help: " + help());
			return;
		}
		
		this.screen = screen;
		try {
			width = Integer.parseInt(arguments[0]);
			height = Integer.parseInt(arguments[1]);
		} catch(Exception e) {
			screen.setConsoleOutput("Error in syntax. Help: " + help());
			return;
		}
		if(arguments.length > 2) {
			multiplier = arguments[2];
		} else {
			multiplier = "precise";
		}
		
		if (multiplier.equals("half") || multiplier.equals("h")) {
			width /= 2;
			height /= 2;
		} else if (multiplier.equals("maximize") || multiplier.equals("max") || multiplier.equals("m")) {
			float aspectRatio = (float) width / (float) height;

			width = getMaxEditorViewportWidth();
			height = getMaxEditorViewportHeight();
			
			float newHeight = (float) width / aspectRatio;
			if (newHeight > height) {
				float newWidth = (float) height * aspectRatio;
				width = (int) newWidth;
			} else {
				height = (int) newHeight;
			}
		} else if (multiplier.equals("fullscreen")) {
			width = getMaxEditorViewportWidth();
			height = getMaxEditorViewportHeight();
		}
		
		setEditorViewportSize(width, height);
	}
		
	private int getMaxEditorViewportWidth() {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int usedWidth = screen.tabs.getWidth() + screen.actorHierarchy.getWidth() + getBorderWidth();
		return screenWidth - usedWidth;
	}

	private int getMaxEditorViewportHeight() {
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int additionalPanelHeight = 0;
		int usedHeight = screen.screenOptionsPanel.getHeight() + getBorderHeight() + additionalPanelHeight;
		return screenHeight - usedHeight;
	}

	private int getBorderWidth() {
		return screen.getWidth() - screen.tabs.getWidth() - screen.screenPanel.getWidth() - screen.actorHierarchy.getWidth();
	}

	private int getBorderHeight() {
		return screen.getHeight() - screen.screenPanel.getHeight() - screen.screenOptionsPanel.getHeight();
	}

	public void setEditorViewportSize(int width, int height) {
		int wholeWindowWidth = screen.getWidth();
		int wholeWindowHeight = screen.getHeight();

		int tabWidth = screen.tabs.getWidth();

		int hierarchyWidth = screen.actorHierarchy.getWidth();

		int editorWidth = screen.screenPanel.getWidth();
		int editorHeight = screen.screenPanel.getHeight();

		int controlPanelHeight = screen.screenOptionsPanel.getHeight();

		int borderWidth = wholeWindowWidth - tabWidth - hierarchyWidth
				- editorWidth;
		int borderHeight = wholeWindowHeight - editorHeight
				- controlPanelHeight;

		int newWindowWidth = width + borderWidth + tabWidth + hierarchyWidth;
		int newWindowHeight = height + borderHeight + controlPanelHeight;
		screen.setSize(newWindowWidth, newWindowHeight);
		screen.setConsoleOutput("Set new resolution: " + width + "x" + height);
		FrameTools.situateOnCenter(screen);
	}

	@Override
	public String help() {
		return    "<res> or <resolution>\n"
				+ "Changes viewport resolution. Syntax: res width height multiplier\n" +
				"If you want to maximize editor, type <maximize>";
	}
}
