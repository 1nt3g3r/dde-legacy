package ua.com.integer.dde.extension.ui.size;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Типы значений, 
 * которые могут указываться для указания размера 
 * или позиционирования обьекта на экране.
 * 
 * @author 1nt3g3r
 *
 */
public enum SizeType {
	/**
	 * Использование абсолютных величин. Почти всегда является дурным тоном - 
	 * за редким исключением, как, например, линии, которые должны всегда быть одной толщины.
	 */
	ABSOLUTE,
	/**
	 * Ширина экрана
	 */
	SCREEN_WIDTH,
	/**
	 * Высота экрана
	 */
	SCREEN_HEIGHT,
	/**
	 * Ширина актера-родителя, в которой находится данный актер
	 */
	PARENT_WIDTH,
	/**
	 * Высота актера-родителся, в которой находится данный актер
	 */
	PARENT_HEIGHT;
	
	/**
	 * Возвращает соответсвующее числовое значение для выбранного типа расстояния
	 */
	public float getValue() {
		switch(this) {
		case ABSOLUTE : return 1;
		case SCREEN_WIDTH : return Gdx.graphics.getWidth();
		case SCREEN_HEIGHT : return Gdx.graphics.getHeight();
		case PARENT_WIDTH : 
		case PARENT_HEIGHT:
			throw new IllegalArgumentException("You can get value of " + this + " using only getValue(actor) method!");
		}
		throw new IllegalArgumentException("You can not call getValue() for " + this + "!");
	}
	
	/**
	 * Возвращает соответсвующее числовое значение для ширины или высоты родителя актера
	 * @param actor актер, для которого нужно узнать размеры родительськой группы
	 */
	public float getValue(Actor actor) {
		switch(this) {
		case PARENT_WIDTH : return actor.getParent().getWidth();
		case PARENT_HEIGHT : return actor.getParent().getHeight();
		default : return getValue();
		}
	}
	
	/**
	 * Нужно ли передавать актера, чтобы получить числовое значение для этого перечисления
	 */
	public boolean needParent() {
		return this == PARENT_WIDTH || this == PARENT_HEIGHT;
	}
	
}
