package ua.com.integer.dde.extension.ui;

import ua.com.integer.dde.extension.ui.actor.Box;
import ua.com.integer.dde.extension.ui.actor.TextLabel;
import ua.com.integer.dde.extension.ui.actor.TextureRegionButton;
import ua.com.integer.dde.extension.ui.skin.DefaultSkin;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.ui.actor.PageControl;
import ua.com.integer.dde.ui.actor.TextureRegionGroupActor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
	 * Страничный скроллинг
	 */
	PAGE_CONTROL,
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
	TOUCHPAD,
	/**
	 * Актер расширения
	 */
	EXTENSION_ACTOR;
	
	public static final WidgetType[] SIMPLE_WIDGETS = {
		IMAGE, TEXT_LABEL, BUTTON, TEXT_BUTTON, CHECKBOX, TEXT_FIELD, TOUCHPAD
	};
	
	public static final WidgetType[] CONTAINER_WIDGETS = {
		EMPTY_GROUP, TEXTURE_REGION_GROUP_ACTOR, BOX, SCROLL_PANE, PAGE_CONTROL
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
		Actor result = null;
		switch(this) {
		case EMPTY_GROUP: 
			result = new Group();
			break;
		case TEXTURE_REGION_GROUP_ACTOR : 
			result = new TextureRegionGroupActor();
			break;
		case TEXT_LABEL : 
			result = new TextLabel();
			break;
		case BOX : 
			result = new Box();
			break;
		case TEXTURE_REGION_BUTTON : 
			result = new TextureRegionButton();
			break;
		case SCROLL_PANE : 
			result = new ScrollPane(null);
			break;
		case TEXT_FIELD : 
			TextField toReturn = new TextField("", DefaultSkin.getInstance().getSkin());
			TextFieldStyle copyStyle = new TextFieldStyle(toReturn.getStyle());
			toReturn.setStyle(copyStyle);
			
			result = toReturn;
			break;
		case IMAGE: 
			return new Image();
		case BUTTON:
			Button toReturnButton = new Button(DefaultSkin.getInstance().getSkin());
			ButtonStyle copyButtonStyle = new ButtonStyle(toReturnButton.getStyle());
			toReturnButton.setStyle(copyButtonStyle);
			result = toReturnButton;
			break;
		case TEXT_BUTTON:
			TextButton toReturnTextButton  = new TextButton("Button", DefaultSkin.getInstance().getSkin());
			if (isEditor()) {
				makeInnerActorsUntochable(toReturnTextButton);
				toReturnTextButton.clearListeners();
			}
			TextButtonStyle copyTextButtonStyle = new TextButtonStyle(toReturnTextButton.getStyle());
			toReturnTextButton.setStyle(copyTextButtonStyle);
			result = toReturnTextButton;
			break;
		case CHECKBOX:
			CheckBox checkbox = new CheckBox("Checkbox", DefaultSkin.getInstance().getSkin());
			CheckBoxStyle checkboxStyle = new CheckBoxStyle(checkbox.getStyle());
			checkbox.setStyle(checkboxStyle);
			result = checkbox;
			break;
		case TOUCHPAD:
			Touchpad touchpad = new Touchpad(2, DefaultSkin.getInstance().getSkin());
			TouchpadStyle touchpadStyle = new TouchpadStyle(touchpad.getStyle());
			touchpad.setStyle(touchpadStyle);
			result = touchpad;
			break;
		case PAGE_CONTROL:
			PageControl slideControl = new PageControl();
			if (isEditor()) {
				makeInnerActorsUntochable(slideControl);
			}
			result = slideControl;
			break;
		default:
			break;
		}
		
		if (isEditor()) {
			result.setTouchable(Touchable.enabled);
			result.clearListeners();
		}
		
		return result;
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
		case PAGE_CONTROL: return "Page slide control";
		default: return "";
		}
	}
	
	private boolean isEditor() {
		String coreName = AbstractScreen.getKernel().getClass().getName();
		return coreName.contains("EditorKernel");
	}
	
	private void makeInnerActorsUntochable(Group actor) {
		for(Actor child : actor.getChildren()) {
			child.setTouchable(Touchable.disabled);
			if (child instanceof Group) {
				makeInnerActorsUntochable((Group) child);
			}
		}
	}
}
