package ua.com.integer.dde.res.screen;

import ua.com.integer.dde.res.ResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DDSplashScreen extends AbstractScreen {
	private Class<? extends AbstractScreen> nextScreen;
	private ResourceManager res = AbstractScreen.getKernel().getResourceManager();
	private boolean canGoToNextScreen;
	private Label progressLabel;
	private float lastPercent;
	
	public DDSplashScreen() {
		getConfig().backgroundColor = Color.DARK_GRAY;
		initScreenListener();
		initLabel();
	}
	
	public Label getProgressLabel() {
		return progressLabel;
	}
	
	private void initLabel() {
		progressLabel = getKernel().ui().labelBuilder().defaultFont(Gdx.graphics.getWidth() / 25).result();
		progressLabel.setText("Loading ... 0%");
		progressLabel.getStyle().fontColor = Color.WHITE;
		progressLabel.setPosition((Gdx.graphics.getWidth() - progressLabel.getPrefWidth())/2f, (Gdx.graphics.getHeight() - progressLabel.getHeight())/2f);
		addActor(progressLabel);
	}
	
	public void setNextScreen(Class<? extends AbstractScreen> nextScreen) {
		this.nextScreen = nextScreen;
	}
	
	private void initScreenListener() {
		addScreenEventListener(new ScreenListener() {
			@Override
			public void eventHappened(AbstractScreen screen, ScreenEvent event) {
				switch(event) {
				case SHOW:
					loadPercentChanged(0f);
					loadStep(0f);
					postTask(0.2f, new Runnable() {
						public void run() {
							res.loadAll();
							canGoToNextScreen = true;
						}
					});
					break;
				default:
					break;
				}
			}
		});
	}
	
	@Override
	public void render(float delta) {
		if (canGoToNextScreen) {
			if (res.loadStep()) {
				getKernel().showScreen(nextScreen);
			} else {
				float currentPercent = res.getLoadPercent();
				loadStep(currentPercent);
				
				if ((float)Math.abs(currentPercent - lastPercent) >= 0.01f) {
					lastPercent = currentPercent;
					loadPercentChanged(lastPercent);
					updateProgressLabel();
				}
			}
		}
		
		super.render(delta);
	}
	
	private void updateProgressLabel() {
		if (progressLabel != null && progressLabel.hasParent()) {
			progressLabel.setText("Loading ... " + (int) (lastPercent * 100f) + "%");
		}
	}
	
	public void loadStep(float percent) {
	}
	
	public void loadPercentChanged(float newPercent) {
		
	}
}
