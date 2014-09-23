package ua.com.integer.dde.startpanel.export;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import com.badlogic.gdx.utils.Array;

import ua.com.integer.dde.startpanel.FileUtils;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.ddestub.FileStubMaker;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;

public class DesktopExporter extends JDialog {
	private static final long serialVersionUID = -5396121823319697966L;
	private final JPanel contentPanel = new JPanel();
	private JTextField startPackageText;
	private JLabel lblStarterClassPackage;

	private JTextField startClassName;
	private JLabel lblStarterClassName;
	
	private JTextField androidProjectPath;
	private JLabel lblAndroidProjectPath;
	
	private JTextField desktopProjectPath;
	private JLabel lblDesktopProjectPath;
	
	private JTextField mainProjectPath;
	private JLabel lblMainProjectPath;
	
	private JTextField exportDirPath;
	private JLabel lblExportDirPath;
	
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private JPanel jarPanel;
	private Component verticalStrut_3;
	private JCheckBox exportJarCheckBox;
	private JTextField jarNameTextField;
	private JLabel exportJarNameLabel;
	private JPanel zipPanel;
	private JCheckBox exportZipCheckBox;
	private JLabel zipArchiveNameLabel;
	private JTextField zipNameTextField;
	private JPanel exportLogPanel;
	private Component verticalStrut_4;
	private JTextPane logText;
	private JScrollPane scrollPane;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JButton selectExportDirDirectory;
	private JPanel exportProgressPanel;
	private JProgressBar progressBar;
	private Component verticalStrut_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DesktopExporter dialog = new DesktopExporter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DesktopExporter() {
		setTitle("DDE Desktop Exporter");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel startPackagePanel = new JPanel();
			startPackagePanel.setPreferredSize(new Dimension(600, 30));
			startPackagePanel.setMinimumSize(new Dimension(600, 30));
			startPackagePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			startPackagePanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(startPackagePanel);
			startPackagePanel.setLayout(new BoxLayout(startPackagePanel, BoxLayout.X_AXIS));
			{
				lblStarterClassPackage = new JLabel("Starter class package");
				startPackagePanel.add(lblStarterClassPackage);
			}
			{
				startPackageText = new JTextField();
				startPackagePanel.add(startPackageText);
				startPackageText.setColumns(10);
			}
			FrameTools.setPreferredHeight(startPackagePanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(12);
			contentPanel.add(verticalStrut);
		}
		
		//Starter classname
		{
			JPanel startClassPanel = new JPanel();
			startClassPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			startClassPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(startClassPanel);
			startClassPanel.setLayout(new BoxLayout(startClassPanel, BoxLayout.X_AXIS));
			{
				lblStarterClassName = new JLabel("Starter class name");
				startClassPanel.add(lblStarterClassName);
			}
			{
				startClassName = new JTextField();
				startClassPanel.add(startClassName);
				startClassName.setColumns(10);
			}
			FrameTools.setPreferredHeight(startClassPanel);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(12);
			contentPanel.add(verticalStrut);
		}
		
	// Main project path
				{
					JPanel mainProjectPanel = new JPanel();
					mainProjectPanel.setBorder(new EtchedBorder(
							EtchedBorder.LOWERED, null, null));
					mainProjectPanel.setBackground(Color.LIGHT_GRAY);
					contentPanel.add(mainProjectPanel);
					mainProjectPanel.setLayout(new BoxLayout(mainProjectPanel,
							BoxLayout.X_AXIS));
					{
						lblMainProjectPath = new JLabel("Main project");
						mainProjectPanel.add(lblMainProjectPath);
					}
					{
						mainProjectPath = new JTextField();
						mainProjectPanel.add(mainProjectPath);
						mainProjectPath.setColumns(10);
					}
					FrameTools.setPreferredHeight(mainProjectPanel);
				}
		{
			verticalStrut_5 = Box.createVerticalStrut(5);
			contentPanel.add(verticalStrut_5);
		}
		
		// Main project path
		{
			JPanel desktopProjectPanel = new JPanel();
			desktopProjectPanel.setBorder(new EtchedBorder(
					EtchedBorder.LOWERED, null, null));
			desktopProjectPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(desktopProjectPanel);
			desktopProjectPanel.setLayout(new BoxLayout(desktopProjectPanel,
					BoxLayout.X_AXIS));
			{
				lblDesktopProjectPath = new JLabel("Desktop project");
				desktopProjectPanel.add(lblDesktopProjectPath);
			}
			{
				desktopProjectPath = new JTextField();
				desktopProjectPanel.add(desktopProjectPath);
				desktopProjectPath.setColumns(10);
			}
			FrameTools.setPreferredHeight(desktopProjectPanel);
		}
		{
			verticalStrut_1 = Box.createVerticalStrut(5);
			contentPanel.add(verticalStrut_1);
		}
		
		// Android project path
		{
			JPanel androidProjectPanel = new JPanel();
			androidProjectPanel.setBorder(new EtchedBorder(
					EtchedBorder.LOWERED, null, null));
			androidProjectPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(androidProjectPanel);
			androidProjectPanel.setLayout(new BoxLayout(androidProjectPanel,
					BoxLayout.X_AXIS));
			{
				lblAndroidProjectPath = new JLabel("Android project");
				androidProjectPanel.add(lblAndroidProjectPath);
			}
			{
				androidProjectPath = new JTextField();
				androidProjectPanel.add(androidProjectPath);
				androidProjectPath.setColumns(10);
			}
			FrameTools.setPreferredHeight(androidProjectPanel);
		}
				{
					verticalStrut_2 = Box.createVerticalStrut(30);
					contentPanel.add(verticalStrut_2);
				}
		
		//Export dir
				{
					JPanel exportPathPanel = new JPanel();
					exportPathPanel.setBorder(new EtchedBorder(
							EtchedBorder.LOWERED, null, null));
					exportPathPanel.setBackground(Color.LIGHT_GRAY);
					contentPanel.add(exportPathPanel);
					exportPathPanel.setLayout(new BoxLayout(exportPathPanel,
							BoxLayout.X_AXIS));
					{
						lblExportDirPath = new JLabel("Export directory");
						exportPathPanel.add(lblExportDirPath);
					}
					{
						selectExportDirDirectory = new JButton("Select");
						selectExportDirDirectory.addActionListener(new SelectExportDirectoryListener());
						selectExportDirDirectory.setPreferredSize(new Dimension(80, 25));
						selectExportDirDirectory.setMinimumSize(new Dimension(80, 25));
						selectExportDirDirectory.setMaximumSize(new Dimension(80, 25));
						selectExportDirDirectory.setBackground(Color.LIGHT_GRAY);
						exportPathPanel.add(selectExportDirDirectory);
					}
					{
						exportDirPath = new JTextField();
						exportPathPanel.add(exportDirPath);
						exportDirPath.setColumns(10);
					}
					FrameTools.setPreferredHeight(exportPathPanel);
				}
				
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton exportButton = new JButton("Export");
				exportButton.setFont(new Font("Dialog", Font.BOLD, 16));
				exportButton.addActionListener(new BuildListener());
				exportButton.setBackground(Color.LIGHT_GRAY);
				exportButton.setActionCommand("OK");
				buttonPane.add(exportButton);
				getRootPane().setDefaultButton(exportButton);
			}
		}
		
		{
			verticalStrut_3 = Box.createVerticalStrut(5);
			contentPanel.add(verticalStrut_3);
		}
		{
			jarPanel = new JPanel();
			jarPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			jarPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(jarPanel);
			jarPanel.setLayout(new BoxLayout(jarPanel, BoxLayout.X_AXIS));
			{
				exportJarCheckBox = new JCheckBox("Export jar");
				exportJarCheckBox.addActionListener(new JarCheckListener());
				exportJarCheckBox.setSelected(true);
				exportJarCheckBox.setBackground(Color.LIGHT_GRAY);
				jarPanel.add(exportJarCheckBox);
			}
			{
				exportJarNameLabel = new JLabel("Name");
				exportJarNameLabel.setPreferredSize(new Dimension(80, 15));
				exportJarNameLabel.setMinimumSize(new Dimension(80, 15));
				exportJarNameLabel.setMaximumSize(new Dimension(80, 15));
				jarPanel.add(exportJarNameLabel);
			}
			{
				jarNameTextField = new JTextField();
				jarPanel.add(jarNameTextField);
				jarNameTextField.setColumns(10);
			}
			FrameTools.setPreferredHeight(jarPanel);
		}

		{
			zipPanel = new JPanel();
			zipPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			zipPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(zipPanel);
			zipPanel.setLayout(new BoxLayout(zipPanel, BoxLayout.X_AXIS));
			{
				exportZipCheckBox = new JCheckBox("Export zip");
				exportZipCheckBox.addActionListener(new ZipCheckListener());
				exportZipCheckBox.setSelected(true);
				exportZipCheckBox.setBackground(Color.LIGHT_GRAY);
				zipPanel.add(exportZipCheckBox);
			}
		}
		
		{
			zipArchiveNameLabel = new JLabel("Name");
			zipArchiveNameLabel.setPreferredSize(new Dimension(80, 15));
			zipArchiveNameLabel.setMinimumSize(new Dimension(80, 15));
			zipArchiveNameLabel.setMaximumSize(new Dimension(80, 15));
			zipPanel.add(zipArchiveNameLabel);
		}
		{
			zipNameTextField = new JTextField();
			zipPanel.add(zipNameTextField);
			zipNameTextField.setColumns(10);
			FrameTools.setPreferredHeight(zipPanel);
		}
		
		{
			horizontalGlue_1 = Box.createHorizontalGlue();
			jarPanel.add(horizontalGlue_1);
		}
		{
			horizontalGlue = Box.createHorizontalGlue();
			zipPanel.add(horizontalGlue);
		}
		{
			verticalStrut_4 = Box.createVerticalStrut(20);
			contentPanel.add(verticalStrut_4);
		}
		{
			exportLogPanel = new JPanel();
			exportLogPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			contentPanel.add(exportLogPanel);
			exportLogPanel.setLayout(new BoxLayout(exportLogPanel, BoxLayout.X_AXIS));
			{
				scrollPane = new JScrollPane();
				scrollPane.setBackground(Color.LIGHT_GRAY);
				scrollPane.setViewportBorder(new TitledBorder(null, "Export log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				exportLogPanel.add(scrollPane);
				{
					logText = new JTextPane();
					logText.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(logText);
				}
			}
		}
		{
			exportProgressPanel = new JPanel();
			exportProgressPanel.setVisible(false);
			exportProgressPanel.setPreferredSize(new Dimension(400, 30));
			exportProgressPanel.setMinimumSize(new Dimension(400, 30));
			exportProgressPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			exportProgressPanel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(exportProgressPanel);
			exportProgressPanel.setLayout(new BoxLayout(exportProgressPanel, BoxLayout.X_AXIS));
			{
				progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				exportProgressPanel.add(progressBar);
			}
		}
		
		FrameTools.setAsExample(lblStarterClassPackage, lblMainProjectPath, lblStarterClassName, lblAndroidProjectPath, lblDesktopProjectPath, lblExportDirPath, exportJarCheckBox, exportZipCheckBox);
		setSize(FrameTools.getQuarterOfScreen());
		FrameTools.situateOnCenter(this);
		loadSettings();
	}

	class BuildListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exportProgressPanel.setVisible(true);
			pack();
			saveSettings();
			if (!(exportJarCheckBox.isSelected() || exportZipCheckBox.isSelected())) {
				exportProgressPanel.setVisible(false);
				JOptionPane.showMessageDialog(null, "Please select zip and/or jar export option!");
			} else {
				buildHere();
			}
			pack();
		}
		
		private void buildHere() {
			FileUtils.clearDirectory(getDesktopBldDir());
			setupAntFile();
			runAnt();
		}
		
		private void setupAntFile() {
			FileStubMaker fMaker = new FileStubMaker();
			fMaker.loadFileFromRes("xml/ant/desktop/build.xml");
			fMaker.set("jarDir", exportDirPath.getText());
			if (exportZipCheckBox.isSelected()) {
				if (exportJarCheckBox.isSelected()) {
					fMaker.set("addTarget", ", build.zip");
				} else {
					fMaker.set("addTarget", ", build.zip, remove.created.jar");
				}
			} else {
				fMaker.set("addTarget", "");
			}
			
			if (jarNameTextField.getText().isEmpty()) {
				jarNameTextField.setText("game");
			}
			fMaker.set("jarFile", jarNameTextField.getText() + ".jar");

			if (zipNameTextField.getText().isEmpty()) {
				zipNameTextField.setText("game");
			}
			fMaker.set("zipFile", zipNameTextField.getText() + ".zip");
			
			fMaker.set("mainClass", startPackageText.getText() + "." + startClassName.getText());
			fMaker.set("androidProject", androidProjectPath.getText());
			fMaker.set("desktopProject", desktopProjectPath.getText());

			fMaker.set("fileset", getFilesets());
			fMaker.set("zipfileset", getZipfilesets());
			
			fMaker.saveToFile(getDesktopBldDir() + "/build.xml");
		}
		
		private String getFilesets() {
			String result = "";
			result += "\t\t\t\t<fileset dir=\"../" + desktopProjectPath.getText() + "/bin\" includes=\"**/*.class\"" + "/>\n";
			result += "\t\t\t\t<fileset dir=\"../bin\" includes=\"**/*.class\"" + "/>";
			return result;
		}
		
		private String getZipfilesets() {
			String result = "";
			Array<File> jarFiles = new Array<File>();
			jarFiles.addAll(getDesktopJarFiles());
			jarFiles.addAll(getMainJarFiles());
			
			String tab4 = "\t\t\t\t";
			for(File jarFile : getDesktopJarFiles()) {
				result += tab4 + "<zipfileset excludes=\"META-INF/*.SF\" src=\"../" + desktopProjectPath.getText() + "/libs/" + jarFile.getName() +"\"/>\n";
			}
			
			for(File jarFile : getMainJarFiles()) {
				result += tab4 + "<zipfileset excludes=\"META-INF/*.SF\" src=\"../libs/" + jarFile.getName() +"\"/>\n";
			}
			
			return result;
		}
		
		private void runAnt() {
			exportProgressPanel.setVisible(true);

			final Project p = new Project();
			p.init();
			
			File buildFile = new File(getDesktopBldDir(), "build.xml");
			ProjectHelper.configureProject(p, buildFile);
			logText.setText("Begin exporting...");
			p.addBuildListener(new ExportTextListener(logText));
				new Thread() {
					public void run() {
						try {
							p.executeTarget("build.desktop");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Can't export desktop application!");
							logText.setText(e.getMessage());
						}
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								exportProgressPanel.setVisible(false);
								logText.setText(logText.getText() + "\n" + "Export finished.");
								JOptionPane.showMessageDialog(null, "Build finished!");
							}
						});
					};
				}.start();
		}

		private String getDesktopBldDir() {
			return mainProjectPath.getText() + "/bld";
		}
	}
	
	private void loadSettings() {
		Settings s = Settings.getInstance();
		
		startPackageText.setText(s.getString("desktop-starter-package", "<undefined>"));
		startClassName.setText(s.getString("desktop-start-class", "DesktopStarter"));
		desktopProjectPath.setText(ProjectFinder.findDesktopProject());
		mainProjectPath.setText(ProjectFinder.findKernelProject());
		androidProjectPath.setText(ProjectFinder.findAndroidProject());
		exportDirPath.setText(s.getString("desktop-export-directory", "<select path>"));
		
		exportJarCheckBox.setSelected(s.getBoolean("desktop-is-export-jar", true));
		if (exportJarCheckBox.isSelected()) {
			jarNameTextField.setText(s.getString("desktop-jar-name", "game.jar"));
		}
		
		exportZipCheckBox.setSelected(s.getBoolean("desktop-is-export-zip", true));
		if (exportZipCheckBox.isSelected()) {
			zipNameTextField.setText(s.getString("desktop-zip-name", "game.zip"));
		}
		
		new JarCheckListener().actionPerformed(null);
		new ZipCheckListener().actionPerformed(null);
		pack();
	}
	
	private void saveSettings() {
		Settings s = Settings.getInstance();
		s.putString("desktop-starter-package", startPackageText.getText());
		s.putString("desktop-start-class", startClassName.getText());
		s.putString("desktop-export-directory", exportDirPath.getText());
		
		s.putBoolean("desktop-is-export-jar", exportJarCheckBox.isSelected());
		if (exportJarCheckBox.isSelected()) {
			s.putString("desktop-jar-name", jarNameTextField.getText());
		}
		
		s.putBoolean("desktop-is-export-zip", exportZipCheckBox.isSelected());
		if (exportZipCheckBox.isSelected()) {
			if (exportJarCheckBox.isSelected()) {
				s.putString("desktop-zip-name", zipNameTextField.getText());
		
			}
		}
	}
	
	class JarCheckListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exportJarNameLabel.setVisible(exportJarCheckBox.isSelected());
			jarNameTextField.setVisible(exportJarCheckBox.isSelected());
		}
	}
	
	class ZipCheckListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			zipArchiveNameLabel.setVisible(exportZipCheckBox.isSelected());
			zipNameTextField.setVisible(exportZipCheckBox.isSelected());
		}
	}
	
	class SelectExportDirectoryListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Settings s = Settings.getInstance();
			JFileChooser fChooser = new JFileChooser(s.getString("desktop-export-directory", "."));
			fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				exportDirPath.setText(fChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}
	
	class JarNoSourceFilter implements FileFilter {
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(".jar") && pathname.isFile() && !pathname.getName().contains("source");
		}
	}
	
	private File[] getDesktopJarFiles() {
		return new File(desktopProjectPath.getText()+"/libs").listFiles(new JarNoSourceFilter());
	}
	
	private File[] getMainJarFiles() {
		return new File(mainProjectPath.getText()+"/libs").listFiles(new JarNoSourceFilter());
	}
}
