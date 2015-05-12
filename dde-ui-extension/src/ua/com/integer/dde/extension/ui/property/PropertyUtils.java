package ua.com.integer.dde.extension.ui.property;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.WidgetType;
import ua.com.integer.dde.extension.ui.property.imp.box.BoxPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.button.ButtonPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.checkbox.CheckboxPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.image.ImagePropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.pagecontrol.PageControlPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.scrollbar.ScrollPanelPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.textbutton.TextButtonPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.textfield.TextFieldPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.textlabel.TextLabelPropertySupporter;
import ua.com.integer.dde.extension.ui.property.imp.textureregiongroup.TextureRegionGroupActorSupporter;
import ua.com.integer.dde.extension.ui.property.imp.touchpad.TouchapadPropertySupporter;
import ua.com.integer.dde.kernel.DDKernel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;

public class PropertyUtils {
	private static ObjectMap<WidgetType, PropertySupporter> propertySupporters; 
	
	static {
		propertySupporters = new ObjectMap<WidgetType, PropertySupporter>();
		
		propertySupporters.put(WidgetType.TEXTURE_REGION_GROUP_ACTOR, new TextureRegionGroupActorSupporter());
		propertySupporters.put(WidgetType.TEXTURE_REGION_BUTTON, propertySupporters.get(WidgetType.TEXTURE_REGION_GROUP_ACTOR));

		propertySupporters.put(WidgetType.TEXT_LABEL, new TextLabelPropertySupporter());
		propertySupporters.put(WidgetType.TEXT_FIELD, new TextFieldPropertySupporter());
		propertySupporters.put(WidgetType.IMAGE, new ImagePropertySupporter());
		propertySupporters.put(WidgetType.BUTTON, new ButtonPropertySupporter());
		propertySupporters.put(WidgetType.TEXT_BUTTON, new TextButtonPropertySupporter());
		propertySupporters.put(WidgetType.CHECKBOX, new CheckboxPropertySupporter());
		propertySupporters.put(WidgetType.TOUCHPAD, new TouchapadPropertySupporter());
		
		propertySupporters.put(WidgetType.BOX, new BoxPropertySupporter());
		propertySupporters.put(WidgetType.SCROLL_PANE, new ScrollPanelPropertySupporter());
		propertySupporters.put(WidgetType.PAGE_CONTROL, new PageControlPropertySupporter());
	}
	
	public static void setupActor(UiConfig config, Actor actor, DDKernel kernel) {
		propertySupporters.get(config.widgetType).setup(config, actor, kernel);
	}
	
	public static PropertySupporter getSupporter(WidgetType type) {
		return propertySupporters.get(type);
	}
}
