package ua.com.integer.dde.extension.ui.actor.animation.spine;

import javax.swing.SpinnerNumberModel;

import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue.FloatEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.imp.ExpandableConfigEditor;

public class SpineAnimationPropertyEditor extends ExpandableConfigEditor {
	private static final long serialVersionUID = 4313828412941313458L;
	
	private SpineConfigListEditPanel animationList;
	private AnimationNamesEditPanel animationName;
	private FloatEditPanel timeScalePanel;

	class AnimationNameChangeListener implements PropertyChangeListener {
		@Override
		public void propertyChanged() {
			animationName.updateUIFromConfig();
			SpineAnimationPropertyEditor.this.propertyChanged();
		}
	}
	
	public SpineAnimationPropertyEditor() {
		setTitle("Spine animation properties");
		
		animationList = new SpineConfigListEditPanel() {
			private static final long serialVersionUID = 7121766988627542408L;
			public void setConfig(ua.com.integer.dde.extension.ui.UiConfig config) {
				super.setConfig(config);
				
				if (animationName != null) {
					animationName.setConfig(config);
					animationName.updateUIFromConfig();
				}
			};
		};
		animationList.setUiPropertyName("spine-animation-config");
		animationList.setLabel("Json config:");
		animationList.setPropertyChangedListener(new AnimationNameChangeListener());
		
		animationName = new AnimationNamesEditPanel();
		animationName.setUiPropertyName("spine-animation-name");
		animationName.setLabel("Animation:");
		animationName.setPropertyChangedListener(this);
		
		timeScalePanel = new FloatEditPanel();
		timeScalePanel.getValueSpinner().setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.1f)));

		timeScalePanel.setDefaultValue("1.0");
		timeScalePanel.setPropertyName("Time scale:");
		timeScalePanel.setUiPropertyName("spine-animation-time-scale");
		timeScalePanel.setPropertyChangedListener(this);
		
		setContent( header("Config to use"),
						animationList,
					header("Animation params"),
						animationName,
						timeScalePanel,
					header("Animation flip"),
						b().bool().setup("Flip X", "spine-animation-flip-x", false).build(),
						b().bool().setup("Flip Y", "spine-animation-flip-y", false).build());
	}
}
