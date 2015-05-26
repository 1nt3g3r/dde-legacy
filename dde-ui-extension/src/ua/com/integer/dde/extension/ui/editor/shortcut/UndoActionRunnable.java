package ua.com.integer.dde.extension.ui.editor.shortcut;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;

public class UndoActionRunnable implements Runnable {
	@Override
	public void run() {
		EditorKernel.editorScreen().undoAction();
	}
}
