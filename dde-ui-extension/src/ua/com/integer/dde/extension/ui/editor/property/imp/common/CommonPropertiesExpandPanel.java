package ua.com.integer.dde.extension.ui.editor.property.imp.common;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.ExpandEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue.FloatEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.side.SideEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.StringEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.touchable.TouchableEditPanel;

public class CommonPropertiesExpandPanel extends ExpandEditPanel {
	private static final long serialVersionUID = -937489155939773792L;
	
	private ColorEditPanel colorPanel;
	private BooleanEditPanel visiblePanel;
	private TouchableEditPanel touchablePanel;
	private IntegerEditPanel rotationPanel;
	private BooleanEditPanel centerOrigin;
	private FloatEditPanel scaleXPanel;
	private FloatEditPanel scaleYPanel;
	private StringEditPanel namePanel;
	private SideEditPanel cornerPanel;
	
	private SizeEditPanel widthPanel, heightPanel;
	
	private SizeEditPanel xDistancePanel, yDistancePanel;

	public CommonPropertiesExpandPanel() {
		setTitle("Common actor properties");
		
		namePanel = new StringEditPanel() {
			private static final long serialVersionUID = -5898502260265831252L;

			@Override
			protected void saveStringToConfig(String value) {
				config.name = value;
				
				EditorKernel.getInstance().getMainWindow().updateActorTree();
			}
			
			@Override
			protected void updateUIFromConfig() {
				if (config != null) {
					propertyValue.setText(config.name);
				}
			}
		};
		namePanel.setPropertyChangedListener(this);
		namePanel.setDefaultValue("actor");
		namePanel.setUiPropertyName("actor-name");
		namePanel.setPropertyName("Name:");
		
		visiblePanel = new BooleanEditPanel();
		visiblePanel.setPropertyChangedListener(this);
		visiblePanel.setUiPropertyName("actor-visible");
		visiblePanel.setPropertyName("Visible");
		
		touchablePanel = new TouchableEditPanel();
		touchablePanel.setPropertyChangedListener(this);
		touchablePanel.setUiPropertyName("actor-touchable");
		touchablePanel.setPropertyName("Touchable");
		
		colorPanel = new ColorEditPanel();
		colorPanel.setPropertyChangedListener(this);
		colorPanel.setPropertyName("Tint color:");
		colorPanel.setUiPropertyName("actor-color");
		
		scaleXPanel = new FloatEditPanel();
		scaleXPanel.setPropertyChangedListener(this);
		scaleXPanel.setUiPropertyName("actor-scale-x");
		scaleXPanel.setDefaultValue("1");
		scaleXPanel.setPropertyName("Scale X:");
		
		scaleYPanel = new FloatEditPanel();
		scaleYPanel.setPropertyChangedListener(this);
		scaleYPanel.setUiPropertyName("actor-scale-y");
		scaleYPanel.setPropertyName("Scale Y:");
		scaleYPanel.setDefaultValue("1");
		
		rotationPanel = new IntegerEditPanel();
		rotationPanel.setPropertyChangedListener(this);
		rotationPanel.setPropertyName("Rotation:");
		rotationPanel.setUiPropertyName("actor-rotation");
			
		centerOrigin = new BooleanEditPanel();
		centerOrigin.setPropertyChangedListener(this);
		centerOrigin.setPropertyName("Center origin:");
		centerOrigin.setUiPropertyName("actor-center-origin");
		
		cornerPanel = new SideEditPanel();
		cornerPanel.setPropertyName("Corner:");
		cornerPanel.setPropertyChangedListener(this);

		xDistancePanel = new SizeEditPanel() {
			private static final long serialVersionUID = 7324665805756034886L;

			@Override
			public void setConfig(UiConfig config) {
				config.set("distanceX", config.horizontalDistance.toString());
				super.setConfig(config);
			}
			
			@Override
			protected void writeChangesIntoConfig() {
				if (config != null) config.horizontalDistance = getCurrentSize();
			}
			
			@Override
			protected void sizeTypeChanged() {
				if (config != null) {
					config.horizontalDistance = getCurrentSize();

					Actor selectedActor = EditorKernel.editorScreen().getSelectedActor();
					if (selectedActor != null) {
						config.loadPositionFromActor(selectedActor);
					}

					setConfig(config);
					writeChangesIntoConfig();

					updateUiFromConfig();
				}
			}
		};
		xDistancePanel.setPropertyChangedListener(this);
		xDistancePanel.setPropertyName("X distance:");
		xDistancePanel.setUiPropertyName("distanceX");
		
		yDistancePanel = new SizeEditPanel() {
			private static final long serialVersionUID = -9111936726252183702L;

			@Override
			public void setConfig(UiConfig config) {
				config.set("distanceY", config.verticalDistance.toString());
				super.setConfig(config);
			}
			
			@Override
			protected void writeChangesIntoConfig() {
				config.verticalDistance = getCurrentSize();
			}
			
			@Override
			protected void sizeTypeChanged() {
				if (config != null) { 
					config.verticalDistance = getCurrentSize();
					
					Actor selectedActor = EditorKernel.editorScreen().getSelectedActor();
					if (selectedActor != null) {
						config.loadPositionFromActor(selectedActor);
					}
					
					setConfig(config);
					writeChangesIntoConfig();
					
					updateUiFromConfig();
				}
			}
		};
		yDistancePanel.setPropertyName("Y distance:");
		yDistancePanel.setUiPropertyName("distanceY");
		yDistancePanel.setPropertyChangedListener(this);
		
		widthPanel = new SizeEditPanel() {
			private static final long serialVersionUID = 6724166118512877478L;
			
			@Override
			public void setConfig(UiConfig config) {
				config.set("width", config.width.toString());
				super.setConfig(config);
			}

			@Override
			protected void writeChangesIntoConfig() {
				if (config != null) config.width = getCurrentSize();
			}
			
			@Override
			protected void sizeTypeChanged() {
				if (config != null) { 
					config.width = getCurrentSize();
					
					Actor selectedActor = EditorKernel.editorScreen().getSelectedActor();
					if (selectedActor != null) {
						config.width.loadFromValue(selectedActor, selectedActor.getWidth());
					}
					
					setConfig(config);
					writeChangesIntoConfig();
					
					updateUiFromConfig();
				}
			}
		};
		widthPanel.setPropertyChangedListener(this);
		widthPanel.setPropertyName("Width:");
		widthPanel.setUiPropertyName("width");
		
		heightPanel = new SizeEditPanel() {
			private static final long serialVersionUID = -6379558281931524628L;

			@Override
			public void setConfig(UiConfig config) {
				config.set("height", config.height.toString());
				super.setConfig(config);
			}
			
			@Override
			protected void writeChangesIntoConfig() {
				if (config != null) config.height = getCurrentSize();
			}
			
			@Override
			protected void sizeTypeChanged() {
				if (config != null) { 
					config.height = getCurrentSize();
					
					Actor selectedActor = EditorKernel.editorScreen().getSelectedActor();
					if (selectedActor != null) {
						config.height.loadFromValue(selectedActor, selectedActor.getHeight());
					}
					
					setConfig(config);
					writeChangesIntoConfig();
					
					updateUiFromConfig();
				}
			}
		};
		heightPanel.setUiPropertyName("height");
		heightPanel.setPropertyChangedListener(this);
		heightPanel.setPropertyName("Height:");
		
		setContent(
					namePanel,
					visiblePanel,
					touchablePanel,
				header("Size"),
					widthPanel,
					heightPanel,
				header("Position"),
					cornerPanel,
					xDistancePanel, 
					yDistancePanel,
				header("Scale"), 
					scaleXPanel, 
					scaleYPanel,
				header("Other properties"), 
					colorPanel, 
					rotationPanel, 
					centerOrigin
					);
	}
}
