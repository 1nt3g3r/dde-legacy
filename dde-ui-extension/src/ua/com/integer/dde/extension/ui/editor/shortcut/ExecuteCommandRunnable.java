package ua.com.integer.dde.extension.ui.editor.shortcut;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;

public class ExecuteCommandRunnable implements Runnable {
	private String command;
	
	public ExecuteCommandRunnable(String command) {
		this.command = command;
	}
	
	public void run() {
		if (command != null) {
			EditorKernel.executeCommand(command);
		}
	}
}
