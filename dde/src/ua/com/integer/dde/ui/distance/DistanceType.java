package ua.com.integer.dde.ui.distance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

public enum DistanceType {
	ABSOLUTE,
	PERCENT_PARENT_WIDTH,
	PERCENT_PARENT_HEIGHT,
	PERCENT_SCREEN_WIDTH,
	PERCENT_SCREEN_HEIGHT;
	
	public float getDistance(float param) {
		switch(this) {
		case ABSOLUTE: return param;
		case PERCENT_PARENT_WIDTH: 
		case PERCENT_PARENT_HEIGHT :throw new IllegalArgumentException("You should call getDistance(float, Actor) to get distance");
		case PERCENT_SCREEN_HEIGHT: return Gdx.graphics.getHeight()*param;
		case PERCENT_SCREEN_WIDTH: return Gdx.graphics.getWidth()*param;
		default: throw new IllegalArgumentException("Undefined DistanceType!");
		}
	}
	
	public float getDistance(float param, Actor parent) {
		switch(this) {
		case PERCENT_PARENT_HEIGHT : return parent.getHeight()*param;
		case PERCENT_PARENT_WIDTH : return parent.getWidth()*param;
		default : throw new IllegalArgumentException("You can call this method only with PERCENT_PARENT_WIDTH or PERCENT_PARENT_HEIGHT values!");
		}
	}
}
