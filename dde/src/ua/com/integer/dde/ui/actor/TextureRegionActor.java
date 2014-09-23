package ua.com.integer.dde.ui.actor;

import ua.com.integer.dde.res.graphics.TextureManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Простой актер, который рисует регион текстуры. Если регион текстуры 
 * не выбран, актер не будет рисоваться
 * 
 * @author 1nt3g3r
 */
public class TextureRegionActor extends Actor {
	private TextureRegion region;

	/**
	 * Создает актера без текстуры
	 */
	public TextureRegionActor() {
	}
	
	/**
	 * Создает актера с регионом текстуры
	 * @param region
	 */
	public TextureRegionActor(TextureRegion region) {
		setRegion(region);
	}
	
	/**
	 * Устанавливает регион текстуры
	 * @param tm менеджер текстур
	 * @param packName имя пака
	 * @param regionName имя региона текстуры
	 */
	public void setRegion(TextureManager tm, String packName, String regionName) {
		this.region = tm.get(packName, regionName);
	}
	
	/**
	 * Устанавливает регион текстуры
	 * @param region регион текстуры
	 */
	public void setRegion(TextureRegion region) {
		this.region = region;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		if (region != null) {
			batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		}
	}
}
