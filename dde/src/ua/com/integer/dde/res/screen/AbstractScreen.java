package ua.com.integer.dde.res.screen;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.ui.helper.ActorHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AbstractScreen implements Screen {
	public static SpriteBatch batch;
	private static DDKernel kernel;

	private Stage stage;
	private ScreenConfig config;
	
	private Array<ActorHelper> componentHelpers;
	private Array<ScreenListener> screenListeners;
	
	public class BackPressListener extends InputListener {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
				notifyAboutEvent(ScreenEvent.BACK_OR_ESCAPE_PRESSED);
			}
			return super.keyDown(event, keycode);
		}
	}
	
	public AbstractScreen() {
		config = new ScreenConfig();
		config.screenName = getClass()+"";
		initialize();
	}
	
	public AbstractScreen(ScreenConfig config) {
		this.config = config;
		initialize();
	}
	
	public AbstractScreen(String name) {
		config = new ScreenConfig();
		config.screenName = name;
		screenListeners = new Array<ScreenListener>();
		initialize();
	}
	
	public static SpriteBatch getBatch() {
		return batch;
	}
	
	public static void setKernel(DDKernel runKernel) {
		kernel = runKernel;
	}
	
	private void initialize() {
		componentHelpers = new Array<ActorHelper>();
		screenListeners = new Array<ScreenListener>();
		stage = new Stage(new ScreenViewport(), getBatch());
		stage.getRoot().setSize(stage.getWidth(), stage.getHeight());
		stage.addListener(new BackPressListener());
	}
	
	public ScreenConfig getConfig() {
		return config;
	}
	
	public void addScreenEventListener(ScreenListener listener) {
		screenListeners.add(listener);
	}
	
	public void removeScreenEventListener(ScreenListener listener) {
		screenListeners.removeValue(listener, true);
	}
	
	public void clearScreenListeners() {
		screenListeners.clear();
	}
	
	public Array<ScreenListener> getScreenListeners() {
		return screenListeners;
	}

	public void setBackground(TextureRegion background) {
		config.background = background;
	}

	public String getScreenName() {
		return config.screenName;
	}

	public Stage getStage() {
		return stage;
	}
	
	public void addActorHelper(ActorHelper helper) {
		if (helper.getActor() != null) {
			if (helper.getActor().getParent() == null) {
				addActor(helper.getActor());
			}
		}
		helper.updateActor();
		componentHelpers.add(helper);
	}
	
	/**
	 * Add an actor to this screen
	 */
	public void addActor(Actor actor) {
		stage.addActor(actor);
	}
	
	/**
	 * Finds and returns an actor with selected name. If no actor found null will be returned. 
	 * Method searches in root group.
	 */
	public <T extends Actor> T findByName(String name) {
		return stage.getRoot().findActor(name);
	}

	/**
	 * Draws background image or fills screen background color if it need. After it renders stage. 
	 * You can override it and disable draw background if you want increase performance.
	 * @param delta
	 */
	@Override
	public void render(float delta) {
		clearScreen();
		if (config.needDrawBackgroundImage) {
			drawBackgroundImage();
		} 
		renderStage(delta);
	}
	
	protected void drawBackgroundImage() {
		batch.begin();
		batch.draw(config.background, 0, 0, stage.getWidth(), stage.getHeight());
		batch.end();
	}
	
	protected void clearScreen() {
		GL20 gl = Gdx.gl20;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glClearColor(config.backgroundColor.r, 
				config.backgroundColor.g,
				config.backgroundColor.b, 
				config.backgroundColor.a);
	}
	
	protected void renderStage(float delta) {
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		stage.getRoot().setSize(stage.getWidth(), stage.getHeight());
		
		updateHelpers();
		notifyAboutEvent(ScreenEvent.RESIZE);
	}
	
	private void updateHelpers() {
		for(ActorHelper locator : componentHelpers) {
			locator.updateActor();
		}
	}

	@Override
	public void show() {
		config.active = true;
		Gdx.input.setInputProcessor(stage);
		notifyAboutEvent(ScreenEvent.SHOW);
	}
	
	public boolean isActive() {
		return config.active;
	}

	@Override
	public void hide() {
		config.active = false;
		notifyAboutEvent(ScreenEvent.HIDE);
	}

	@Override
	public void pause() {
		config.active = false;
		notifyAboutEvent(ScreenEvent.PAUSE);
	}

	@Override
	public void resume() {
		config.active = true;
		notifyAboutEvent(ScreenEvent.RESUME);
	}

	@Override
	public void dispose() {
		for(Actor actor : getStage().getRoot().getChildren()) {
			if (actor instanceof Disposable) {
				((Disposable) actor).dispose();
			}
		}
		
		notifyAboutEvent(ScreenEvent.DISPOSE);
	}
	
	public static DDKernel getKernel() {
		return kernel;
	}
	
	public static TextureRegion getRegion(String pack, String name) {
		return kernel.getRegion(pack, name);
	}
	
	public static Sound getSound(String name) {
		return kernel.getSound(name);
	}
	
	public static Music getMusic(String name) {
		return kernel.getMusic(name);
	}
	
	public static BitmapFont getFont(String name, int size) {
		return kernel.getFont(name, size);
	}
	
	public void notifyAboutEvent(ScreenEvent event) {
		for(int i = 0; i < screenListeners.size; i++) {
			screenListeners.get(i).eventHappened(this, event);
		}
	}

	public static void disposeBatch() {
		batch.dispose();
		batch = null;
	}
	
	/**
	 * Исполняет указанную задачу через указанный промежуток времени. 
	 * Особенность - если экран будет спрятан до начала времени выполнения задачи, 
	 * то задача не будет выполнена
	 * @param delay задержка в секундах перед выполнением задания
	 * @param task задача
	 */
	public void postTask(float delay, Runnable task) {
		SequenceAction seqTask = Actions.sequence();
		seqTask.addAction(Actions.delay(delay));
		seqTask.addAction(Actions.run(task));
		getStage().addAction(seqTask);
	}
	
	/**
	 * Циклично исполняет переданную задачу
	 * @param interval
	 * @param task
	 */
	public void repeatTask(float interval, Runnable task) {
		getStage().addAction(Actions.forever(
				Actions.sequence(
						Actions.delay(interval),
						Actions.run(task))
				));
	}
	
	public void showScreen(Class<? extends AbstractScreen> screen) {
		getKernel().showScreen(screen);
	}
}
