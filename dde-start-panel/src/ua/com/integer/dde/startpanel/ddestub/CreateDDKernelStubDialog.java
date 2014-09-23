package ua.com.integer.dde.startpanel.ddestub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.Settings;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;

public class CreateDDKernelStubDialog extends JDialog {
	private static final long serialVersionUID = -6966105495542989983L;
	private final JPanel contentPanel = new JPanel();
	private JTextField kernelProjectPath;
	private JTextField packageNameField;
	private JTextField mainClassNameText;
	private JTextField desktopProjectPath;
	private JLabel lblDesktopProjectPath;
	private JLabel lblKernelProjectPath;
	private JLabel lblMainClassName;
	private JLabel lblPackageName;
	private JCheckBox generateProjectDesktopStub;
	private JPanel desktopProjectPanel;
	private JPanel androidProjectGeneratePanel;
	private Component verticalStrut_1;
	private JCheckBox generateAndroidProjectCheckBox;
	private JRadioButton androidAppRadiobutton;
	private JRadioButton androidLiveWallpaperRadiobutton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Component horizontalGlue_1;
	private JPanel androidPanel;
	private JLabel lblAndroidProjectPath;
	private JTextField androidProjectPath;
	private JPanel morppKernelCheckboxPanel;
	private JCheckBox generateKernelProjectBox;
	private Component horizontalGlue_2;
	private JPanel kernelProjectPanel;
	private Component horizontalGlue_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateDDKernelStubDialog dialog = new CreateDDKernelStubDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreateDDKernelStubDialog() {
		setTitle("DDKernel Morpher");
		setBounds(100, 100, 650, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel packageNamePanel = new JPanel();
			packageNamePanel.setBackground(Color.LIGHT_GRAY);
			packageNamePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(packageNamePanel);
			packageNamePanel.setLayout(new BoxLayout(packageNamePanel, BoxLayout.X_AXIS));
			{
				lblPackageName = new JLabel("Package name");
				packageNamePanel.add(lblPackageName);
			}
			{
				packageNameField = new JTextField();
				packageNameField.setText("ua.com.integer.ddetest");
				packageNamePanel.add(packageNameField);
				packageNameField.setColumns(10);
			}
			FrameTools.setPreferredHeight(packageNamePanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel mainClassNamePanel = new JPanel();
			mainClassNamePanel.setBackground(Color.LIGHT_GRAY);
			mainClassNamePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(mainClassNamePanel);
			mainClassNamePanel.setLayout(new BoxLayout(mainClassNamePanel, BoxLayout.X_AXIS));
			{
				lblMainClassName = new JLabel("Kernel class name");
				mainClassNamePanel.add(lblMainClassName);
			}
			{
				mainClassNameText = new JTextField();
				mainClassNameText.setText("DDETestGame");
				mainClassNamePanel.add(mainClassNameText);
				mainClassNameText.setColumns(10);
			}
			FrameTools.setPreferredHeight(mainClassNamePanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			morppKernelCheckboxPanel = new JPanel();
			morppKernelCheckboxPanel.setBackground(Color.GRAY);
			morppKernelCheckboxPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(morppKernelCheckboxPanel);
			morppKernelCheckboxPanel.setLayout(new BoxLayout(morppKernelCheckboxPanel, BoxLayout.X_AXIS));
			{
				generateKernelProjectBox = new JCheckBox("Morph kernel project");
				generateKernelProjectBox.setBackground(Color.GRAY);
				generateKernelProjectBox.setSelected(true);
				generateKernelProjectBox.addActionListener(new GenerateKernelProjectListener());
				morppKernelCheckboxPanel.add(generateKernelProjectBox);
			}
			{
				horizontalGlue_2 = Box.createHorizontalGlue();
				morppKernelCheckboxPanel.add(horizontalGlue_2);
			}
		}
		{
			kernelProjectPanel = new JPanel();
			kernelProjectPanel.setBackground(Color.LIGHT_GRAY);
			kernelProjectPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(kernelProjectPanel);
			kernelProjectPanel.setLayout(new BoxLayout(kernelProjectPanel, BoxLayout.X_AXIS));
			{
				lblKernelProjectPath = new JLabel("Kernel project path");
				kernelProjectPanel.add(lblKernelProjectPath);
			}
			{
				kernelProjectPath = new JTextField();
				kernelProjectPath.setText("../dde-stub-kernel");
				kernelProjectPanel.add(kernelProjectPath);
				kernelProjectPath.setColumns(10);
			}
			FrameTools.setPreferredHeight(kernelProjectPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel generateProjectDesktopPanel = new JPanel();
			generateProjectDesktopPanel.setBackground(Color.GRAY);
			generateProjectDesktopPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(generateProjectDesktopPanel);
			generateProjectDesktopPanel.setLayout(new BoxLayout(generateProjectDesktopPanel, BoxLayout.X_AXIS));
			{
				generateProjectDesktopStub = new JCheckBox("Morph desktop project");
				generateProjectDesktopStub.setBackground(Color.GRAY);
				generateProjectDesktopStub.setSelected(true);
				generateProjectDesktopStub.addActionListener(new GenerateDesktopProjectListener());
				generateProjectDesktopPanel.add(generateProjectDesktopStub);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				generateProjectDesktopPanel.add(horizontalGlue);
			}
		}
		{
			desktopProjectPanel = new JPanel();
			desktopProjectPanel.setBackground(Color.LIGHT_GRAY);
			desktopProjectPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(desktopProjectPanel);
			desktopProjectPanel.setLayout(new BoxLayout(desktopProjectPanel, BoxLayout.X_AXIS));
			{
				lblDesktopProjectPath = new JLabel("Desktop project path");
				desktopProjectPanel.add(lblDesktopProjectPath);
			}
			{
				desktopProjectPath = new JTextField();
				desktopProjectPath.setText("../dde-stub-desktop");
				desktopProjectPanel.add(desktopProjectPath);
				desktopProjectPath.setColumns(10);
			}
			FrameTools.setPreferredHeight(desktopProjectPanel);
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Morph to DDE");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.addActionListener(new StubListener());
				//buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
				{
					horizontalGlue_3 = Box.createHorizontalGlue();
					buttonPane.add(horizontalGlue_3);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		{
			verticalStrut_1 = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut_1);
		}
		{
			androidProjectGeneratePanel = new JPanel();
			androidProjectGeneratePanel.setBackground(Color.GRAY);
			androidProjectGeneratePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(androidProjectGeneratePanel);
			androidProjectGeneratePanel.setLayout(new BoxLayout(androidProjectGeneratePanel, BoxLayout.X_AXIS));
			{
				generateAndroidProjectCheckBox = new JCheckBox("Morph android project");
				generateAndroidProjectCheckBox.setBackground(Color.GRAY);
				generateAndroidProjectCheckBox.setSelected(true);
				generateAndroidProjectCheckBox.addActionListener(new GenerateAndroidProjectListener());
				androidProjectGeneratePanel.add(generateAndroidProjectCheckBox);
			}
			{
				horizontalGlue_1 = Box.createHorizontalGlue();
				androidProjectGeneratePanel.add(horizontalGlue_1);
			}
			{
				androidAppRadiobutton = new JRadioButton("Application stub");
				androidAppRadiobutton.setBackground(Color.GRAY);
				androidAppRadiobutton.setSelected(true);
				buttonGroup.add(androidAppRadiobutton);
				androidProjectGeneratePanel.add(androidAppRadiobutton);
			}
			{
				androidLiveWallpaperRadiobutton = new JRadioButton("Live wallpaper stub");
				androidLiveWallpaperRadiobutton.setBackground(Color.GRAY);
				buttonGroup.add(androidLiveWallpaperRadiobutton);
				androidProjectGeneratePanel.add(androidLiveWallpaperRadiobutton);
			}
		}
		{
			androidPanel = new JPanel();
			androidPanel.setBackground(Color.LIGHT_GRAY);
			androidPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(androidPanel);
			androidPanel.setLayout(new BoxLayout(androidPanel, BoxLayout.X_AXIS));
			{
				lblAndroidProjectPath = new JLabel("Android project path");
				androidPanel.add(lblAndroidProjectPath);
			}
			{
				androidProjectPath = new JTextField();
				androidProjectPath.setText("../dde-android-stub");
				androidPanel.add(androidProjectPath);
				androidProjectPath.setColumns(10);
			}
			FrameTools.setPreferredHeight(androidPanel);
		}
		
		FrameTools.setAsExample(lblDesktopProjectPath, lblPackageName, lblMainClassName, lblKernelProjectPath, lblAndroidProjectPath);
		pack();
		FrameTools.situateOnCenter(this);
	
		findProjects();
	}

	class StubListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure? All you source files will be erased!", "Stub creation confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				DDEMorpher morpher = new DDEMorpher();
				morpher.setPackageName(packageNameField.getText());
				morpher.setMainClassname(mainClassNameText.getText());

				morpher.setMorphKernel(generateKernelProjectBox.isSelected());
				morpher.setKernelProjectPath(kernelProjectPath.getText());

				morpher.setMorphAndroid(generateAndroidProjectCheckBox.isSelected());
				morpher.setAndroidProject(androidProjectPath.getText());
				morpher.setAndroidWallpaper(androidLiveWallpaperRadiobutton.isSelected());
				
				morpher.setMorphDesktop(generateProjectDesktopStub.isSelected());
				morpher.setDesktopProject(desktopProjectPath.getText());

				morpher.createStub();
				
				Settings s = Settings.getInstance();
				s.putString("desktop-starter-package", packageNameField.getText());
				s.putString("desktop-start-class", "DesktopStarter");
				JOptionPane.showMessageDialog(null, "All projects was mophed. Please refresh your projects.");
			}
			
		}
	}
	
	class GenerateDesktopProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			desktopProjectPanel.setVisible(generateProjectDesktopStub.isSelected());
		}
	}
	
	class GenerateAndroidProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			androidPanel.setVisible(generateAndroidProjectCheckBox.isSelected());
			androidAppRadiobutton.setVisible(generateAndroidProjectCheckBox.isSelected());
			androidLiveWallpaperRadiobutton.setVisible(generateAndroidProjectCheckBox.isSelected());
		}
	}
	
	class GenerateKernelProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			kernelProjectPanel.setVisible(generateKernelProjectBox.isSelected());
		}
		
	}
	
	private void findProjects() {
		androidProjectPath.setText(ProjectFinder.findAndroidProject());
		desktopProjectPath.setText(ProjectFinder.findDesktopProject());
		kernelProjectPath.setText(ProjectFinder.findKernelProject());
	}
}
