package ua.com.integer.dde.extension.ui.actor;

import ua.com.integer.dde.res.screen.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Текстовая метка с возможностью выравнивания
 * 
 * @author 1nt3g3r
 */
public class TextLabel extends Label {
	private Align align;
	
	public TextLabel() {
		super("Text", new LabelStyle(AbstractScreen.getKernel().getFont(Gdx.graphics.getWidth()/10), Color.WHITE));
		
		LabelStyle lStyle = new LabelStyle();
		lStyle.font = AbstractScreen.getKernel().getFont(Gdx.graphics.getWidth()/10);
		setStyle(lStyle);
		
		setAlign(Align.CENTER);
	}
	
	public Label getTextLabel() {
		return this;
	}
	
	public void setAlign(Align al) {
		this.align = al;
		
		switch(al) {
		case CENTER: 
			setAlignment(com.badlogic.gdx.utils.Align.center, com.badlogic.gdx.utils.Align.center);
			break;
		case TOP_LEFT:
			setAlignment(com.badlogic.gdx.utils.Align.left | com.badlogic.gdx.utils.Align.top, com.badlogic.gdx.utils.Align.left);
			break;
		case BOTTOM_CENTER:
			setAlignment(com.badlogic.gdx.utils.Align.center | com.badlogic.gdx.utils.Align.bottom, com.badlogic.gdx.utils.Align.center);
			break;
		case BOTTOM_LEFT:
			setAlignment(com.badlogic.gdx.utils.Align.bottom | com.badlogic.gdx.utils.Align.left, com.badlogic.gdx.utils.Align.left);
			break;
		case BOTTOM_RIGHT: 
			setAlignment(com.badlogic.gdx.utils.Align.bottom | com.badlogic.gdx.utils.Align.right, com.badlogic.gdx.utils.Align.right);
			break;
		case LEFT_CENTER: 
			setAlignment(com.badlogic.gdx.utils.Align.left | com.badlogic.gdx.utils.Align.center, com.badlogic.gdx.utils.Align.left);
			break;
		case RIGHT_CENTER: 
			setAlignment(com.badlogic.gdx.utils.Align.right | com.badlogic.gdx.utils.Align.center, com.badlogic.gdx.utils.Align.right);
			break;
		case TOP_CENTER:
			setAlignment(com.badlogic.gdx.utils.Align.center | com.badlogic.gdx.utils.Align.top, com.badlogic.gdx.utils.Align.center);
			break;
		case TOP_RIGHT: 
			setAlignment(com.badlogic.gdx.utils.Align.top | com.badlogic.gdx.utils.Align.right, com.badlogic.gdx.utils.Align.right);
			break;
		default:
			break;
		}
	}
	
	public Align getAlign() {
		return align;
	}
}
