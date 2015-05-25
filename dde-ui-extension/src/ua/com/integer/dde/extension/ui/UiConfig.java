package ua.com.integer.dde.extension.ui;

import java.io.File;

import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Конфиг виджета. Из обязательных параметров - имя, 
 * размеры и положение на экране. 
 * Остальные настройки специфические для актеров
 * 
 * @author 1nt3g3r
 */
public class UiConfig {
	public String name;
	
	public WidgetType widgetType = WidgetType.TEXTURE_REGION_GROUP_ACTOR;
	public String extensionId;
	public Side parentCorner = Side.CENTER;
	public Side targetCorner = Side.CENTER;
	public Size horizontalDistance = new Size();
	public Size verticalDistance = new Size();
	public Size width = new Size();
	public Size height = new Size();
	
	public Array<UiConfig> children = new Array<UiConfig>();
	
	public ObjectMap<String, String> properties = new ObjectMap<String, String>();
	
	public UiConfig() {
		width.setSizeValue(0.5f);
		height.setSizeValue(0.5f);
	}
	
	public void saveToFile(File file) {
		JsonWorker.toJson(this, file);
	}
	
	public void setSide(Side side) {
		parentCorner = side;
		targetCorner = side;
	}
	
	public void setSize(Side side, Actor target) {
		setSide(side);
		loadPositionFromActor(target);
	}

	public static UiConfig fromFileHandle(FileHandle handle) {
		return JsonWorker.JSON.fromJson(UiConfig.class, handle);
	}
	
	public static UiConfig fromFile(File file) {
		return JsonWorker.fromJson(UiConfig.class, file);
	}
	
	@Override
	public String toString() {
		return name + "";
	}
	
	/**
	 * Установить значение свойства
	 * @param name имя
	 * @param value значение
	 */
	public void set(String name, String value) {
		properties.put(name, value);
	}
	
	/**
	 * Возвращает значение свойства
	 * @param name имя свойства
	 * @param defValue строка по умолчанию - возвращается, если нет значения для заданного имени свойства
	 */
	public String get(String name, String defValue) {
		return properties.get(name, defValue);
	}
	
	/**
	 * Возвращает значение свойства с указанным именем. Если нет значения для заданного свойства, 
	 * возвращается пустая строка
	 * 
	 * @param name имя свойства
	 */
	public String get(String name) {
		return get(name, "");
	}

	/**
	 * Returns name and properties of this config
	 */
	public void print() {
		System.out.println("Config: " + name);
		System.out.println(properties);
	}
	
	/**
	 * Updates properties of this config (horizontalDistance and verticalDistance) from the given actor. 
	 * You should use this method if you drag actor by mouse and want to save position to config
	 */
	public void loadPositionFromActor(Actor actor) {
		switch(parentCorner) {
		case BOTTOM_LEFT:
			loadFromActorBottomLeft(actor);
			break;
		case BOTTOM_RIGHT:
			loadFromActorBottomRight(actor);
			break;
		case CENTER:
			loadFromActorCenterSide(actor);
			break;
		case TOP_LEFT:
			loadFromActorTopLeft(actor);
			break;
		case TOP_RIGHT:
			loadFromActorTopRight(actor);
			break;
		default:
			break;
		
		}
	}
	
	private void loadFromActorCenterSide(Actor actor) {
		float actorCenterX = actor.getX() + actor.getWidth()/2f;
		float actorCenterY = actor.getY() + actor.getHeight()/2f;
		
		Group parent = actor.getParent();
		float parentCenterX = parent.getWidth()/2f;
		float parentCenterY = parent.getHeight()/2f;
		
		float dstX = actorCenterX - parentCenterX;
		float dstY = actorCenterY - parentCenterY;
		updateDistances(actor, dstX, dstY);
	}
	
	private void loadFromActorBottomLeft(Actor actor) {
		updateDistances(actor, actor.getX(), actor.getY());
	}
	
	private void loadFromActorBottomRight(Actor actor) {
		Group parent = actor.getParent();
		float dstX = parent.getWidth() - actor.getWidth() - actor.getX();
		updateDistances(actor, dstX, actor.getY());
	}
	
	private void loadFromActorTopLeft(Actor actor) {
		Group parent = actor.getParent();
		float dstY = parent.getHeight() - actor.getHeight() - actor.getY();
		updateDistances(actor, actor.getX(), dstY);
	}
	
	private void loadFromActorTopRight(Actor actor) {
		Group parent = actor.getParent();
		float dstX = parent.getWidth() - actor.getWidth() - actor.getX();
		float dstY = parent.getHeight() - actor.getHeight() - actor.getY();
		updateDistances(actor, dstX, dstY);
	}
	
	private void updateDistances(Actor actor, float dstX, float dstY) {
		horizontalDistance.loadFromValue(actor, dstX);
		verticalDistance.loadFromValue(actor, dstY);
	}
	
	public void loadSizeFromActor(Actor actor) {
		float oldX = actor.getX();
		float oldY = actor.getY();
		
		height.loadFromValue(actor, actor.getHeight());
		width.loadFromValue(actor, actor.getWidth());
		
		actor.setPosition(oldX, oldY);
		loadPositionFromActor(actor);
	}

	public boolean differs(UiConfig config) {
		if (config.widgetType != widgetType) {
			return true;
		}
		
		if (config.widgetType == widgetType && widgetType == WidgetType.EXTENSION_ACTOR) {
			return !config.extensionId.equals(extensionId);
		}
		
		return false;
	}
}
