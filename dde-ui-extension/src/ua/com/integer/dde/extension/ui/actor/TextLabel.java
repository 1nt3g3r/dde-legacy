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
		switch(al) {
		case CENTER: 
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.center, com.badlogic.gdx.scenes.scene2d.utils.Align.center);
			break;
		case TOP_LEFT:
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.left | com.badlogic.gdx.scenes.scene2d.utils.Align.top, com.badlogic.gdx.scenes.scene2d.utils.Align.left);
			break;
		case BOTTOM_CENTER:
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.center | com.badlogic.gdx.scenes.scene2d.utils.Align.bottom, com.badlogic.gdx.scenes.scene2d.utils.Align.center);
			break;
		case BOTTOM_LEFT:
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.bottom | com.badlogic.gdx.scenes.scene2d.utils.Align.left, com.badlogic.gdx.scenes.scene2d.utils.Align.left);
			break;
		case BOTTOM_RIGHT: 
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.bottom | com.badlogic.gdx.scenes.scene2d.utils.Align.right, com.badlogic.gdx.scenes.scene2d.utils.Align.right);
			break;
		case LEFT_CENTER: 
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.left | com.badlogic.gdx.scenes.scene2d.utils.Align.center, com.badlogic.gdx.scenes.scene2d.utils.Align.left);
			break;
		case RIGHT_CENTER: 
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.right | com.badlogic.gdx.scenes.scene2d.utils.Align.center, com.badlogic.gdx.scenes.scene2d.utils.Align.right);
			break;
		case TOP_CENTER:
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.center | com.badlogic.gdx.scenes.scene2d.utils.Align.top, com.badlogic.gdx.scenes.scene2d.utils.Align.center);
			break;
		case TOP_RIGHT: 
			setAlignment(com.badlogic.gdx.scenes.scene2d.utils.Align.top | com.badlogic.gdx.scenes.scene2d.utils.Align.right, com.badlogic.gdx.scenes.scene2d.utils.Align.right);
			break;
		default:
			break;
		}
	}
}
