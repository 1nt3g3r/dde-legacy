package ua.com.integer.dde.extension.ui.property.util.actor;

import ua.com.integer.dde.extension.ui.Actors;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.UiConfigurator;
import ua.com.integer.dde.extension.ui.layout.LayoutListener;
import ua.com.integer.dde.extension.ui.property.PropertySupporter;
import ua.com.integer.dde.extension.ui.property.PropertyUtils;
import ua.com.integer.dde.extension.ui.property.imp.common.CommonPropertySupporter;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenEvent;
import ua.com.integer.dde.ui.actor.PageControl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

/**
 * Вспомогательный класс для работы с актерами, 
 * которые хранятся в конфигурационных файлах. Позволяет создать и загрузить актера из файла 
 * в актер-группу или в корневую группу экрана
 * 
 * @author 1nt3g3r
 */
public class ActorUtils {
	/**
	 * Создает актера по описанию и добавляет его в группу-родителя parent. Создает слушателя на 
	 * изменения экрана, который связан с этим актером и меняет его размеры\положение\другие параметры 
	 * при изменении размера экрана. Также связывает с этим актером конфигурационный файл - вызовом setUserObject. 
	 * Возвращает созданного актера
	 * 
	 * @param screen экран, который будет отсылать события изменения размера
	 * @param config конфигурационный файл с описанием актера
	 * @param parent группа, куда будет добавлен созданный файл
	 */
	public static Actor addActor(AbstractScreen screen, UiConfig config, Group parent) {
		Actor actor = config.widgetType.createWidget();
		actor.setName(config.name);
		actor.setUserObject(config);
		
		UiConfigurator configurator = new UiConfigurator();
		configurator.setConfig(config);
		configurator.setTarget(actor);
		
		if (parent instanceof ScrollPane) {
			((ScrollPane) parent).setWidget(actor);
		} else if (parent instanceof PageControl) {
			((PageControl) parent).setWidget(actor);
		} else {
			parent.addActor(actor);
		}
		screen.addScreenEventListener(configurator);
		configurator.eventHappened(screen, ScreenEvent.RESIZE);
		
		PropertySupporter actorSpecificSupporter = PropertyUtils.getSupporter(config.widgetType);
		if (actorSpecificSupporter != null) {
			actorSpecificSupporter.setup(config, actor, AbstractScreen.getKernel());
		}

		CommonPropertySupporter.getInstance().setup(config, actor, AbstractScreen.getKernel());
		
		return actor;
	}
	
	/**
	 * Inflates UI config by given configName to given screen
	 * @param screen screen config will be inflated to
	 * @param configName name of config
	 */
	public static void deployConfigToScreen(AbstractScreen screen, String configName) {
		deployConfigToScreen(screen, Actors.getInstance().getConfig(configName));
	}
	
	/**
	 * Делает {@link #insertConfigToActor(AbstractScreen, UiConfig, Group)} для screen.getStage().getRoot(), 
	 * после этого добавляет слушателя LayoutListener для screen.getStage().getRoot()
	 */
	public static void deployConfigToScreen(AbstractScreen screen, UiConfig config) {
		insertConfigToActor(screen, config, screen.getStage().getRoot());
		screen.addScreenEventListener(new LayoutListener(screen.getStage().getRoot()));
	}
	
	/**
	 * Делает {@link #insertConfigToActor(AbstractScreen, UiConfig, Group), после этого 
	 * добавляет слушателя LayoutListener для новосозданного актера. Изменяет размеры актера 
	 * до размеров актера, который добавляется (config)
	 */
	public static void deployConfigToGroup(final AbstractScreen screen, final UiConfig config, final Group parent) {
		insertConfigToActor(screen, config, parent);
		screen.addScreenEventListener(new LayoutListener(parent.getChildren().first()));
		parent.setSize(parent.getChildren().first().getWidth(), parent.getChildren().first().getHeight());
	}
	
	
	/**
	 * Рекурсивно инициализирует и добавляет в группу актера по указанном конфигурационному файлу
	 * @param screen экран, который будет сообщать актерам о изменениях размера
	 * @param config конфигурационный файл актера
	 * @param parent группа, куда будет добавлен созданный актер
	 */
	public static void insertConfigToActor(AbstractScreen screen, UiConfig config, Group parent) {
		Actor actor = addActor(screen, config, parent);
		
		for(UiConfig cfg : config.children) {
			insertConfigToActor(screen, cfg, (Group) actor);
		}
	}
}
