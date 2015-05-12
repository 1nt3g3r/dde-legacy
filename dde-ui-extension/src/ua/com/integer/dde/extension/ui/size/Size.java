package ua.com.integer.dde.extension.ui.size;

import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Size {
	private float sizeValue = 0f;
	private SizeType sizeType = SizeType.PARENT_WIDTH;
	
	public void setSizeValue(float sizeValue) {
		this.sizeValue = sizeValue;
	}
	
	public void addSizeValue(float addValue) {
		setSizeValue(getSizeValue() + addValue);
	}
	
	public void setType(SizeType sizeType) {
		this.sizeType = sizeType;
	}
	
	public float getValue() {
		return sizeValue * sizeType.getValue();
	}
	
	public float getValue(Actor actor) {
		return sizeValue * sizeType.getValue(actor);
	}
	
	public SizeType getSizeType() {
		return sizeType;
	}
	
	public float getSizeValue() {
		return sizeValue;
	}
	
	public String toString() {
		return JsonWorker.JSON.toJson(this);
	}
	
	public static Size fromString(String json) {
		return JsonWorker.JSON.fromJson(Size.class, json);
	}
	
	public static Size create(float value, SizeType type) {
		Size result = new Size();
		result.sizeType = type;
		result.sizeValue = value;
		return result;
	}
	
	public boolean needParentActor() {
		return sizeType == SizeType.PARENT_WIDTH || sizeType == SizeType.PARENT_HEIGHT;
	}
	
	public void loadFromValue(Actor actor, float value) {
		sizeValue = value / sizeType.getValue(actor);
	}
}
