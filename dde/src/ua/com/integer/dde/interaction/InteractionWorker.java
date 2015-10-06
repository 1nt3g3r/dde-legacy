package ua.com.integer.dde.interaction;

import com.badlogic.gdx.utils.ObjectMap;

public class InteractionWorker implements InteractionInterface {
	private ObjectMap<String, InteractionHandler> handlers = new ObjectMap<String, InteractionHandler>();
	
	private ObjectMap<String, Object> params;
	
	public void addHandler(String command, InteractionHandler handler) {
		handlers.put(command, handler);
	}
	
	public void sendMessage(String message, ObjectMap<String, Object> params) {
		this.params = params;
		
		InteractionHandler handler = handlers.get(message);
		if (handler != null) {
			handler.handle(this);
		}
	}
	
	public boolean exists(String name) {
		return params.containsKey(name);
	}
	
	public Object getObject(String name) {
		if (params.containsKey(name)) {
			return params.get(name);
		} else {
			return null;
		}
	}
	
	public String getString(String name) {
		Object obj = getObject(name);
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}
	
	public int getInt(String name) {
		String strValue = getString(name);
		if (strValue != null) {
			return Integer.parseInt(strValue);
		} else {
			return -1;
		}
	}
	
	public boolean getBoolean(String name) {
		String strValue = getString(name);
		if (strValue != null) {
			return Boolean.parseBoolean(strValue);
		} else {
			return false;
		}
	}
	
	public float getFloat(String name) {
		String strValue = getString(name);
		if (strValue != null) {
			return Float.parseFloat(strValue);
		} else {
			return -1f;
		}
	}
}
