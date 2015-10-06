package ua.com.integer.dde.interaction;

import com.badlogic.gdx.utils.ObjectMap;

public interface InteractionInterface {
	public void sendMessage(String message, ObjectMap<String, Object> params);
}
