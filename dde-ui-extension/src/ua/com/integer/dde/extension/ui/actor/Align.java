package ua.com.integer.dde.extension.ui.actor;

/**
 * Варианты выравнивания
 * 
 * @author 1nt3g3r
 */
public enum Align {
	CENTER,
	TOP_LEFT,
	TOP_CENTER,
	TOP_RIGHT,
	BOTTOM_LEFT,
	BOTTOM_CENTER,
	BOTTOM_RIGHT,
	LEFT_CENTER,
	RIGHT_CENTER;
	
	/**
	 * Возвращает выравнивание как комбинацию битовых флагов, используя 
	 * константы из com.badlogic.gdx.scenes.scene2d.utils.Align
	 */
	public int getAlign() {
		int toReturn = com.badlogic.gdx.scenes.scene2d.utils.Align.center;
		
		switch(this) {
		case BOTTOM_CENTER: 
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.bottom;
			break;
		case BOTTOM_LEFT: 
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.bottom;
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.left;
			break;
		case BOTTOM_RIGHT:
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.bottom;
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.right;
			break;
		case CENTER:
			break;
		case LEFT_CENTER:
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.left;
			break;
		case RIGHT_CENTER:
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.right;
			break;
		case TOP_CENTER:
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.top;
			break;
		case TOP_LEFT:
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.top;
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.left;
			break;
		case TOP_RIGHT:
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.top;
			toReturn |= com.badlogic.gdx.scenes.scene2d.utils.Align.right;
			break;
		}
		
		return toReturn;
	}
}