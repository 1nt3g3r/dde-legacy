package ua.com.integer.dde.extension.ui.editor.drag;

import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.startpanel.Settings;

public class GridSettings {
	private Settings sets;
	
	private static GridSettings instance = new GridSettings();
	
	private GridSettings() {
		sets = new Settings();
		sets.setSettingsClass(UiEditorScreen.class);
	}
	
	public static GridSettings getInstance() {
		return instance;
	}
	
	public boolean needSnapToGrid() {
		return sets.getBoolean("need-snap-to-grid", true);
	}
	
	public boolean needShowGrid() {
		return sets.getBoolean("need-show-grid", true);
	}
	
	public void setNeedSnapToGrid(boolean needSnap) {
		sets.putBoolean("need-snap-to-grid", needSnap);
	}
	
	public void setNeedShowGrid(boolean needShow) {
		sets.putBoolean("need-show-grid", needShow);
	}
	
	public float getGridPercentX() {
		return sets.getFloat("grid-percent-x", 0.1f);
	}
	
	public float getGridPercentY() {
		return sets.getFloat("grid-percent-y", 0.1f);
	}
	
	public void setGridPercentX(float value) {
		sets.putFloat("grid-percent-x", value);
	}
	
	public void setGridPercentY(float value) {
		sets.putFloat("grid-percent-y", value);
	}
	
	public void setNeedMakeBothDirectionsTheSame(boolean need) {
		sets.putBoolean("make-both-directions-the-same", need);
	}
	
	public boolean isNeedMakeBothDirectionsTheSame() {
		return sets.getBoolean("make-both-directions-the-same", true);
	}
}
