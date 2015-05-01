package ua.com.integer.dde.ui.actor;

import ua.com.integer.dde.res.graphics.TextureManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Актер-группа с подложкой - регионом текстуры. Если регион текстуры не установлен, 
 * то данный актер ведет себя как обычная группа
 * 
 * @author 1nt3g3r
 */
public class TextureRegionGroupActor extends Group {
	private TextureRegion region;

	/**
	 * Создает группу без подложки
	 */
	public TextureRegionGroupActor() {
	}
	
	/**
	 * Создает группу с подложкой
	 * @param region регион текстуры - подложка
	 */
	public TextureRegionGroupActor(TextureRegion region) {
		this.region = region;
	}
	
	/**
	 * Устанавливает фон - подложку
	 * @param tm менеджер текстур
	 * @param packName название атласа
	 * @param regionName название региона
	 */
	public void setRegion(TextureManager tm, String packName, String regionName) {
		this.region = tm.get(packName, regionName);
	}
	
	/**
	 * Устанавливает фон
	 * @param region регион текстуры - фон
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
		
		super.draw(batch, parentAlpha);
	}
	
	public TextureRegion getRegion() {
		return region;
	}
}
