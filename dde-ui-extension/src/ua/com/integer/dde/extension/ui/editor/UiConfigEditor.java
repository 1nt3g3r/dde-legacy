package ua.com.integer.dde.extension.ui.editor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.extension.ui.Side;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.property.PropertyUtils;
import ua.com.integer.dde.extension.ui.property.imp.common.CommonPropertySupporter;
import ua.com.integer.dde.extension.ui.size.SizeType;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import javax.swing.JTextField;

import java.awt.GridLayout;

public class UiConfigEditor extends JDialog {
	private static final long serialVersionUID = -2367147658963242752L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JRadioButton parentBottomLeftCorner;
	private JRadioButton childCenter;
	private JRadioButton parentCenter;
	private JRadioButton parentTopRightCorner;
	private JRadioButton childBottomLeftCorner;
	private JRadioButton childBottomRightCorner;
	private JRadioButton parentBottomRightCorner;
	private JRadioButton childTopRightCorner;
	private JRadioButton childTopLeftCorner;
	private JRadioButton parentTopLeftCorner;
	private JPanel widthPanel;
	private JPanel xDistancePanel;
	private JPanel yDistancePanel;
	@SuppressWarnings("rawtypes")
	private JComboBox xDistanceSizeType;
	private JLabel label_1;
	private JSpinner xDistanceValue;
	@SuppressWarnings("rawtypes")
	private JComboBox yDistanceSizeType;
	private JLabel label_2;
	private JSpinner yDistanceValue;
	private JPanel commonPropertiesPanel;
	private JPanel buttonPanel;
	private JButton refreshButton;
	private JCheckBox autorefreshConfig;
	private JSpinner heightValue;
	@SuppressWarnings("rawtypes")
	private JComboBox heightSizeType;
	private JSpinner widthValue;
	@SuppressWarnings("rawtypes")
	private JComboBox widthSizeType;
	
	private Array<ConfigChangedListener> configChangeListeners;
	
	private UiConfig config;
	private JTextField actorNameField;
	private JPanel customPropertiesPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiConfigEditor dialog = new UiConfigEditor();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UiConfigEditor() {
		addWindowListener(new CloseUiEditorDialogListener());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		configChangeListeners = new Array<ConfigChangedListener>();
		setTitle("DDE UI Config Editor");
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBounds(100, 100, 850, 650);
		
		JPanel parentPanel = new JPanel();
		parentPanel.setBackground(Color.GRAY);
		parentPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Select corner or center", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		widthPanel = new JPanel();
		widthPanel.setBackground(Color.GRAY);
		widthPanel.setBorder(new TitledBorder(null, "Width", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel heightPanel = new JPanel();
		heightPanel.setBackground(Color.GRAY);
		heightPanel.setBorder(new TitledBorder(null, "Height", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		xDistancePanel = new JPanel();
		xDistancePanel.setBackground(Color.GRAY);
		xDistancePanel.setBorder(new TitledBorder(null, "X distance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		yDistancePanel = new JPanel();
		yDistancePanel.setBackground(Color.GRAY);
		yDistancePanel.setBorder(new TitledBorder(null, "Y distance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		commonPropertiesPanel = new JPanel();
		commonPropertiesPanel.setBackground(Color.GRAY);
		commonPropertiesPanel.setBorder(new TitledBorder(null, "Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.GRAY);
		buttonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel actorNameLabel = new JLabel("Actor name:");
		
		actorNameField = new JTextField();
		actorNameField.setColumns(10);
		
		customPropertiesPanel = new JPanel();
		customPropertiesPanel.setBackground(Color.GRAY);
		customPropertiesPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Specific actor properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(buttonPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(parentPanel, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(actorNameLabel)
									.addGap(2)
									.addComponent(actorNameField, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
								.addComponent(xDistancePanel, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
								.addComponent(yDistancePanel, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
								.addComponent(heightPanel, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
								.addComponent(widthPanel, 0, 0, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(commonPropertiesPanel, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customPropertiesPanel, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(parentPanel, 0, 0, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(actorNameLabel)
								.addComponent(actorNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(xDistancePanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(yDistancePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(widthPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(heightPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(customPropertiesPanel, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
						.addComponent(commonPropertiesPanel, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		customPropertiesPanel.setLayout(new GridLayout(1, 1, 0, 0));
		commonPropertiesPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		autorefreshConfig = new JCheckBox("Autorefresh");
		autorefreshConfig.setBackground(Color.GRAY);
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new RefreshClickListener());
		refreshButton.setBackground(Color.LIGHT_GRAY);
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(autorefreshConfig)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(refreshButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(608))
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(autorefreshConfig)
						.addComponent(refreshButton))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		buttonPanel.setLayout(gl_buttonPanel);
		
		yDistanceSizeType = new JComboBox();
		yDistanceSizeType.addActionListener(new ChangeConfigActionListener());
		yDistanceSizeType.setBackground(Color.LIGHT_GRAY);
		yDistanceSizeType.setModel(new DefaultComboBoxModel(SizeType.values()));
		
		label_2 = new JLabel("*");
		label_2.setFont(new Font("Dialog", Font.BOLD, 20));
		
		yDistanceValue = new JSpinner();
		yDistanceValue.addChangeListener(new ChangeConfigValueChangeListener());
		yDistanceValue.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
		GroupLayout gl_yDistancePanel = new GroupLayout(yDistancePanel);
		gl_yDistancePanel.setHorizontalGroup(
			gl_yDistancePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_yDistancePanel.createSequentialGroup()
					.addComponent(yDistanceSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(yDistanceValue, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_yDistancePanel.setVerticalGroup(
			gl_yDistancePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_yDistancePanel.createSequentialGroup()
					.addGroup(gl_yDistancePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(yDistanceSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(yDistanceValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		yDistancePanel.setLayout(gl_yDistancePanel);
		
		xDistanceSizeType = new JComboBox();
		xDistanceSizeType.addActionListener(new ChangeConfigActionListener());
		xDistanceSizeType.setBackground(Color.LIGHT_GRAY);
		xDistanceSizeType.setModel(new DefaultComboBoxModel(SizeType.values()));
		
		label_1 = new JLabel("*");
		label_1.setFont(new Font("Dialog", Font.BOLD, 20));
		
		xDistanceValue = new JSpinner();
		xDistanceValue.addChangeListener(new ChangeConfigValueChangeListener());
		xDistanceValue.setForeground(Color.LIGHT_GRAY);
		xDistanceValue.setBackground(Color.LIGHT_GRAY);
		xDistanceValue.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
		GroupLayout gl_xDistancePanel = new GroupLayout(xDistancePanel);
		gl_xDistancePanel.setHorizontalGroup(
			gl_xDistancePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_xDistancePanel.createSequentialGroup()
					.addComponent(xDistanceSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(xDistanceValue, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_xDistancePanel.setVerticalGroup(
			gl_xDistancePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_xDistancePanel.createSequentialGroup()
					.addGroup(gl_xDistancePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(xDistanceSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(xDistanceValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		xDistancePanel.setLayout(gl_xDistancePanel);
		
		heightSizeType = new JComboBox();
		heightSizeType.addActionListener(new ChangeConfigActionListener());
		heightSizeType.setBackground(Color.LIGHT_GRAY);
		heightSizeType.setModel(new DefaultComboBoxModel(SizeType.values()));
		
		JLabel label = new JLabel("*");
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		
		heightValue = new JSpinner();
		heightValue.addChangeListener(new ChangeConfigValueChangeListener());
		heightValue.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
		GroupLayout gl_heightPanel = new GroupLayout(heightPanel);
		gl_heightPanel.setHorizontalGroup(
			gl_heightPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_heightPanel.createSequentialGroup()
					.addComponent(heightSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(heightValue, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_heightPanel.setVerticalGroup(
			gl_heightPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_heightPanel.createSequentialGroup()
					.addGroup(gl_heightPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(heightSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_heightPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(heightValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		heightPanel.setLayout(gl_heightPanel);
		
		widthSizeType = new JComboBox();
		widthSizeType.addActionListener(new ChangeConfigActionListener());
		widthSizeType.setBackground(Color.LIGHT_GRAY);
		widthSizeType.setModel(new DefaultComboBoxModel(SizeType.values()));
		
		JLabel lblNewLabel = new JLabel("*");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		widthValue = new JSpinner();
		widthValue.addChangeListener(new ChangeConfigValueChangeListener());
		widthValue.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
		GroupLayout gl_widthPanel = new GroupLayout(widthPanel);
		gl_widthPanel.setHorizontalGroup(
			gl_widthPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_widthPanel.createSequentialGroup()
					.addComponent(widthSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(widthValue, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_widthPanel.setVerticalGroup(
			gl_widthPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_widthPanel.createSequentialGroup()
					.addGroup(gl_widthPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_widthPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(widthSizeType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addComponent(widthValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		widthPanel.setLayout(gl_widthPanel);
		
		JPanel childPanel = new JPanel();
		childPanel.setVisible(false);
		childPanel.setBorder(new TitledBorder(null, "Target corner", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		childPanel.setBounds(34, 150, 260, 72);
		childPanel.setBackground(Color.LIGHT_GRAY);
		
		parentTopLeftCorner = new JRadioButton("");
		parentTopLeftCorner.addActionListener(new ChangeConfigActionListener());
		parentTopLeftCorner.setBackground(Color.GRAY);
		buttonGroup_1.add(parentTopLeftCorner);
		parentTopLeftCorner.setBounds(0, 0, 21, 21);
		parentPanel.setLayout(null);
		parentPanel.add(childPanel);
		childPanel.setLayout(null);
		
		childTopLeftCorner = new JRadioButton("");
		childTopLeftCorner.addActionListener(new ChangeConfigActionListener());
		buttonGroup.add(childTopLeftCorner);
		childTopLeftCorner.setBackground(Color.LIGHT_GRAY);
		childTopLeftCorner.setBounds(0, 0, 21, 23);
		childPanel.add(childTopLeftCorner);
		
		childTopRightCorner = new JRadioButton("");
		childTopRightCorner.addActionListener(new ChangeConfigActionListener());
		buttonGroup.add(childTopRightCorner);
		childTopRightCorner.setBackground(Color.LIGHT_GRAY);
		childTopRightCorner.setBounds(239, 0, 21, 23);
		childPanel.add(childTopRightCorner);
		
		childBottomLeftCorner = new JRadioButton("");
		childBottomLeftCorner.addActionListener(new ChangeConfigActionListener());
		buttonGroup.add(childBottomLeftCorner);
		childBottomLeftCorner.setBackground(Color.LIGHT_GRAY);
		childBottomLeftCorner.setBounds(0, 49, 21, 23);
		childPanel.add(childBottomLeftCorner);
		
		childCenter = new JRadioButton("");
		childCenter.addActionListener(new ChangeConfigActionListener());
		buttonGroup.add(childCenter);
		childCenter.setBackground(Color.LIGHT_GRAY);
		childCenter.setBounds(119, 25, 21, 23);
		childPanel.add(childCenter);
		
		childBottomRightCorner = new JRadioButton("");
		childBottomRightCorner.addActionListener(new ChangeConfigActionListener());
		buttonGroup.add(childBottomRightCorner);
		childBottomRightCorner.setBackground(Color.LIGHT_GRAY);
		childBottomRightCorner.setBounds(239, 49, 21, 23);
		childPanel.add(childBottomRightCorner);
		parentPanel.add(parentTopLeftCorner);
		
		parentTopRightCorner = new JRadioButton("");
		parentTopRightCorner.addActionListener(new ChangeConfigActionListener());
		parentTopRightCorner.setBackground(Color.GRAY);
		buttonGroup_1.add(parentTopRightCorner);
		parentTopRightCorner.setBounds(302, 0, 21, 23);
		parentPanel.add(parentTopRightCorner);
		
		parentBottomLeftCorner = new JRadioButton("");
		parentBottomLeftCorner.addActionListener(new ChangeConfigActionListener());
		parentBottomLeftCorner.setBackground(Color.GRAY);
		buttonGroup_1.add(parentBottomLeftCorner);
		parentBottomLeftCorner.setBounds(0, 210, 21, 23);
		parentPanel.add(parentBottomLeftCorner);
		
		parentBottomRightCorner = new JRadioButton("");
		parentBottomRightCorner.addActionListener(new ChangeConfigActionListener());
		parentBottomRightCorner.setBackground(Color.GRAY);
		buttonGroup_1.add(parentBottomRightCorner);
		parentBottomRightCorner.setBounds(302, 210, 21, 23);
		parentPanel.add(parentBottomRightCorner);
		
		parentCenter = new JRadioButton("");
		parentCenter.addActionListener(new ChangeConfigActionListener());
		parentCenter.setBackground(Color.GRAY);
		buttonGroup_1.add(parentCenter);
		parentCenter.setBounds(149, 105, 21, 23);
		parentPanel.add(parentCenter);
		getContentPane().setLayout(groupLayout);

	}
	
	public void addConfigChangeListener(ConfigChangedListener listener) {
		configChangeListeners.add(listener);
	}
	
	public void notifyConfigChangeListeners() {
		if (config == null) {
			return;
		}
		UiConfig config = getUpdatedConfig();
		
		for(ConfigChangedListener listener : configChangeListeners) {
			listener.configChanged(config);
		}
	}
	
	private UiConfig getUpdatedConfig() {
		setupParentCorner(config);
		setupSize(config);
		setupDistances(config);
		
		config.name = actorNameField.getText();
		return config;
	}
	
	private void setupParentCorner(UiConfig config) {
		if (parentBottomLeftCorner.isSelected()) {
			config.parentCorner = Side.BOTTOM_LEFT;
		} else if (parentBottomRightCorner.isSelected()) {
			config.parentCorner = Side.BOTTOM_RIGHT;
		} else if (parentTopLeftCorner.isSelected()) {
			config.parentCorner = Side.TOP_LEFT;
		} else if (parentTopRightCorner.isSelected()) {
			config.parentCorner = Side.TOP_RIGHT;
		} else if (parentCenter.isSelected()) {
			config.parentCorner = Side.CENTER;
		}
		
		config.targetCorner = config.parentCorner;
	}
	
	private void setupSize(UiConfig config) {
		config.width.setType(SizeType.valueOf(widthSizeType.getSelectedItem().toString()));
		config.width.setSizeValue(Float.parseFloat(widthValue.getValue().toString()));
		
		config.height.setType(SizeType.valueOf(heightSizeType.getSelectedItem().toString()));
		config.height.setSizeValue(Float.parseFloat(heightValue.getValue().toString()));
	}
	
	private void setupDistances(UiConfig config) {
		config.horizontalDistance.setType(SizeType.valueOf(xDistanceSizeType.getSelectedItem().toString()));
		config.horizontalDistance.setSizeValue(Float.parseFloat(xDistanceValue.getValue().toString()));
		
		config.verticalDistance.setType(SizeType.valueOf(yDistanceSizeType.getSelectedItem().toString()));
		config.verticalDistance.setSizeValue(Float.parseFloat(yDistanceValue.getValue().toString()));
	}
	
	class ChangeConfigActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			notifyListenersIfAutorefreshEnabled();
		}
	}
	
	private void notifyListenersIfAutorefreshEnabled() {
		if (autorefreshConfig.isSelected()) {
			notifyConfigChangeListeners();
		}
	}
	
	class ChangeConfigValueChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			notifyListenersIfAutorefreshEnabled();
		}
	}
	
	class RefreshClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			notifyConfigChangeListeners();
		}
	}
	
	public void setConfig(UiConfig config, Actor actor) {
		autorefreshConfig.setSelected(false);

		this.config = config;
		
		loadParentCorner(config);
		loadSize(config);
		loadDistances(config);
		
		actorNameField.setText(config.name);
		
		try {
			customPropertiesPanel.removeAll();
			customPropertiesPanel.add(PropertyUtils.getSupporter(config.widgetType).createSetupPanel(config, actor, this));
		} catch (Exception e) {
			customPropertiesPanel.add(new JLabel("Custom properties are missing!"));
		}
		
		commonPropertiesPanel.removeAll();
		commonPropertiesPanel.add(CommonPropertySupporter.getInstance().createSetupPanel(config, actor, this));
		
		autorefreshConfig.setSelected(true);
	}
	
	private void loadParentCorner(UiConfig config) {
		switch(config.parentCorner) {
		case BOTTOM_LEFT:
			parentBottomLeftCorner.setSelected(true);
			break;
		case BOTTOM_RIGHT:
			parentBottomRightCorner.setSelected(true);
			break;
		case CENTER:
			parentCenter.setSelected(true);
			break;
		case TOP_LEFT:
			parentTopLeftCorner.setSelected(true);
			break;
		case TOP_RIGHT:
			parentTopRightCorner.setSelected(true);
			break;
		}
	}
	
	private void loadSize(UiConfig config) {
		widthSizeType.setSelectedItem(config.width.getSizeType());
		widthValue.setValue(config.width.getSizeValue());
		
		heightSizeType.setSelectedItem(config.height.getSizeType());
		heightValue.setValue(config.height.getSizeValue());
	}
	
	private void loadDistances(UiConfig config) {
		xDistanceSizeType.setSelectedItem(config.horizontalDistance.getSizeType());
		xDistanceValue.setValue(config.horizontalDistance.getSizeValue());
		
		yDistanceSizeType.setSelectedItem(config.verticalDistance.getSizeType());
		yDistanceValue.setValue(config.verticalDistance.getSizeValue());
	}
	
	class CloseUiEditorDialogListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			notifyConfigChangeListeners();
		}
	}
	public JPanel getPropertyPanel() {
		return commonPropertiesPanel;
	}
	public JPanel getCommonPropertiesPanel() {
		return customPropertiesPanel;
	}
}
