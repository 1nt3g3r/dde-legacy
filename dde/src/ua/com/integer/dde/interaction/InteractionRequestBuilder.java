package ua.com.integer.dde.interaction;

import com.badlogic.gdx.utils.ObjectMap;

public class InteractionRequestBuilder {
	private static InteractionRequestBuilder instance = new InteractionRequestBuilder();

	private String message;
	private ObjectMap<String, Object> params = new ObjectMap<String, Object>();
	
	private InteractionRequestBuilder() {
	}
	
	public static InteractionRequestBuilder getInstance() {
		return instance.create();
	}
	
	public InteractionRequestBuilder create() {
		params.clear();
		return this;
	}
	
	public InteractionRequestBuilder message(String message) {
		this.message = message;
		return this;
	}
	
	public InteractionRequestBuilder setInt(String name, int value) {
		params.put(name, value);
		return this;
	}
	
	public InteractionRequestBuilder setString(String name, String value) {
		params.put(name, value);
		return this;
	}
	
	public InteractionRequestBuilder setObject(String name, Object value) {
		params.put(name, value);
		return this;
	}
	
	public InteractionRequestBuilder setBoolean(String name, Object value) {
		params.put(name, value);
		return this;
	}
	
	public InteractionRequestBuilder setFloat(String name, float value) {
		params.put(name, value);
		return this;
	}
	
	public void send(InteractionInterface interaction) {
		if (interaction != null) {
			interaction.sendMessage(message, params);
		}
	}
}
