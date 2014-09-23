package ua.com.integer.dde.ui.builder;


import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.font.TTFFontManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Pools;

public class LabelBuilder {
	private TTFFontManager fm;
	private String text;
	private BitmapFont font;
	
	public LabelBuilder kernel(DDKernel kernel) {
		fm = kernel.getResourceManager().getManager(TTFFontManager.class);
		return this;
	}
	
	public LabelBuilder text(String text) {
		this.text = text;
		return this;
	}
	
	public LabelBuilder font(String name, int size) {
		font = fm.getFont(name, size);
		return this;
	}
	
	public LabelBuilder defaultFont(int size) {
		font = fm.getFont(size);
		return this;
	}
	
	public LabelBuilder localize(boolean localize) {
		return this;
	}
	
	public Label result() {
		LabelStyle style = new LabelStyle();
		style.font = font;
		
		Label toReturn = new Label(text, style);
		Pools.free(this);
		return toReturn;
	}
}
