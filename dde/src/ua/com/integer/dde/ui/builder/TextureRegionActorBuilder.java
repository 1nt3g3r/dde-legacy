package ua.com.integer.dde.ui.builder;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.graphics.TextureManager;
import ua.com.integer.dde.ui.actor.TextureRegionActor;

public class TextureRegionActorBuilder {
	private TextureManager tm;
	private String pack;
	private String region;
	private float width;
	private float height;
	
	public TextureRegionActorBuilder kernel(DDKernel kernel) {
		tm = kernel.getResourceManager().getManager(TextureManager.class);
		return this;
	}
	
	public TextureRegionActorBuilder pack(String pack) {
		this.pack = pack;
		return this;
	}
	
	public TextureRegionActorBuilder region(String region) {
		this.region = region;
		return this;
	}
	
	public TextureRegionActorBuilder width(float width) {
		this.width = width;
		return this;
	}
	
	public TextureRegionActorBuilder height(float height) {
		this.height = height;
		return this;
	}
	
	public TextureRegionActor result() {
		TextureRegionActor actor = new TextureRegionActor();
		actor.setRegion(tm, pack, region);
		actor.setSize(width, height);
		return actor;
	}
}
