package ua.com.integer.dde.extension.ui;

import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.res.screen.ScreenListener;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Располагает актера по "угловой" раскладке
 * 
 * @author 1nt3g3r
 */
public class UiConfigurator implements ScreenListener {
	private Actor target;
	private float distanceX, distanceY;
	
	private UiConfig config;
	
	public void setTarget(Actor target) {
		this.target = target;
	}
	
	public void setConfig(UiConfig config) {
		this.config = config;
	}
	
	public Actor getTarget() {
		return target;
	}
	
	public UiConfig getConfig() {
		return config;
	}

	@Override
	public void eventHappened(AbstractScreen screen, ScreenEvent event) {
		if (event == ScreenEvent.RESIZE) {
			position();
		}
	}
	
	private void position() {
		if (target == null) return;
		
		setupDistances();
		setupSizes();
		
		switch(config.parentCorner) {
		case BOTTOM_LEFT:
			handleParentBottomLeft();
			break;
		case BOTTOM_RIGHT:
			handleParentBottomRight();
			break;
		case CENTER:
			handleParentCenter();
			break;
		case TOP_LEFT:
			handleParentTopLeft();
			break;
		case TOP_RIGHT:
			handleParentTopRight();
			break;
		default:
			break;
		}
	}
	
	private void setupDistances() {
		if (!(config.horizontalDistance.needParentActor() && target.getParent() == null)) {
			distanceX = config.horizontalDistance.getValue(target);
		}
		
		if (!(config.verticalDistance.needParentActor() && target.getParent() == null)) {
			distanceY = config.verticalDistance.getValue(target);
		}
	}
	
	private void setupSizes() {
		//Если нам нужен родитель-группа для определения размеров, а его нет - ничего не определяем
		if ((config.width.needParentActor() || config.height.needParentActor()) && !target.hasParent()) {
			return;
		}
		
		target.setSize(config.width.getValue(target), config.height.getValue(target));
	}
	
	private void handleParentBottomLeft() {
		target.setPosition(distanceX, distanceY);
	}
	
	private void handleParentBottomRight() {
		if (target.getParent() == null) return;
		
		target.setPosition(target.getParent().getWidth() - target.getWidth() - distanceX, distanceY);
	}
	
	private void handleParentCenter() {
		if (target.getParent() == null) return;
		
		target.setX(distanceX + (target.getParent().getWidth() - target.getWidth())/2);
		target.setY(distanceY + (target.getParent().getHeight() - target.getHeight())/2);
	}
	
	private void handleParentTopLeft() {
		if (target.getParent() == null) return;
		
		target.setX(distanceX);
		target.setY(target.getParent().getHeight() - target.getHeight() - distanceY);
	}
	
	private void handleParentTopRight() {
		if (target.getParent() == null) return;
		
		target.setX(target.getParent().getWidth() - target.getWidth() - distanceX);
		target.setY(target.getParent().getHeight() - target.getHeight() - distanceY);
	}
}
