package ua.com.integer.dde.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class PageControl extends Group {
	private ScrollPane scroll;
	
	private float scrollSize = Gdx.graphics.getWidth();
	private float elapsedTimeToScroll = 0f;
	
	private boolean verticalOrientation;
	
	public PageControl() {
		scroll = new ScrollPane(null);
		scroll.setFadeScrollBars(false);
		addActor(scroll);
		
		setVerticalOrientation(false);
	}

	public void setVerticalOrientation(boolean verticalOrientation) {
		this.verticalOrientation = verticalOrientation;
		if (verticalOrientation) {
			scroll.setScrollingDisabled(true, false);
		} else {
			scroll.setScrollingDisabled(false, true);
		}
	}
	
	public void setWidget(Actor widget) {
		scroll.setWidget(widget);
	}
	
	public void setScrollSize(float scrollSize) {
		this.scrollSize = scrollSize;
	}
	
	@Override
	public void act(float delta) {
		elapsedTimeToScroll += delta;
		if (elapsedTimeToScroll >= 0.3f) {
			elapsedTimeToScroll = 0f;
			updateScroll();
		}
		
		super.act(delta);
	}
	
	private void updateScroll() {
		if (verticalOrientation) {
			updateForVerticalScrolling();
		} else {
			updateForHorizontalScrolling();
		}
	}
	
	private void updateForHorizontalScrolling() {
		float nearestX = getNearestX();
		if (!scroll.isPanning() && Math.abs(nearestX - scroll.getScrollX()) >= 2 && Math.abs(scroll.getVelocityX()) <= 0.00f) {
			scroll.setScrollX(nearestX);
		}
	}
	
	private float getNearestX() {
		float currentX = scroll.getScrollX();
		float tmp = (currentX/scrollSize) * 10;
		int lastDigit = ((int) tmp)%10;
		int count = (int) (currentX/scrollSize);
		if (lastDigit >= 5) {
			count++;
		}
		return count * scrollSize;
	}
	
	private void updateForVerticalScrolling() {
		float nearestY = getNearestY();
		if (!scroll.isPanning() && Math.abs(nearestY - scroll.getScrollY()) >= 2 && Math.abs(scroll.getVelocityY()) <= 0.00f) {
			scroll.setScrollY(nearestY);
		}
	}
	
	private float getNearestY() {
		float currentY = scroll.getScrollY();
		float tmp = (currentY / scrollSize) * 10;
		int lastDigit = ((int) tmp) % 10;
		int count = (int) (currentY / scrollSize);
		if (lastDigit >= 5) {
			count++;
		}
		return count * scrollSize;
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		
		scroll.setSize(width, height);
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		
		scroll.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		
		scroll.setHeight(height);
	}

	public void scrollToStart() {
		if (verticalOrientation) {
			scroll.setScrollY(0);
		} else {
			scroll.setScrollX(0);
		}
	}

	public int getCurrentPage() {
		if (verticalOrientation) {
			return (int) (getNearestY() / scrollSize) + 1;
		} else {
			return (int) (getNearestX() / scrollSize) + 1;
		}
	}
	
	public void scrollToPage(int page) {
		if (verticalOrientation) {
			scroll.setScrollY((page - 1) * scrollSize);
		} else {
			scroll.setScrollX((page - 1) * scrollSize);
		}
	}
	
	public void scrollToNextPage() {
		scrollToPage(getCurrentPage() + 1);
	}
	
	public void scrollToPrevPage() {
		scrollToPage(getCurrentPage() - 1);
	}
	
	public ScrollPane getScroll() {
		return scroll;
	}
}
