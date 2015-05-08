package ua.com.integer.dde.startpanel;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Settings {
	private Preferences prefs;
	
	private static Settings instance = new Settings();
	private Class<? extends Object> cl;
	
	public void setSettingsClass(Class<? extends Object> cl) {
		this.cl = cl;
		prefs = Preferences.userNodeForPackage(cl);
	}
	
	public static Settings getInstance() {
		if (instance.prefs == null) {
			instance.setSettingsClass(Settings.class);
		}
		return instance;
	}

	public void setScreenResolution(int width, int height) {
		prefs.putInt("screen-width", width);
		prefs.putInt("screen-height", height);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}
	
	public void setScreenOrientaion(boolean landscape) {
		prefs.putBoolean("screen-orientation", landscape);
	}
	
	public boolean isLandscapeOrientation() {
		return prefs.getBoolean("screen-orientation", true);
	}
	
	public int getScreenWidth() {
		return prefs.getInt("screen-width", 800);
	}
	
	public int getScreenHeight() {
		return prefs.getInt("screen-height", 480);
	}
	
	public void setNeedScale(boolean needScale) {
		prefs.putBoolean("need-scale", needScale);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}
	
	public boolean isNeedScale() {
		return prefs.getBoolean("need-scale", false);
	}
	
	public void setNeedFullScreen(boolean fullscreen) {
		prefs.putBoolean("fullscreen", fullscreen);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}
	
	public boolean isNeedFullScreen() {
		return prefs.getBoolean("fullscreen", false);
	}
	
	public void setScale(String scale) {
		prefs.put("scale-coeff", scale);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}
	
	public String getScale() {
		return prefs.get("scale-coeff", "1");
	}

	public String getLastCustomImageDirectory() {
		return prefs.get("last-custom-image-directory", "./");
	}
	
	public void setLastCustomImageDirectory(String directory) {
		prefs.put("last-custom-image-directory", directory);
	}
	
	public void putString(String key, String value) {
		prefs.put(key, value);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}
	
	public String getString(String key, String defValue) {
		return prefs.get(key, defValue);
	}
	
	public void putBoolean(String key, boolean value) {
		prefs.putBoolean(key, value);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
	}
	
	public boolean getBoolean(String key, boolean defValue) {
		return prefs.getBoolean(key, defValue);
	}
	
	public void putFloat(String key, float value) {
		prefs.putFloat(key, value);
		flush();
	}
	
	public float getFloat(String key, float value) {
		return prefs.getFloat(key, value);
	}
	
	private void flush() {
		try {
			prefs.flush();
		} catch(Exception e) {
		}
	}
	
	public Class<? extends Object> getSettingsClass() {
		return cl;
	}
}
