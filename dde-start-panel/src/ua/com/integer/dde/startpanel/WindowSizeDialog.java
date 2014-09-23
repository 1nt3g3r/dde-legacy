package ua.com.integer.dde.startpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


public class WindowSizeDialog extends JDialog {
	private static final long serialVersionUID = 7263099860580688447L;
	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField resWidth;
	private JTextField resHeight;
	private JPanel customResolutionPanel;
	private JPanel predefinedResolutionPanel;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JRadioButton rdbtnCustomResolution;
	private JRadioButton portRadioButton;
	private JComboBox<String> comboBox;
	private JRadioButton landRadionButton;
	private JRadioButton rdbtnStandardResolution;
	private JButton okButton;
	private JCheckBox scaleBox;
	private JComboBox<String> scaleComboBox;
	private JPanel scaleComboboxPanel;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JRadioButton rdbtnFullscreenMode;
	private JRadioButton rdbtnWindow;
	private JPanel orientationPanel;
	private JPanel resTypePanel;
	private JPanel scalePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			final WindowSizeDialog dialog = new WindowSizeDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.getOkButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dialog.saveSettings();
					dialog.dispose();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WindowSizeDialog() {
		setResizable(false);
		setTitle("Select screen size");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				rdbtnWindow = new JRadioButton("Window mode");
				rdbtnWindow.setBackground(Color.LIGHT_GRAY);
				rdbtnWindow.addActionListener(new FullScreenModeListener());
				rdbtnWindow.setSelected(true);
				buttonGroup_2.add(rdbtnWindow);
				panel.add(rdbtnWindow);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				panel.add(horizontalGlue);
			}
			{
				rdbtnFullscreenMode = new JRadioButton("Fullscreen mode");
				rdbtnFullscreenMode.setBackground(Color.LIGHT_GRAY);
				rdbtnFullscreenMode.addActionListener(new FullScreenModeListener());
				buttonGroup_2.add(rdbtnFullscreenMode);
				panel.add(rdbtnFullscreenMode);
			}
		}
		{
			Component verticalStrut = Box.createVerticalStrut(12);
			contentPanel.add(verticalStrut);
		}
		{
			orientationPanel = new JPanel();
			orientationPanel.setBackground(Color.LIGHT_GRAY);
			orientationPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(orientationPanel);
			orientationPanel.setLayout(new BoxLayout(orientationPanel, BoxLayout.X_AXIS));
			{
				landRadionButton = new JRadioButton("Landscape");
				landRadionButton.setBackground(Color.LIGHT_GRAY);
				landRadionButton.setSelected(true);
				buttonGroup.add(landRadionButton);
				orientationPanel.add(landRadionButton);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				orientationPanel.add(horizontalGlue);
			}
			{
				portRadioButton = new JRadioButton("Portrait");
				portRadioButton.setBackground(Color.LIGHT_GRAY);
				buttonGroup.add(portRadioButton);
				orientationPanel.add(portRadioButton);
			}
		}
		{
			Component verticalStrut = Box.createVerticalStrut(12);
			contentPanel.add(verticalStrut);
		}
		{
			resTypePanel = new JPanel();
			resTypePanel.setBackground(Color.LIGHT_GRAY);
			resTypePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(resTypePanel);
			resTypePanel.setLayout(new BoxLayout(resTypePanel, BoxLayout.Y_AXIS));
			{
				Component verticalStrut = Box.createVerticalStrut(20);
				resTypePanel.add(verticalStrut);
			}
			{
				JPanel resolutionTypePanel = new JPanel();
				resolutionTypePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				resolutionTypePanel.setBackground(Color.LIGHT_GRAY);
				resTypePanel.add(resolutionTypePanel);
				resolutionTypePanel.setLayout(new BoxLayout(resolutionTypePanel, BoxLayout.X_AXIS));
				{
					rdbtnStandardResolution = new JRadioButton("Standard resolution");
					rdbtnStandardResolution.setBackground(Color.LIGHT_GRAY);
					rdbtnStandardResolution.setSelected(true);
					rdbtnStandardResolution.addActionListener(new ShowPredefinedResolutionListener(true));
					buttonGroup_1.add(rdbtnStandardResolution);
					resolutionTypePanel.add(rdbtnStandardResolution);
				}
				{
					rdbtnCustomResolution = new JRadioButton("Custom resolution");
					rdbtnCustomResolution.setBackground(Color.LIGHT_GRAY);
					rdbtnCustomResolution.addActionListener(new ShowPredefinedResolutionListener(false));
					{
						Component horizontalGlue = Box.createHorizontalGlue();
						resolutionTypePanel.add(horizontalGlue);
					}
					buttonGroup_1.add(rdbtnCustomResolution);
					resolutionTypePanel.add(rdbtnCustomResolution);
				}
			}
			{
				Component verticalStrut = Box.createVerticalStrut(20);
				resTypePanel.add(verticalStrut);
			}
			{
				JPanel resolutionValuePanel = new JPanel();
				resolutionValuePanel.setMaximumSize(new Dimension(500, 30));
				resolutionValuePanel.setPreferredSize(new Dimension(500, 30));
				resolutionValuePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				resTypePanel.add(resolutionValuePanel);
				resolutionValuePanel.setLayout(new BoxLayout(resolutionValuePanel, BoxLayout.X_AXIS));
				{
					predefinedResolutionPanel = new JPanel();
					predefinedResolutionPanel.setBackground(Color.GRAY);
					resolutionValuePanel.add(predefinedResolutionPanel);
					predefinedResolutionPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
					predefinedResolutionPanel.setLayout(new BoxLayout(predefinedResolutionPanel, BoxLayout.X_AXIS));
					{
						comboBox = new JComboBox<String>();
						comboBox.setBackground(Color.LIGHT_GRAY);
						predefinedResolutionPanel.add(comboBox);
						comboBox.setBorder(null);
						comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"320*240", "480*320", "640*480", "800*480", "800*600", "960*540", "1024*768", "1280*800", "1366*768"}));
					}
				}
				{
					customResolutionPanel = new JPanel();
					customResolutionPanel.setBackground(Color.GRAY);
					resolutionValuePanel.add(customResolutionPanel);
					customResolutionPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
					customResolutionPanel.setLayout(new BoxLayout(customResolutionPanel, BoxLayout.X_AXIS));
					{
						resWidth = new JTextField();
						customResolutionPanel.add(resWidth);
						resWidth.setColumns(10);
					}
					{
						JLabel label = new JLabel("*");
						customResolutionPanel.add(label);
					}
					{
						resHeight = new JTextField();
						customResolutionPanel.add(resHeight);
						resHeight.setColumns(10);
					}
				}
			}
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			scalePanel = new JPanel();
			scalePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			scalePanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(scalePanel);
			scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.X_AXIS));
			{
				scaleBox = new JCheckBox("Scale resolution");
				scaleBox.addActionListener(new ScaleCheckedListener());
				scaleBox.setBackground(Color.LIGHT_GRAY);
				scalePanel.add(scaleBox);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				scalePanel.add(horizontalGlue);
			}
		}
		{
			scaleComboboxPanel = new JPanel();
			scaleComboboxPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			scaleComboboxPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(scaleComboboxPanel);
			scaleComboboxPanel.setLayout(new BoxLayout(scaleComboboxPanel, BoxLayout.X_AXIS));
			{
				scaleComboBox = new JComboBox<String>();
				scaleComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9", "2.0"}));
				scaleComboBox.setSelectedIndex(9);
				scaleComboBox.setBackground(Color.LIGHT_GRAY);
				scaleComboboxPanel.add(scaleComboBox);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Select");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		pack();
		predefinedResolutionPanel.setVisible(true);
		customResolutionPanel.setVisible(false);
		scaleComboboxPanel.setVisible(false);
		
		loadSettings();
	}
	
	
	class ShowPredefinedResolutionListener implements ActionListener {
		private boolean showPredefined;
		public ShowPredefinedResolutionListener(boolean showPredefined) {
			this.showPredefined = showPredefined;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			customResolutionPanel.setVisible(!showPredefined);
			predefinedResolutionPanel.setVisible(showPredefined);
			pack();
		}
	}
	
	public int getScreenWidth() {
		int width = 0;
		
		if (rdbtnStandardResolution.isSelected()) {
			String[] resItems = comboBox.getSelectedItem().toString().split("\\*");
			width = Integer.parseInt(resItems[0]);
		} else {
			try {
				width = Integer.parseInt(resWidth.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(WindowSizeDialog.this, "Input correct screen resolution!");
			}
		}
		
		return width;
	}
	
	public int getScreenHeight() {
		int height = 0;
		
		if (rdbtnStandardResolution.isSelected()) {
			String[] resItems = comboBox.getSelectedItem().toString().split("\\*");
			height = Integer.parseInt(resItems[1]);
		} else {
			try {
				height = Integer.parseInt(resHeight.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(WindowSizeDialog.this, "Input correct screen resolution!");
			}
		}
		
		return height;
	}
	
	public boolean isLandscape() {
		return landRadionButton.isSelected();
	}
	
	public float getScaleCoeff() {
		if (scaleBox.isSelected()) {
			return Float.parseFloat(scaleComboBox.getSelectedItem().toString());
		} else {
			return 1;
		}
	}
	
	public boolean isScreenSizeCorrect() {
		if (rdbtnStandardResolution.isSelected()) {
			return comboBox.getSelectedIndex() >= 0;
		} else {
			try {
				Integer.parseInt(resWidth.getText());
				Integer.parseInt(resHeight.getText());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}
	
	public boolean isFullScreen() {
		return rdbtnFullscreenMode.isSelected();
	}
	
	public void saveSettings() {
		Settings.getInstance().setScreenOrientaion(landRadionButton.isSelected());
		Settings.getInstance().setScreenResolution(getScreenWidth(), getScreenHeight());
		Settings.getInstance().setNeedScale(scaleBox.isSelected());
		Settings.getInstance().setScale(scaleComboBox.getSelectedItem().toString());
		Settings.getInstance().setNeedFullScreen(rdbtnFullscreenMode.isSelected());
	}
	
	public void loadSettings() {
		if (Settings.getInstance().isLandscapeOrientation()) {
			landRadionButton.setSelected(true);
		} else {
			portRadioButton.setSelected(true);
		}
		
		int width = Settings.getInstance().getScreenWidth();
		int height = Settings.getInstance().getScreenHeight();

		rdbtnCustomResolution.setSelected(true);
		
		String size = width+"*"+height;
		for(int i = 0; i < comboBox.getModel().getSize(); i++) {
			Object o = comboBox.getModel().getElementAt(i);
			if (o.toString().equals(size)) {
				rdbtnStandardResolution.setSelected(true);
				comboBox.setSelectedItem(o);
				break;
			}
		}
		
		resWidth.setText(width+"");
		resHeight.setText(height+"");
		scaleBox.setSelected(Settings.getInstance().isNeedScale());
		scaleComboBox.setSelectedItem(Settings.getInstance().getScale());
		rdbtnFullscreenMode.setSelected(Settings.getInstance().isNeedFullScreen());
		new FullScreenModeListener().actionPerformed(null);
		new ShowPredefinedResolutionListener(rdbtnStandardResolution.isSelected()).actionPerformed(null);
		new ScaleCheckedListener().actionPerformed(null);
	}
	
	class ScaleCheckedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (rdbtnWindow.isSelected()) {
				scaleComboboxPanel.setVisible(scaleBox.isSelected());
				pack();
			}
		}
	}
	
	class FullScreenModeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orientationPanel.setVisible(rdbtnWindow.isSelected());
			scalePanel.setVisible(rdbtnWindow.isSelected());
			scaleComboboxPanel.setVisible(rdbtnWindow.isSelected());
			
			new ScaleCheckedListener().actionPerformed(null);
			
			pack();
		}
		
	}
	
	public JButton getOkButton() {
		return okButton;
	}
}
