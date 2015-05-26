package ua.com.integer.dde.extension.ui.editor.shortcut;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;

public class RenameRunnable implements Runnable {
	@Override
	public void run() {
		if (EditorKernel.editorScreen().getSelectedConfig() == null) {
			return;
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String previousName = EditorKernel.editorScreen().getSelectedConfig().name;
				String newName = JOptionPane.showInputDialog("Enter new name", previousName);

				if (newName != null) {
					EditorKernel.editorScreen().getSelectedConfig().name = newName;
					EditorKernel.editorScreen().updateConfig();
					EditorKernel.getInstance().getMainWindow().updatePropertyPanelForSelectedActor();
				}
			}
		});
	}
}
