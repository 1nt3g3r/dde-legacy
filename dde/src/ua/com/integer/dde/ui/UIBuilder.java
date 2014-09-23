package ua.com.integer.dde.ui;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.ui.builder.LabelBuilder;
import ua.com.integer.dde.ui.builder.TextureRegionActorBuilder;

public class UIBuilder {
	private LabelBuilder labelBuilder;
	private TextureRegionActorBuilder textureRegionActorBuilder;
	
	public UIBuilder(DDKernel kernel) {
		labelBuilder = new LabelBuilder();
		labelBuilder.kernel(kernel);
		
		textureRegionActorBuilder = new TextureRegionActorBuilder();
		textureRegionActorBuilder.kernel(kernel);
	}
	
	public LabelBuilder labelBuilder() {
		return labelBuilder;
	}
	
	public TextureRegionActorBuilder textureRegionActorBuilder() {
		return textureRegionActorBuilder;
	}
}
