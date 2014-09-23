package ua.com.integer.dde.util;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Profiler {
	private static long time;
	private static Preferences prefs = Preferences.userNodeForPackage(Profiler.class);
	
	public static void start() {
		time = System.currentTimeMillis();
	}
	
	public static void finish() {
		long endTime = System.currentTimeMillis();
		
		long executionTimeInMillis = endTime - time;
		System.out.println("Execution time is " + executionTimeInMillis + " ms");
		if (getLastTime() != -1) {
			long prevTime = getLastTime();
			long delta = prevTime - executionTimeInMillis;
			float percents = 100.0f*delta/prevTime;
			System.out.println("Previous time is " + prevTime + " ms. You improved it by " + delta + " ms. There is " + percents + "%");;
		}
		
		prefs.putLong("last-time", executionTimeInMillis);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {}
	}
	
	private static long getLastTime() {
		return prefs.getLong("last-time", -1);
	}
	
	public static void clear() {
		prefs.putLong("last-time", -1);
		try {
			prefs.flush();
			System.out.println("Cleared!");
		} catch (BackingStoreException e) {
			System.out.println("Error during clearing!");
		}
	}
}
