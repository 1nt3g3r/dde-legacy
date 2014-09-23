package ua.com.integer.dde.extension.ui.actor;

import ua.com.integer.dde.extension.ui.layout.Layout;
import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

/**
 * Располагает дочерних актеров горизонтально, 
 * один возле одного
 * 
 * @author 1nt3g3r
 */
public class Box extends Group implements Layout {
	private Size pad;
	
	private Array<Actor> tmpActors = new Array<Actor>();
	private Align orientation = Align.CENTER;
	
	/**
	 * Варианты расположения актеров
	 * 
	 * @author 1nt3g3r
	 */
	public static enum Align {
		/**
		 * Расположить слева направо
		 */
		LEFT_TO_RIGHT,
		/**
		 * Расположить справа налево
		 */
		RIGHT_TO_LEFT,
		/**
		 * Сгруппировать по центру
		 */
		CENTER,
		/**
		 * Расположить сверху вниз
		 */
		TOP_TO_BOTTOM,
		/**
		 * Расположить снизу вверх
		 */
		BOTTOM_TO_TOP,
	};

	public Box() {
		pad = new Size();
		pad.setType(SizeType.ABSOLUTE);
		pad.setSizeValue(5);
	}
	
	public void setAlignment(Align orientation) {
		this.orientation = orientation;
		
		layout();
	}
	
	@Override
	public void layout() {
		switch(orientation) {
		case LEFT_TO_RIGHT:
			layoutActorsLeftToRight();
			centerY();
			break;
		case RIGHT_TO_LEFT:
			layoutActorsRightToLeft();
			centerY();
			break;
		case CENTER:
			layoutActorsCenter();
			centerY();
			break;
		case BOTTOM_TO_TOP:
			layoutBottomToTop();
			centerX();
			break;
		case TOP_TO_BOTTOM:
			layoutTopToBottom();
			centerX();
			break;
		default:
			break;
		}
	}
	
	private void centerX() {
		for(Actor actor : getChildren()) {
			actor.setX((getWidth() - actor.getWidth())/2);
		}
	}
	
	private void centerY() {
		for(Actor actor : getChildren()) {
			actor.setY((getHeight() - actor.getHeight())/2);
		}
	}
	
	private void layoutActorsCenter() {
		float totalPadWidth = (getChildren().size - 1) * pad.getValue();
		
		float x = (getWidth() - getTotalChildrenWidth())/2 - totalPadWidth/2;
		
		for(Actor actor : getChildren()) {
			actor.setX(x);
			
			x += actor.getWidth();
			x += pad.getValue();
		}
	}

	private void layoutActorsLeftToRight() {
		float x = pad.getValue();
		for(Actor actor : getChildren()) {
			actor.setX(x);
			
			x += actor.getWidth();
			x += pad.getValue();
		}
	}
	
	private void layoutActorsRightToLeft() {
		float x = getWidth();
		
		tmpActors.clear();
		tmpActors.addAll(getChildren());
		tmpActors.reverse();
		
		for(Actor actor : tmpActors) {
			actor.setX(x - actor.getWidth() - pad.getValue());
			x -= actor.getWidth();
			x -= pad.getValue();
		}
	}
	
	private void layoutBottomToTop() {
		float y = pad.getValue();
		
		for(Actor actor: getChildren()) {
			actor.setY(y);
			
			y += (actor.getHeight() + pad.getValue());
		}
	}
	
	private void layoutTopToBottom() {
		float y = getHeight();
		
		for(Actor actor : getChildren()) {
			actor.setY(y - actor.getHeight() - pad.getValue());
			y -= (actor.getHeight() + pad.getValue());
		}
	}
	
	private float getTotalChildrenWidth() {
		float toReturn = 0;
		
		for(Actor child : getChildren()) toReturn += child.getWidth();
		
		return toReturn;
	}
	
	@Override
	public void addActor(Actor actor) {
		super.addActor(actor);
		
		layout();
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		layout();
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		
		layout();
	}
	
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		
		layout();
	}

	public void setPad(Size size) {
		pad = size;
		
		layout();
	}
}
