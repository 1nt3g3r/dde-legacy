package ua.com.integer.dde.extension.ui.editor.property.edit.drawable;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.startpanel.image.ImageSelectionListener;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;

public class DrawableEditor extends JDialog implements PropertyEditComponent {
	private static final long serialVersionUID = 5274312930603228302L;
	private JPanel ninePatchSetupPanel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton textureRegionDrawableButton;
	private JRadioButton ninePatchDrawableButton;
	private JRadioButton tiledDrawableButton;
	private JSpinner bottomSpinner;
	private JSpinner rightSpinner;
	private JSpinner leftSpinner;
	private JSpinner topSpinner;
	
	private PropertyChangeListener listener;
	private UiConfig config;
	private String uiPropertyName;
	private JPanel baseDrawableSetupPanel;
	private JLabel lblMinWidth;
	private JLabel lblLeftWidth;
	private JSpinner leftWidth;
	private JLabel lblRightWidth;
	private JSpinner rightWidth;
	private JLabel lblTopWidth;
	private JSpinner topHeight;
	private JLabel lblBottomHeight;
	private JSpinner bottomHeight;
	private JSpinner minHeight;
	private JSpinner minWidth;
	private JCheckBox setupSizeBackground;

	/**
	 * Create the panel.
	 */
	public DrawableEditor() {
		getContentPane().setBackground(Color.GRAY);
		
		textureRegionDrawableButton = new JRadioButton("TextureRegion drawable");
		textureRegionDrawableButton.addActionListener(new SelectTypeActionListener());
		textureRegionDrawableButton.setSelected(true);
		buttonGroup.add(textureRegionDrawableButton);
		textureRegionDrawableButton.setBackground(Color.GRAY);
		
		tiledDrawableButton = new JRadioButton("Tiled drawable");
		tiledDrawableButton.addActionListener(new SelectTypeActionListener());
		buttonGroup.add(tiledDrawableButton);
		tiledDrawableButton.setBackground(Color.GRAY);
		
		ninePatchDrawableButton = new JRadioButton("Nine patch drawable");
		ninePatchDrawableButton.addActionListener(new SelectTypeActionListener());
		buttonGroup.add(ninePatchDrawableButton);
		ninePatchDrawableButton.setBackground(Color.GRAY);
		
		JButton selectImageButton = new JButton("Select image...");
		selectImageButton.addActionListener(new DrawableImageSelectListener());
		selectImageButton.setBackground(Color.LIGHT_GRAY);
		
		ninePatchSetupPanel = new JPanel();
		ninePatchSetupPanel.setVisible(false);
		ninePatchSetupPanel.setBackground(Color.GRAY);
		ninePatchSetupPanel.setBorder(new TitledBorder(null, "Setup nine patch drawable", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		baseDrawableSetupPanel = new JPanel();
		baseDrawableSetupPanel.setVisible(false);
		baseDrawableSetupPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Setup base drawable size", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		baseDrawableSetupPanel.setBackground(Color.GRAY);
		
		setupSizeBackground = new JCheckBox("Setup size");
		setupSizeBackground.addActionListener(new SetupSizeListener());
		setupSizeBackground.setBackground(Color.GRAY);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(setupSizeBackground)
						.addComponent(selectImageButton, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textureRegionDrawableButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tiledDrawableButton, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ninePatchDrawableButton, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
						.addComponent(ninePatchSetupPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
						.addComponent(baseDrawableSetupPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(selectImageButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textureRegionDrawableButton)
						.addComponent(tiledDrawableButton)
						.addComponent(ninePatchDrawableButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(setupSizeBackground)
					.addGap(7)
					.addComponent(baseDrawableSetupPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ninePatchSetupPanel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(26))
		);
		
		lblMinWidth = new JLabel("Min width:");
		
		minWidth = new JSpinner();
		minWidth.addChangeListener(new SpinnerListener());
		
		JLabel lblMinHeight = new JLabel("Min height:");
		
		minHeight = new JSpinner();
		minHeight.addChangeListener(new SpinnerListener());
		
		lblLeftWidth = new JLabel("Left width:");
		
		leftWidth = new JSpinner();
		leftWidth.addChangeListener(new SpinnerListener());
		
		lblRightWidth = new JLabel("Right width:");
		
		rightWidth = new JSpinner();
		rightWidth.addChangeListener(new SpinnerListener());
		
		lblTopWidth = new JLabel("Top height:");
		
		topHeight = new JSpinner();
		topHeight.addChangeListener(new SpinnerListener());
		
		lblBottomHeight = new JLabel("Bottom height:");
		
		bottomHeight = new JSpinner();
		bottomHeight.addChangeListener(new SpinnerListener());
		
		GroupLayout gl_baseDrawableSetupPanel = new GroupLayout(baseDrawableSetupPanel);
		gl_baseDrawableSetupPanel.setHorizontalGroup(
			gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_baseDrawableSetupPanel.createSequentialGroup()
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMinWidth)
						.addComponent(lblMinHeight))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(minHeight)
						.addComponent(minWidth, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLeftWidth)
						.addComponent(lblRightWidth))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(rightWidth)
						.addComponent(leftWidth, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTopWidth)
						.addComponent(lblBottomHeight))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(topHeight, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(bottomHeight, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_baseDrawableSetupPanel.setVerticalGroup(
			gl_baseDrawableSetupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_baseDrawableSetupPanel.createSequentialGroup()
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMinWidth)
						.addComponent(minWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLeftWidth)
						.addComponent(leftWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTopWidth)
						.addComponent(topHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_baseDrawableSetupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMinHeight)
						.addComponent(minHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRightWidth)
						.addComponent(rightWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBottomHeight)
						.addComponent(bottomHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		baseDrawableSetupPanel.setLayout(gl_baseDrawableSetupPanel);
		
		JLabel lblLeft = new JLabel("Left:");
		
		leftSpinner = new JSpinner();
		leftSpinner.addChangeListener(new SpinnerListener());
		
		JLabel lblRight = new JLabel("Right:");
		
		rightSpinner = new JSpinner();
		rightSpinner.addChangeListener(new SpinnerListener());
		
		JLabel lblTop = new JLabel("Top:");
		
		JLabel lblBottom = new JLabel("Bottom:");
		
		topSpinner = new JSpinner();
		topSpinner.addChangeListener(new SpinnerListener());
		
		bottomSpinner = new JSpinner();
		bottomSpinner.addChangeListener(new SpinnerListener());
		GroupLayout gl_ninePatchSetupPanel = new GroupLayout(ninePatchSetupPanel);
		gl_ninePatchSetupPanel.setHorizontalGroup(
			gl_ninePatchSetupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ninePatchSetupPanel.createSequentialGroup()
					.addGroup(gl_ninePatchSetupPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_ninePatchSetupPanel.createSequentialGroup()
							.addComponent(lblLeft, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(leftSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_ninePatchSetupPanel.createSequentialGroup()
							.addComponent(lblRight)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rightSpinner)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_ninePatchSetupPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblBottom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblTop, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_ninePatchSetupPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(bottomSpinner)
						.addComponent(topSpinner, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
					.addContainerGap(155, Short.MAX_VALUE))
		);
		gl_ninePatchSetupPanel.setVerticalGroup(
			gl_ninePatchSetupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ninePatchSetupPanel.createSequentialGroup()
					.addGroup(gl_ninePatchSetupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLeft)
						.addComponent(leftSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTop)
						.addComponent(topSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_ninePatchSetupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRight)
						.addComponent(rightSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBottom)
						.addComponent(bottomSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		ninePatchSetupPanel.setLayout(gl_ninePatchSetupPanel);
		getContentPane().setLayout(groupLayout);
		setTitle("Drawable editor");
		
		pack();
	}
	
	class DrawableImageSelectListener extends ImageSelectionListener {

		@Override
		public void imageSelected() {
			String result = getPack() + ";" + getRegion();
			
			config.set(uiPropertyName, result);
			
			updateConfigFromUi();
		}
	}
	
	class SelectTypeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ninePatchSetupPanel.setVisible(ninePatchDrawableButton.isSelected());
			pack();
			
			updateConfigFromUi();
		}
	}
	
	public static void main(String[] args) {
		new DrawableEditor().setVisible(true);
	}

	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		
		updateUiFromConfig();
	}
	
	@Override
	public void setUiPropertyName(String propertyName) {
		uiPropertyName = propertyName;
		
		updateUiFromConfig();
	}
	
	private void updateUiFromConfig() {
		if (config != null && uiPropertyName != null) {
			config.print();
			String type = config.get(uiPropertyName + "-type", "texture-region-drawable");
			
			int left = getInt(uiPropertyName + "-9-left");
			int right = getInt(uiPropertyName + "-9-right");
			int top = getInt(uiPropertyName + "-9-top");
			int bottom = getInt(uiPropertyName + "-9-bottom");
			
			int minWidth = getInt(uiPropertyName + "-min-width", 10);
			int minHeight = getInt(uiPropertyName + "-min-height", 10);
			int leftWidth = getInt(uiPropertyName + "-left-width");
			int rightWidth = getInt(uiPropertyName + "-right-width");
			int topHeight = getInt(uiPropertyName + "-top-height");
			int bottomHeight = getInt(uiPropertyName + "-bottom-height");
			
			if (config.get("setup-size", "false").equals("true")) {
				this.minWidth.setValue(minWidth);
				this.minHeight.setValue(minHeight);
				this.leftWidth.setValue(leftWidth);
				this.rightWidth.setValue(rightWidth);
				this.topHeight.setValue(topHeight);
				this.bottomHeight.setValue(bottomHeight);
				
				setupSizeBackground.setSelected(true);
				baseDrawableSetupPanel.setVisible(true);
			} else {
				setupSizeBackground.setSelected(false);
				baseDrawableSetupPanel.setVisible(false);
			}
			
			if (type == "texture-region-drawable") {
				textureRegionDrawableButton.setSelected(true);
			} else if (type == "tiled-region-drawable") {
				tiledDrawableButton.setSelected(true);
			} else if (type == "9patch-region-drawable") {
				ninePatchDrawableButton.setSelected(true);
				
				ninePatchSetupPanel.setVisible(true);
				
				leftSpinner.setValue(left);
				rightSpinner.setValue(right);
				topSpinner.setValue(top);
				bottomSpinner.setValue(bottom);
			}
		}
		
		pack();
	}
	
	@Override
	public void setPropertyName(String propertyName) {
	}
	
	
	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
	}
	
	@Override
	public String getDefaultValue() {
		return "";
	}
	
	class SetupSizeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			baseDrawableSetupPanel.setVisible(setupSizeBackground.isSelected());
			updateConfigFromUi();
			pack();
		}
	}
	
	class SpinnerListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			updateConfigFromUi();
		}
	}
	
	private void updateConfigFromUi() {
		if (config != null && uiPropertyName != null) {
			if (textureRegionDrawableButton.isSelected()) {
				config.set(uiPropertyName + "-type", "texture-region-drawable");
			} else if (tiledDrawableButton.isSelected()) {
				config.set(uiPropertyName + "-type", "tiled-region-drawable");
			} else if (ninePatchDrawableButton.isSelected()) {
				config.set(uiPropertyName + "-type", "9patch-region-drawable");
				
				config.set(uiPropertyName + "-9-left", leftSpinner.getValue().toString());
				config.set(uiPropertyName + "-9-right", rightSpinner.getValue().toString());
				config.set(uiPropertyName + "-9-top", topSpinner.getValue().toString());
				config.set(uiPropertyName + "-9-bottom", bottomSpinner.getValue().toString());
			}
			
			config.set("setup-size", setupSizeBackground.isSelected() + "");
			if (setupSizeBackground.isSelected()) {
				config.set(uiPropertyName + "-min-width", minWidth.getValue().toString());
				config.set(uiPropertyName + "-min-height", minHeight.getValue().toString());
				config.set(uiPropertyName + "-left-width", leftWidth.getValue().toString());
				config.set(uiPropertyName + "-right-width", rightWidth.getValue().toString());
				config.set(uiPropertyName + "-top-height", topHeight.getValue().toString());
				config.set(uiPropertyName + "-bottom-height", bottomHeight.getValue().toString());
			}
			
			if (listener != null) listener.propertyChanged();
		}
	}
	
	private int getInt(String name) {
		return getInt(name, 0);
	}
	
	private int getInt(String name, int defValue) {
		return (int) Float.parseFloat(config.get(name, defValue+""));

	}
}
