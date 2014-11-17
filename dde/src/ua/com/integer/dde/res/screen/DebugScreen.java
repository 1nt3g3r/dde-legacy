package ua.com.integer.dde.res.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DebugScreen extends AbstractScreen {
	private ShapeRenderer sRenderer = new ShapeRenderer();
	
	private boolean drawBorder, drawCenter, drawCross;
	
	public DebugScreen() {
		drawBorder = true;
		drawCenter = true;
		drawCross = true;
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);

		sRenderer.setProjectionMatrix(getStage().getBatch().getProjectionMatrix());
		sRenderer.setTransformMatrix(getStage().getBatch().getTransformMatrix());
		sRenderer.begin(ShapeType.Line);
		sRenderer.setColor(Color.RED);
		for(Actor a : getStage().getActors()) {
			if (drawBorder) {
				sRenderer.setColor(Color.BLUE);
				sRenderer.rect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
			}
			
			if (drawCross) {
				sRenderer.setColor(Color.GREEN);
				sRenderer.line(a.getX() + a.getWidth()/2, a.getY(), a.getX() + a.getWidth()/2, a.getTop());
				sRenderer.line(a.getX(), a.getY() + a.getHeight()/2, a.getRight(), a.getY()+a.getHeight()/2);
			}
			
			if (drawCenter) {
				sRenderer.setColor(Color.RED);
				sRenderer.circle(a.getX() + a.getWidth()/2, a.getY() + a.getHeight()/2, 2);
			}
		}
		sRenderer.end();
	}
	
	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder = drawBorder;
	}
	
	public void setDrawCenter(boolean drawCenter) {
		this.drawCenter = drawCenter;
	}
	
	public void setDrawCross(boolean drawCross) {
		this.drawCross = drawCross;
	}
}
