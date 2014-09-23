package ua.com.integer.dde.extension.ui;

import ua.com.integer.dde.extension.ui.layout.LayoutListener;
import ua.com.integer.dde.extension.ui.property.util.actor.ActorUtils;
import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.res.screen.ScreenListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.ObjectMap;

public class Actors {
	public static final String ACTOR_EXTENSION = "actor";
	public static final String ACTOR_DIRECTORY = "data/dde-actors";

	private static Actors instance = new Actors();

	private String fullActorConfigPath;
	
	private ObjectMap<String, UiConfig> configs;
	
	private Actors() {
		DDKernel kernel = AbstractScreen.getKernel();
		
		fullActorConfigPath = kernel.getConfig().relativeDirectory + ACTOR_DIRECTORY + "/";
		
		configs = new ObjectMap<String, UiConfig>();
	}
	
	public static Actors getInstance() {
		return instance;
	}
	
	public UiConfig getConfig(String name) {
		if (configs.get(name) == null) {
			loadConfig(name);
		}
		
		return configs.get(name);
	}
	
	/**
	 * Удаляет актера и сопутствующего ему "помощника" - UiConfigurator, 
	 * если такой есть. Аналогично рекурсивно поступает со всеми его детьми. 
	 * Также удаляет, если есть LayoutListener для этого актера
	 * @param actor актер для удаления
	 * @param screen экран, с которого нужно удалить актера
	 */
	public static void removeActorFromScreen(Actor actor, AbstractScreen screen) {
		actor.remove();
		
		for(ScreenListener screenListener : screen.getScreenListeners()) {
			if (screenListener instanceof UiConfigurator) {
				UiConfigurator configurator = (UiConfigurator) screenListener;
				if (configurator.getTarget() == actor) {
					screen.getScreenListeners().removeValue(screenListener, true);
					break;
				}
			}
		}
		
		for(ScreenListener screenListener : screen.getScreenListeners()) {
			if (screenListener instanceof LayoutListener) {
				if (((LayoutListener) screenListener).getActor() == actor) {
					screen.getScreenListeners().removeValue(screenListener, true);
					break;
				}
			}
		}
		
		if (actor instanceof Group) {
			for(Actor child : ((Group) actor).getChildren()) {
				removeActorFromScreen(child, screen);
			}
		}
	}
	
	private void loadConfig(String name) {
		configs.put(name,
		UiConfig.fromFileHandle(Gdx.files.internal(fullActorConfigPath + name + "." +ACTOR_EXTENSION)));
	}
	
	public void deployToScreen(String actor, Class<? extends AbstractScreen> screen) {
		ActorUtils.deployConfigToScreen(AbstractScreen.getKernel().getScreen(screen), getConfig(actor));
	}
	
	public void deployToScreen(String actor, AbstractScreen screen) {
		ActorUtils.deployConfigToScreen(screen, getConfig(actor));
	}
}
