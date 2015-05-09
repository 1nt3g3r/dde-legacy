package ua.com.integer.dde.extension.ui.editor.property.imp.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.bool.BooleanEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.color.ColorEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.floatvalue.FloatEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.integer.IntegerEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.side.SideEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.size.SizeEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.string.StringEditPanel;
import ua.com.integer.dde.extension.ui.editor.property.edit.touchable.TouchableEditPanel;
import ua.com.integer.dde.extension.ui.size.Size;

public class CommonPropertiesPanel extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = 46432456083633266L;
	
	private UiConfig config;
	private ColorEditPanel colorPanel;
	private BooleanEditPanel visiblePanel;
	private TouchableEditPanel touchablePanel;
	private IntegerEditPanel rotationPanel;
	private BooleanEditPanel centerOrigin;
	private FloatEditPanel scaleXPanel;
	private FloatEditPanel scaleYPanel;
	private StringEditPanel namePanel;
	private SideEditPanel sideEditPanel;
	private SizeEditPanel widthPanel;
	private SizeEditPanel heightPanel;
	private SizeEditPanel xDistancePanel;
	private SizeEditPanel yDistancePanel;
	
	/**
	 * Create the panel.
	 */
	public CommonPropertiesPanel() {
		setBackground(Color.GRAY);
		setSize(new Dimension(310, 370));
		setMaximumSize(new Dimension(310, 370));
		setMinimumSize(new Dimension(310, 370));
		setPreferredSize(new Dimension(310, 370));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel sizeAndPositionTitlePanel = new JPanel();
		sizeAndPositionTitlePanel.setPreferredSize(new Dimension(300, 20));
		sizeAndPositionTitlePanel.setMinimumSize(new Dimension(300, 20));
		sizeAndPositionTitlePanel.setMaximumSize(new Dimension(300, 20));
		sizeAndPositionTitlePanel.setBackground(Color.GRAY);
		add(sizeAndPositionTitlePanel);
		sizeAndPositionTitlePanel.setLayout(new BoxLayout(sizeAndPositionTitlePanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		sizeAndPositionTitlePanel.add(horizontalStrut_1);
		
		JLabel lblPositionAndSize = new JLabel("Position and size:");
		lblPositionAndSize.setForeground(Color.GREEN);
		sizeAndPositionTitlePanel.add(lblPositionAndSize);
		
		sideEditPanel = new SideEditPanel();
		sideEditPanel.setPropertyChangedListener(this);
		sideEditPanel.setPropertyName("Layout side:");
		add(sideEditPanel);
		
		Component verticalStrut_8 = Box.createVerticalStrut(5);
		add(verticalStrut_8);
		
		widthPanel = new SizeEditPanel() {
			private static final long serialVersionUID = 6724166118512877478L;
			
			@Override
			public void setConfig(UiConfig config) {
				config.set("width", config.width.toString());
				super.setConfig(config);
			}

			@Override
			protected void writeChangesIntoConfig(Size size) {
				if (config != null) config.width = size;
			}
		};
		widthPanel.setPropertyChangedListener(this);
		widthPanel.setPropertyName("Width:");
		widthPanel.setUiPropertyName("width");
		add(widthPanel);
		
		Component verticalStrut_11 = Box.createVerticalStrut(2);
		add(verticalStrut_11);
		
		heightPanel = new SizeEditPanel() {
			private static final long serialVersionUID = -6379558281931524628L;

			@Override
			public void setConfig(UiConfig config) {
				config.set("height", config.height.toString());
				super.setConfig(config);
			}
			
			@Override
			protected void writeChangesIntoConfig(Size size) {
				if (config != null) config.height = size;
			}
		};
		heightPanel.setUiPropertyName("height");
		heightPanel.setPropertyChangedListener(this);
		heightPanel.setPropertyName("Height:");
		add(heightPanel);
		
		Component verticalStrut_10 = Box.createVerticalStrut(5);
		add(verticalStrut_10);
		
		xDistancePanel = new SizeEditPanel() {
			private static final long serialVersionUID = 7324665805756034886L;

			@Override
			public void setConfig(UiConfig config) {
				config.set("distanceX", config.horizontalDistance.toString());
				super.setConfig(config);
			}
			
			@Override
			protected void writeChangesIntoConfig(Size size) {
				if (config != null) config.horizontalDistance = size;
			}
		};
		xDistancePanel.setPropertyChangedListener(this);
		xDistancePanel.setPropertyName("X distance:");
		xDistancePanel.setUiPropertyName("distanceX");
		add(xDistancePanel);
		
		Component verticalStrut_12 = Box.createVerticalStrut(2);
		add(verticalStrut_12);
		
		yDistancePanel = new SizeEditPanel() {
			private static final long serialVersionUID = -9111936726252183702L;

			@Override
			public void setConfig(UiConfig config) {
				config.set("distanceY", config.verticalDistance.toString());
				super.setConfig(config);
			}
			
			@Override
			protected void writeChangesIntoConfig(Size size) {
				config.verticalDistance = size;
			}
		};
		yDistancePanel.setPropertyName("Y distance:");
		yDistancePanel.setUiPropertyName("distanceY");
		yDistancePanel.setPropertyChangedListener(this);
		add(yDistancePanel);
		
		Component verticalStrut_9 = Box.createVerticalStrut(10);
		add(verticalStrut_9);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(Color.GRAY);
		labelPanel.setPreferredSize(new Dimension(300, 20));
		labelPanel.setMinimumSize(new Dimension(300, 20));
		labelPanel.setMaximumSize(new Dimension(300, 20));
		add(labelPanel);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		labelPanel.add(horizontalStrut);
		
		JLabel lblNewLabel = new JLabel("Common actor properties:");
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		labelPanel.add(lblNewLabel);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		add(verticalStrut);
		
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
		add(namePanel);
		
		Component verticalStrut_1 = Box.createVerticalStrut(2);
		add(verticalStrut_1);
		
		visiblePanel = new BooleanEditPanel();
		visiblePanel.setPropertyChangedListener(this);
		visiblePanel.setUiPropertyName("actor-visible");
		visiblePanel.setPropertyName("Visible:");
		add(visiblePanel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(2);
		add(verticalStrut_2);
		
		touchablePanel = new TouchableEditPanel();
		touchablePanel.setPropertyChangedListener(this);
		touchablePanel.setUiPropertyName("actor-touchable");
		touchablePanel.setPropertyName("Touchable:");
		add(touchablePanel);
		
		Component verticalStrut_3 = Box.createVerticalStrut(2);
		add(verticalStrut_3);
		
		colorPanel = new ColorEditPanel();
		colorPanel.setPropertyChangedListener(this);
		colorPanel.setPropertyName("Tint color:");
		colorPanel.setUiPropertyName("actor-color");
		add(colorPanel);
		
		Component verticalStrut_4 = Box.createVerticalStrut(10);
		add(verticalStrut_4);
		
		scaleXPanel = new FloatEditPanel();
		scaleXPanel.setPropertyChangedListener(this);
		scaleXPanel.setUiPropertyName("actor-scale-x");
		scaleXPanel.setDefaultValue("1");
		scaleXPanel.setPropertyName("Scale X:");
		add(scaleXPanel);
		
		Component verticalStrut_5 = Box.createVerticalStrut(2);
		add(verticalStrut_5);
		
		scaleYPanel = new FloatEditPanel();
		scaleYPanel.setPropertyChangedListener(this);
		scaleYPanel.setUiPropertyName("actor-scale-y");
		scaleYPanel.setPropertyName("Scale Y:");
		scaleYPanel.setDefaultValue("1");
		add(scaleYPanel);
		
		Component verticalStrut_6 = Box.createVerticalStrut(10);
		add(verticalStrut_6);
		
		rotationPanel = new IntegerEditPanel();
		rotationPanel.setPropertyChangedListener(this);
		rotationPanel.setPropertyName("Rotation:");
		rotationPanel.setUiPropertyName("actor-rotation");
		add(rotationPanel);
		
		Component verticalStrut_7 = Box.createVerticalStrut(10);
		add(verticalStrut_7);
		
		centerOrigin = new BooleanEditPanel();
		centerOrigin.setPropertyChangedListener(this);
		centerOrigin.setPropertyName("Center origin:");
		centerOrigin.setUiPropertyName("actor-center-origin");
		add(centerOrigin);
	}
	
	public void setConfig(UiConfig config) {
		this.config = config;
		
		sideEditPanel.setConfig(config);
		namePanel.setConfig(config);
		visiblePanel.setConfig(config);
		touchablePanel.setConfig(config);
		colorPanel.setConfig(config);
		scaleXPanel.setConfig(config);
		scaleYPanel.setConfig(config);
		rotationPanel.setConfig(config);
		centerOrigin.setConfig(config);
		
		widthPanel.setConfig(config);
		heightPanel.setConfig(config);
		xDistancePanel.setConfig(config);
		yDistancePanel.setConfig(config);
	}

	@Override
	public void propertyChanged() {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateConfig();
	}
}
