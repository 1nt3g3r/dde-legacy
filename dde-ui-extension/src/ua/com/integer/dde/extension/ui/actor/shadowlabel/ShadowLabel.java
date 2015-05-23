package ua.com.integer.dde.extension.ui.actor.shadowlabel;

import ua.com.integer.dde.extension.ui.actor.TextLabel;

import com.badlogic.gdx.graphics.Color;

public class ShadowLabel extends TextLabel {
	public static final String ID = "ua.com.integer.dde.ui.shadow.label";
	public static final String CATEGORY = "Custom Labels";
	public static final String DESCRIPTION = "Shadow label";
	
	private TextLabel shadowLabel = new TextLabel();
	private float offsetX = 4, offsetY = -4;
	private Color shadowColor = Color.BLACK;
	
	@Override
	public void act(float delta) {
		if (!shadowLabel.hasParent()) {
			getParent().addActorBefore(this, shadowLabel);
		}
		
		shadowLabel.setText(getText());
		shadowLabel.setSize(getWidth(), getHeight());
		shadowLabel.setStyle(getStyle());
		shadowLabel.setAlign(getAlign());
		
		shadowLabel.setColor(shadowColor);
		shadowLabel.setPosition(getX() + offsetX, getY() + offsetY);
		
		shadowLabel.act(delta);
		super.act(delta);
	}
	
	@Override
	public void setWrap(boolean wrap) {
		super.setWrap(wrap);
		
		shadowLabel.setWrap(wrap);
	}
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
	public void setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
	}
	
	public Color getShadowColor() {
		return shadowColor;
	}
	
	public TextLabel getShadowLabel() {
		return shadowLabel;
	}
}
