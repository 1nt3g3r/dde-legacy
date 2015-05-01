package ua.com.integer.dde.extension.ui;

import ua.com.integer.dde.extension.ui.actor.Box;
import ua.com.integer.dde.extension.ui.actor.TextLabel;
import ua.com.integer.dde.extension.ui.actor.TextureRegionButton;
import ua.com.integer.dde.extension.ui.skin.DefaultSkin;
import ua.com.integer.dde.ui.actor.TextureRegionGroupActor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;

/**
 * Типы виджетов пользовательського интерфейса
 * @author integer
 *
 */
public enum WidgetType {
	/**
	 * Пустая группа
	 */
	EMPTY_GROUP,
	/**
	 * Горизонтальная группа
	 */
	BOX,
	/**
	 * Группа с фоном-текстурой
	 */
	TEXTURE_REGION_GROUP_ACTOR,
	/**
	 * Панель с возможность прокрутки
	 */
	SCROLL_PANE,
	/**
	 * Текстовая метка из scene2d
	 */
	TEXT_LABEL,
	/**
	 * Поле для ввода текста из scene2d
	 */
	TEXT_FIELD,
	/**
	 * Кнопка из scene2d
	 */
	BUTTON,
	/**
	 * Кнопка с текстом из scene2d
	 */
	TEXT_BUTTON,
	/**
	 * Чекбокс (кнопка с двумя состояниями) из scene2d
	 */
	CHECKBOX,
	/**
	 * Кнопка с картинкой
	 * 
	 */
	TEXTURE_REGION_BUTTON, 
	/**
	 * Картинка
	 */
	IMAGE,
	/**
	 * Тачпад из scene2d
	 */
	TOUCHPAD;
	
	public static final WidgetType[] SIMPLE_WIDGETS = {
		IMAGE, TEXT_LABEL, BUTTON, TEXT_BUTTON, CHECKBOX, TEXT_FIELD, TOUCHPAD
	};
	
	public static final WidgetType[] CONTAINER_WIDGETS = {
		EMPTY_GROUP, TEXTURE_REGION_GROUP_ACTOR, BOX, SCROLL_PANE
	};
	
	public static final WidgetType[] OTHER_WIDGETS = {
		TEXTURE_REGION_BUTTON
	};
	
	/**
	 * Is this widget can contain other widgets
	 * @return
	 */
	public boolean isContainer() {
		for(WidgetType type : CONTAINER_WIDGETS) {
			if (this == type) {
				return true;
			}
		}
		
		return false;
	}
	
	public Actor createWidget() {
		switch(this) {
		case EMPTY_GROUP: 
			return new Group();
		case TEXTURE_REGION_GROUP_ACTOR : 
			return new TextureRegionGroupActor();
		case TEXT_LABEL : 
			return new TextLabel();
		case BOX : 
			return new Box();
		case TEXTURE_REGION_BUTTON : 
			return new TextureRegionButton();
		case SCROLL_PANE : 
			return new ScrollPane(null);
		case TEXT_FIELD : 
			TextField toReturn = new TextField("", DefaultSkin.getInstance().getSkin());
			TextFieldStyle copyStyle = new TextFieldStyle(toReturn.getStyle());
			toReturn.setStyle(copyStyle);
			return toReturn;
		case IMAGE: 
			return new Image();
		case BUTTON:
			Button toReturnButton = new Button(DefaultSkin.getInstance().getSkin());
			ButtonStyle copyButtonStyle = new ButtonStyle(toReturnButton.getStyle());
			toReturnButton.setStyle(copyButtonStyle);
			return toReturnButton;
		case TEXT_BUTTON:
			TextButton toReturnTextButton  = new TextButton("Button", DefaultSkin.getInstance().getSkin());
			TextButtonStyle copyTextButtonStyle = new TextButtonStyle(toReturnTextButton.getStyle());
			toReturnTextButton.setStyle(copyTextButtonStyle);
			return toReturnTextButton;
		case CHECKBOX:
			CheckBox checkbox = new CheckBox("Checkbox", DefaultSkin.getInstance().getSkin());
			CheckBoxStyle checkboxStyle = new CheckBoxStyle(checkbox.getStyle());
			checkbox.setStyle(checkboxStyle);
			return checkbox;
		case TOUCHPAD:
			Touchpad touchpad = new Touchpad(2, DefaultSkin.getInstance().getSkin());
			TouchpadStyle touchpadStyle = new TouchpadStyle(touchpad.getStyle());
			touchpad.setStyle(touchpadStyle);
			return touchpad;
		default:
			break;
		}
		
		return null;
	}
	
	public String getShortDescription() {
		switch(this) {
		case EMPTY_GROUP: return "Empty group";
		case TEXTURE_REGION_GROUP_ACTOR : return "Group with texture background";
		case TEXT_LABEL : return "Label";
		case BOX : return "Box";
		case TEXTURE_REGION_BUTTON : return "Image button";
		case TEXT_FIELD : return "Text field";
		case SCROLL_PANE : return "Scroll panel";
		case IMAGE : return "Image";
		case BUTTON : return "Button";
		case TEXT_BUTTON : return "Text button";
		case CHECKBOX : return "Checkbox";
		case TOUCHPAD : return "Touchpad";
		default: return "";
		}
	}
}
