package ua.com.integer.dde.extension.ui.actor.animated;

import javax.swing.SpinnerNumberModel;

import com.badlogic.gdx.utils.Scaling;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.ExpandEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue.FloatEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.scaling.ScalingEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.textureregion.TextureRegionEditPanel;

public class FrameAnimationPropertyEditor extends ExpandEditPanel {
	private static final long serialVersionUID = 6464795552264451196L;
	
	private TextureRegionEditPanel atlasChooser;
	private PlayModeEditPanel playMode;
	private FloatEditPanel frameDuration;
	
	private AlignEditPanel alignEditPanel;
	private ScalingEditPanel scalingEditPanel;
	
	public FrameAnimationPropertyEditor() {
		setTitle("Frame animation properties");
		
		atlasChooser = new TextureRegionEditPanel();
		atlasChooser.setPropertyName("Frames");
		atlasChooser.setUiPropertyName("frames");
		atlasChooser.setPropertyChangedListener(this);
		
		playMode = new PlayModeEditPanel();
		playMode.setUiPropertyName("animation-play-mode");
		playMode.setPropertyName("Play mode");
		playMode.setPropertyChangedListener(this);

		frameDuration = new FloatEditPanel();
		frameDuration.getValueSpinner().setModel(new SpinnerNumberModel(new Float(0.2f), null, null, new Float(0.1f)));
		frameDuration.setDefaultValue("0.2f");
		frameDuration.setUiPropertyName("frame-duration");
		frameDuration.setPropertyName("Frame duration");
		frameDuration.setPropertyChangedListener(this);
		
		alignEditPanel = new ua.com.integer.dde.extension.ui.editor.property.edit.text.AlignEditPanel();
		alignEditPanel.setPropertyChangedListener(this);
		alignEditPanel.setPropertyName("Image align:");
		alignEditPanel.setUiPropertyName("align");
		alignEditPanel.setPropertyChangedListener(this);
		
		scalingEditPanel = new ScalingEditPanel();
		scalingEditPanel.setDefaultValue(Scaling.fit.toString());
		scalingEditPanel.setPropertyChangedListener(this);
		scalingEditPanel.setUiPropertyName("scaling");
		scalingEditPanel.setPropertyName("Scaling:");
		scalingEditPanel.setPropertyChangedListener(this);
		
		setContent(
				header("Animation frames"),
					atlasChooser,
				header("Animation play mode"),
					playMode,
				header("Duration of one frame"),
					frameDuration,
				header("Scale and align"),
					scalingEditPanel,
					alignEditPanel);
	}

}
