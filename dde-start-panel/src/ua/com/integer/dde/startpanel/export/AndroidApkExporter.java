package ua.com.integer.dde.startpanel.export;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import ua.com.integer.dde.startpanel.FileUtils;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.ddestub.FileStubMaker;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;

public class AndroidApkExporter extends JDialog {
	private static final long serialVersionUID = -622040365542521102L;
	private final JPanel contentPanel = new JPanel();
	private JTextField androidProjectPathText;
	private JTextPane textPane;
	private JTextField androidSdkDirText;
	private JLabel lblAndroidSdkDir;
	private JLabel lblAndroidProjectPath;
	private JPanel progressPanel;
	private JTextField kernelProjectPathText;
	private JLabel lblKernelProjectPath;
	private JTextField keystoreLocationText;
	private JLabel lblKeystoreLocation;
	private JPasswordField keystorePasswordField;
	private JTextField aliasNameField;
	private JPasswordField aliasPassField;
	private JLabel lblKeystorePass;
	private JTextField apkDirectoryField;
	private JLabel lblDestinationApk;
	private JTextField apkNameField;
	private JLabel lblApkName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AndroidApkExporter dialog = new AndroidApkExporter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AndroidApkExporter() {
		setTitle("DDE Android exporter");
		setBounds(100, 100, 650, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel kernelProjectPathPanel = new JPanel();
			kernelProjectPathPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			kernelProjectPathPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(kernelProjectPathPanel);
			kernelProjectPathPanel.setLayout(new BoxLayout(kernelProjectPathPanel, BoxLayout.X_AXIS));
			{
				lblKernelProjectPath = new JLabel("Kernel project path");
				kernelProjectPathPanel.add(lblKernelProjectPath);
			}
			{
				kernelProjectPathText = new JTextField();
				kernelProjectPathPanel.add(kernelProjectPathText);
				kernelProjectPathText.setColumns(10);
			}
			FrameTools.setPreferredHeight(kernelProjectPathPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel androidProjectPathPanel = new JPanel();
			androidProjectPathPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			androidProjectPathPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(androidProjectPathPanel);
			androidProjectPathPanel.setLayout(new BoxLayout(androidProjectPathPanel, BoxLayout.X_AXIS));
			{
				lblAndroidProjectPath = new JLabel("Android project path");
				androidProjectPathPanel.add(lblAndroidProjectPath);
			}
			{
				androidProjectPathText = new JTextField();
				androidProjectPathText.setBackground(Color.WHITE);
				androidProjectPathPanel.add(androidProjectPathText);
				androidProjectPathText.setColumns(10);
			}
			FrameTools.setPreferredHeight(androidProjectPathPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel sdkDirPanel = new JPanel();
			sdkDirPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			sdkDirPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(sdkDirPanel);
			sdkDirPanel.setLayout(new BoxLayout(sdkDirPanel, BoxLayout.X_AXIS));
			{
				lblAndroidSdkDir = new JLabel("Android SDK dir");
				sdkDirPanel.add(lblAndroidSdkDir);
			}
			{
				androidSdkDirText = new JTextField();
				sdkDirPanel.add(androidSdkDirText);
				androidSdkDirText.setColumns(10);
			}
			FrameTools.setPreferredHeight(sdkDirPanel);
			{
				JButton sdkDirButton = new JButton("Select");
				sdkDirButton.addActionListener(new ChangeSdkDirLocationListener());
				sdkDirButton.setBackground(Color.LIGHT_GRAY);
				sdkDirPanel.add(sdkDirButton);
			}
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel keystoreLocationPanel = new JPanel();
			keystoreLocationPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			keystoreLocationPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(keystoreLocationPanel);
			keystoreLocationPanel.setLayout(new BoxLayout(keystoreLocationPanel, BoxLayout.X_AXIS));
			{
				lblKeystoreLocation = new JLabel("Keystore location");
				keystoreLocationPanel.add(lblKeystoreLocation);
			}
			{
				keystoreLocationText = new JTextField();
				keystoreLocationPanel.add(keystoreLocationText);
				keystoreLocationText.setColumns(10);
			}
			{
				JButton btnSelectKeystoreLocation = new JButton("Select");
				btnSelectKeystoreLocation.addActionListener(new ChangeKeystoreLocationListener());
				btnSelectKeystoreLocation.setBackground(Color.LIGHT_GRAY);
				keystoreLocationPanel.add(btnSelectKeystoreLocation);
			}
			FrameTools.setPreferredHeight(keystoreLocationPanel);
		}
		{
			JPanel keystoreParamsPanel = new JPanel();
			keystoreParamsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			keystoreParamsPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(keystoreParamsPanel);
			keystoreParamsPanel.setLayout(new BoxLayout(keystoreParamsPanel, BoxLayout.X_AXIS));
			{
				lblKeystorePass = new JLabel("Keystore pass");
				keystoreParamsPanel.add(lblKeystorePass);
			}
			{
				keystorePasswordField = new JPasswordField();
				keystorePasswordField.setColumns(10);
				keystoreParamsPanel.add(keystorePasswordField);
			}
			{
				JLabel lblAlias = new JLabel("Alias name");
				keystoreParamsPanel.add(lblAlias);
			}
			{
				aliasNameField = new JTextField();
				keystoreParamsPanel.add(aliasNameField);
				aliasNameField.setColumns(10);
			}
			{
				JLabel lblAliasPass = new JLabel("Alias pass");
				keystoreParamsPanel.add(lblAliasPass);
			}
			{
				aliasPassField = new JPasswordField();
				keystoreParamsPanel.add(aliasPassField);
				aliasPassField.setColumns(10);
			}
			FrameTools.setPreferredHeight(keystoreParamsPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel destinationApkPanel = new JPanel();
			destinationApkPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			destinationApkPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(destinationApkPanel);
			destinationApkPanel.setLayout(new BoxLayout(destinationApkPanel, BoxLayout.X_AXIS));
			{
				lblDestinationApk = new JLabel("Destination apk path");
				destinationApkPanel.add(lblDestinationApk);
			}
			{
				apkDirectoryField = new JTextField();
				destinationApkPanel.add(apkDirectoryField);
				apkDirectoryField.setColumns(10);
			}
			FrameTools.setPreferredHeight(destinationApkPanel);
			{
				JButton setApkPathButton = new JButton("Select");
				setApkPathButton.addActionListener(new ChangeApkLocationListener());
				setApkPathButton.setBackground(Color.LIGHT_GRAY);
				destinationApkPanel.add(setApkPathButton);
			}
		}
		{
			JPanel apkNamePanel = new JPanel();
			apkNamePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			apkNamePanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(apkNamePanel);
			apkNamePanel.setLayout(new BoxLayout(apkNamePanel, BoxLayout.X_AXIS));
			{
				lblApkName = new JLabel("Apk name");
				apkNamePanel.add(lblApkName);
			}
			{
				apkNameField = new JTextField();
				apkNamePanel.add(apkNameField);
				apkNameField.setColumns(10);
			}
			FrameTools.setPreferredHeight(apkNamePanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut);
		}
		{
			JPanel logPanel = new JPanel();
			logPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			logPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(logPanel);
			logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.X_AXIS));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBackground(Color.LIGHT_GRAY);
				scrollPane.setViewportBorder(new TitledBorder(null, "Export log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				logPanel.add(scrollPane);
				{
					textPane = new JTextPane();
					textPane.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(textPane);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Export");
				okButton.setFont(new Font("Dialog", Font.BOLD, 16));
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.addActionListener(new ExportListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		{
			progressPanel = new JPanel();
			progressPanel.setVisible(false);
			progressPanel.setBackground(Color.LIGHT_GRAY);
			progressPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(progressPanel);
			progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.X_AXIS));
			{
				JProgressBar progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				progressPanel.add(progressBar);
			}
		}
		
		pack();
		loadSettings();
		FrameTools.setAsExample(lblDestinationApk, lblAndroidProjectPath, lblAndroidSdkDir, lblKernelProjectPath, lblKeystoreLocation, lblKeystorePass, lblApkName);
		findProjects();
	}
	
	class ExportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileUtils.clearDirectory(getAndroidBldDir());
			FileUtils.clearDirectory(ProjectFinder.findAndroidProject() + "/bin");
			copyAntBuildFileForBuildApk();
			copyAntPropertiesFile();
			copyAntBuildFileForExport();
			runAnt();
		}
		
		private void copyAntBuildFileForBuildApk() {
			FileStubMaker fMaker = new FileStubMaker();
			fMaker.loadFileFromRes("xml/ant/android/build-dde.xml");
			fMaker.saveToFile(androidProjectPathText.getText()+"/build.xml");
		}
		
		
		private void copyAntPropertiesFile() {
			FileStubMaker fMaker = new FileStubMaker();
			fMaker.loadFileFromRes("xml/ant/android/build.properties");
			fMaker.set("kernelProject", kernelProjectPathText.getText());
			fMaker.set("androidProject", getAndroidAntDir());
			fMaker.set("androidToolsDir", androidSdkDirText.getText());
			fMaker.set("keystorePass", new String(keystorePasswordField.getPassword()));
			fMaker.set("aliasPass", new String(aliasPassField.getPassword()));
			fMaker.set("aliasName", aliasNameField.getText());
			fMaker.set("androidApkName", apkDirectoryField.getText()+"/"+apkNameField.getText()+".apk");
			fMaker.saveToFile(getAndroidBldDir()+"/build.properties");
		}
		
		private void copyAntBuildFileForExport() {
			FileStubMaker fMaker = new FileStubMaker();
			fMaker.loadFileFromRes("xml/ant/android/build.xml");
			fMaker.set("keystoreName", keystoreLocationText.getText());
			fMaker.saveToFile(getAndroidBldDir()+"/build.xml");
		}
		
		private void runAnt() {
			progressPanel.setVisible(true);
			saveSettings();

			final Project p = new Project();
			p.init();
			
			File buildFile = new File(getAndroidBldDir(), "build.xml");
			ProjectHelper.configureProject(p, buildFile);
			textPane.setText("Begin exporting...");
			p.addBuildListener(new ExportTextListener(textPane));
				new Thread() {
					public void run() {
						try {
							p.executeTarget("build.android");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Can't export android application!");
							textPane.setText(e.getMessage());
						}
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								progressPanel.setVisible(false);
								textPane.setText(textPane.getText() + "\n" + "Export finished.");
								JOptionPane.showMessageDialog(null, "Build finished!");
							}
						});
					};
				}.start();
		}
	}
	
	private void findProjects() {
		androidProjectPathText.setText(ProjectFinder.findAndroidProject());
		kernelProjectPathText.setText(ProjectFinder.findKernelProject());
	}
	
	private String getAndroidAntDir() {
		return "../" + androidProjectPathText.getText();
	}
	
	private String getAndroidBldDir() {
		return androidProjectPathText.getText() + "/bld";
	}
	
	private void saveSettings() {
		Settings s = Settings.getInstance();
		s.putString("android-sdk-dir", androidSdkDirText.getText());
		s.putString("android-keystore-location", keystoreLocationText.getText());
		s.putString("android-keystore-password", new String(keystorePasswordField.getPassword()));
		s.putString("android-keystore-alias-name", aliasNameField.getText());
		s.putString("android-keystore-alias-password", new String(aliasPassField.getPassword()));
		s.putString("android-apk-directory", apkDirectoryField.getText());
		s.putString("android-apk-name", apkNameField.getText());
	}
	
	private void loadSettings() {
		Settings s = Settings.getInstance();
		androidSdkDirText.setText(s.getString("android-sdk-dir", "<undefined>"));
		keystoreLocationText.setText(s.getString("android-keystore-location", "<undefined>"));
		keystorePasswordField.setText(s.getString("android-keystore-password", ""));
		aliasNameField.setText(s.getString("android-keystore-alias-name", "<undefined>"));
		aliasPassField.setText(s.getString("android-keystore-alias-password", ""));
		apkDirectoryField.setText(s.getString("android-apk-directory", "<undefined>"));
		apkNameField.setText(s.getString("android-apk-name", "<undefined>"));
	}
	
	class ChangeKeystoreLocationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Settings s = Settings.getInstance();
			JFileChooser fChooser = new JFileChooser(s.getString("android-keystore-location", "./"));
			if (fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				keystoreLocationText.setText(fChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	class ChangeApkLocationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Settings s = Settings.getInstance();
			JFileChooser fChooser = new JFileChooser(s.getString("android-apk-directory", "<undefined>"));
			fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				apkDirectoryField.setText(fChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	class ChangeSdkDirLocationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Settings s = Settings.getInstance();
			JFileChooser fChooser = new JFileChooser(s.getString("android-sdk-dir", "<undefined>"));
			fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				androidSdkDirText.setText(fChooser.getSelectedFile().getAbsolutePath());
			}
		}
		
	}
}
