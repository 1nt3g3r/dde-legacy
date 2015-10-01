package ua.com.integer.dde.util;

import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	private Preferences prefs;
	
	public Settings() {
		String prefsName = AbstractScreen.getKernel().getClass().getName();
		prefs = Gdx.app.getPreferences(prefsName);
		
		System.out.println(prefsName);
	}
	
	public Settings saveInt(String name, int value) {
		prefs.putInteger(name, value);
		return this;
	}
	
	public int getInt(String name) {
		return prefs.getInteger(name);
	}
	
	public Settings saveString(String name, String value) {
		prefs.putString(name, value);
		return this;
	}
	
	public String getString(String name) {
		return prefs.getString(name);
	}
	
	public Settings saveFloat(String name, float value) {
		prefs.putFloat(name, value);
		return this;
	}
	
	public float getFloat(String name) {
		return prefs.getFloat(name);
	}
	
	public Settings saveLong(String name, long value) {
		prefs.putLong(name, value);
		return this;
	}
	
	public long getLong(String name) {
		return prefs.getLong(name);
	}
	
	public Settings saveBoolean(String name, boolean value) {
		prefs.putBoolean(name, value);
		return this;
	}
	
	public boolean getBoolean(String name) {
		return prefs.getBoolean(name);
	}
	
	public Settings saveJsonObject(String name, Object value) {
		saveString(name, JsonWorker.JSON.toJson(value));
		return this;
	}
	
	public <T extends Object> T getJsonObject(String name, Class<T> cl) {
		String json = getString(name);
		if (json == null || json.equals("")) {
			return null;
		}
		return JsonWorker.JSON.fromJson(cl, json);
	}
	
	public void flush() {
		prefs.flush();
	}
	
	public Preferences prefs() {
		return prefs;
	}
}
