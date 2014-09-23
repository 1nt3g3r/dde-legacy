package ua.com.integer.dde.ui.distance;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Distance {
	private float value;
	private DistanceType type;
	
	public Distance(DistanceType type, float value) {
		this.type = type;
		this.value = value;
	}
	
	
	public float getValue(Actor actor) {
		if (type == DistanceType.PERCENT_PARENT_HEIGHT || type == DistanceType.PERCENT_PARENT_WIDTH) {
			return type.getDistance(value, actor);
		} else {
			return getValue();
		}
	}
	
	public float getValue() {
		return type.getDistance(value);
	}
	
	public static Distance createDistance(DistanceType type, float value) {
		return new Distance(type, value);
	}
}
