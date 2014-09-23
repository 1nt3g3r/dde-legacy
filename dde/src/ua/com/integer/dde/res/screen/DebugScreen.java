package ua.com.integer.dde.res.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DebugScreen extends AbstractScreen {
	private ShapeRenderer sRenderer = new ShapeRenderer();
	
	@Override
	public void render(float delta) {
		super.render(delta);

		sRenderer.setProjectionMatrix(getStage().getBatch().getProjectionMatrix());
		sRenderer.setTransformMatrix(getStage().getBatch().getTransformMatrix());
		sRenderer.begin(ShapeType.Line);
		sRenderer.setColor(Color.RED);
		for(Actor a : getStage().getActors()) {
			sRenderer.rect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
			
			sRenderer.setColor(Color.GREEN);
			sRenderer.line(a.getX() + a.getWidth()/2, a.getY(), a.getX() + a.getWidth()/2, a.getTop());
			sRenderer.line(a.getX(), a.getY() + a.getHeight()/2, a.getRight(), a.getY()+a.getHeight()/2);
		}
		sRenderer.end();
	}
}
