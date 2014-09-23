package ua.com.integer.dde.extension.localize.editor.dialog;

import javax.swing.JOptionPane;

public class LocalizeDialog {
	public static String showSelectTagDialog(String tag, String language, LocalizeSelectListener listener) {
		SelectTagPanel panel = new SelectTagPanel();
		if (tag != null) panel.setTag(tag);
		if (language != null) panel.setLanguage(language);
		if (listener != null) panel.setLocalizeSelectedListener(listener);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Select tag", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) return panel.getSelectedTag();
		
		return tag;
	}
	
	public static String showSelectTagDialog(String tag, LocalizeSelectListener listener) {
		return showSelectTagDialog(tag, null, listener);
	}
	
	public static String showSelectTagDialog(LocalizeSelectListener listener) {
		return showSelectTagDialog(null, listener);
	}
	
	public static String showSelectTagDialog() {
		return showSelectTagDialog(null);
	}
	
	public static void main(String[] args) {
		showSelectTagDialog();
	}
}
