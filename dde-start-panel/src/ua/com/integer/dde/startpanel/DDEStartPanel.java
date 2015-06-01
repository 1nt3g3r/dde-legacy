package ua.com.integer.dde.startpanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.startpanel.ddestub.CreateDDKernelStubDialog;
import ua.com.integer.dde.startpanel.export.AndroidApkExporter;
import ua.com.integer.dde.startpanel.export.DesktopExporter;
import ua.com.integer.dde.startpanel.extension.ExtensionDialog;
import ua.com.integer.dde.startpanel.image.ImageChooser;
import ua.com.integer.dde.startpanel.start.StartRunnable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;

public class DDEStartPanel {

	private JFrame frmDdeControlPanel;
	private JButton launchButton;
	
	private LwjglFrame frame;
	
	private DDKernel kernel;
	private LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	
	private JPanel centerPanel;

	private JPanel runParamsPanel;
	private JButton btnResolution;
	private JPanel resourcesPanel;
	private Component verticalStrut;
	private JButton btnImageExplorer;
	private JPanel exportPanel;
	private Component verticalStrut_1;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JButton exportAndroidButton;
	private Component horizontalGlue_2;
	private Component horizontalStrut;
	private JButton exportPcButton;
	private Component horizontalStrut_1;
	private Component horizontalGlue_3;
	private JMenuBar menuBar;
	private JMenu mnUtilities;
	private JMenuItem mntmMorphToDde;
	
	private static DDEStartPanel instance;
	private JPanel otherPanel;
	private Component verticalStrut_2;
	private JButton btnExtension;
	private Component horizontalGlue_4;
	private JMenu mnDDE;
	private JMenuItem mntmOptions;
	
	public static DDEStartPanel getInstance() {
		if (instance == null) {
			instance = new DDEStartPanel(null);
		}
		return instance;
	}
	
	public static DDEStartPanel getInstance(DDKernel kernel) {
		if (instance == null) {
			instance = new DDEStartPanel(kernel);
		} else {
			instance.kernel = kernel;
		}
		return instance;
	}
	
	public DDKernel getKernel() {
		return kernel;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DDEStartPanel window = new DDEStartPanel(null);
					window.frmDdeControlPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	private DDEStartPanel(DDKernel kernel) {
		this.kernel = kernel;
		if (kernel != null) {
			Settings.getInstance().setSettingsClass(kernel.getClass());
			Settings.getInstance().putString("out-images-directory", 
					kernel.getConfig().relativeDirectory + "/" + kernel.getConfig().packDirectory);
		}
		initialize();
		frmDdeControlPanel.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDdeControlPanel = new JFrame("Dark Dream Control Panel");
		frmDdeControlPanel.setTitle("DDE Control Panel");
		frmDdeControlPanel.setBounds(100, 100, 450, 300);
		frmDdeControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loadConfigFromSettings();
		initBottomPanel();
		
		new Thread(new StartRunnable()).start();
	}
	
	
	
	private void loadConfigFromSettings() {
		if (Settings.getInstance().isNeedFullScreen()) {
			config.fullscreen = true;
		} else {
			config.fullscreen = false;
			if (Settings.getInstance().isLandscapeOrientation()) {
				config.width = Settings.getInstance().getScreenWidth();
				config.height = Settings.getInstance().getScreenHeight();
			} else {
				config.width = Settings.getInstance().getScreenHeight();
				config.height = Settings.getInstance().getScreenWidth();
			}
			if (Settings.getInstance().isNeedScale()) {
				float scaleCoeff = Float.parseFloat(Settings.getInstance().getScale());
				config.width *= scaleCoeff;
				config.height *= scaleCoeff;
			}
			
		}
	}
	
	private void initBottomPanel() {
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.GRAY);
		frmDdeControlPanel.setContentPane(centerPanel);
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		menuBar = new JMenuBar();
		frmDdeControlPanel.setJMenuBar(menuBar);
		
		mnDDE = new JMenu("DDE");
		menuBar.add(mnDDE);
		
		mntmOptions = new JMenuItem("Options");
		mntmOptions.addActionListener(new OptionsListener());
		mnDDE.add(mntmOptions);
		
		mnUtilities = new JMenu("Utilities");
		menuBar.add(mnUtilities);
		
		mntmMorphToDde = new JMenuItem("Morph to DDE...");
		mntmMorphToDde.addActionListener(new MorphToDDEListener());
		mnUtilities.add(mntmMorphToDde);
		
		runParamsPanel = new JPanel();
		runParamsPanel.setBackground(Color.LIGHT_GRAY);
		runParamsPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Launch properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerPanel.add(runParamsPanel);
		runParamsPanel.setLayout(new BoxLayout(runParamsPanel, BoxLayout.X_AXIS));
		
		launchButton = new JButton("Launch");
		launchButton.setIcon(new ImageIcon(DDEStartPanel.class.getResource("/ua/com/integer/dde/res/icon/launch-icon.png")));
		launchButton.setForeground(Color.LIGHT_GRAY);
		launchButton.setFont(new Font("Dialog", Font.BOLD, 20));
		launchButton.setBackground(Color.DARK_GRAY);
		launchButton.setMaximumSize(new Dimension(220, 70));
		runParamsPanel.add(launchButton);
		launchButton.setMinimumSize(new Dimension(220, 70));
		launchButton.setPreferredSize(new Dimension(220, 70));
		launchButton.addActionListener(new LaunchListener());
		
		horizontalGlue = Box.createHorizontalGlue();
		runParamsPanel.add(horizontalGlue);
		
		btnResolution = new JButton("Resolution");
		btnResolution.setIcon(new ImageIcon(DDEStartPanel.class.getResource("/ua/com/integer/dde/res/icon/resolution-icon.png")));
		btnResolution.addActionListener(new ResolutionListener());
		
		horizontalStrut = Box.createHorizontalStrut(10);
		runParamsPanel.add(horizontalStrut);
		btnResolution.setBackground(Color.GRAY);
		btnResolution.setMaximumSize(new Dimension(200, 70));
		btnResolution.setMinimumSize(new Dimension(200, 70));
		btnResolution.setPreferredSize(new Dimension(220, 40));
		runParamsPanel.add(btnResolution);
		
		verticalStrut = Box.createVerticalStrut(20);
		centerPanel.add(verticalStrut);
		
		resourcesPanel = new JPanel();
		resourcesPanel.setBorder(new TitledBorder(null, "Resources", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		resourcesPanel.setBackground(Color.LIGHT_GRAY);
		centerPanel.add(resourcesPanel);
		resourcesPanel.setLayout(new BoxLayout(resourcesPanel, BoxLayout.X_AXIS));
		
		btnImageExplorer = new JButton("Image explorer");
		btnImageExplorer.setIcon(new ImageIcon(DDEStartPanel.class.getResource("/ua/com/integer/dde/res/icon/img-explorer-icon.png")));
		btnImageExplorer.addActionListener(new ImageExplorerListener());
		btnImageExplorer.setBackground(Color.GRAY);
		btnImageExplorer.setMaximumSize(new Dimension(220, 70));
		btnImageExplorer.setMinimumSize(new Dimension(220, 70));
		btnImageExplorer.setPreferredSize(new Dimension(220, 70));
		resourcesPanel.add(btnImageExplorer);
		
		horizontalGlue_1 = Box.createHorizontalGlue();
		resourcesPanel.add(horizontalGlue_1);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setVisible(false);
		centerPanel.add(verticalStrut_1);
		
		exportPanel = new JPanel();
		exportPanel.setVisible(false);
		exportPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Export", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		exportPanel.setBackground(Color.LIGHT_GRAY);
		centerPanel.add(exportPanel);
		exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.X_AXIS));
		
		exportAndroidButton = new JButton("Android");
		exportAndroidButton.setMinimumSize(new Dimension(220, 70));
		exportAndroidButton.setMaximumSize(new Dimension(220, 70));
		exportAndroidButton.addActionListener(new AndroidExportDialogListener());
		exportAndroidButton.setPreferredSize(new Dimension(220, 70));
		exportAndroidButton.setBackground(Color.GRAY);
		exportAndroidButton.setIcon(new ImageIcon(DDEStartPanel.class.getResource("/ua/com/integer/dde/res/icon/android-icon.png")));
		exportPanel.add(exportAndroidButton);
		
		horizontalStrut_1 = Box.createHorizontalStrut(10);
		exportPanel.add(horizontalStrut_1);
		
		horizontalGlue_3 = Box.createHorizontalGlue();
		exportPanel.add(horizontalGlue_3);
		
		exportPcButton = new JButton("Desktop");
		exportPcButton.addActionListener(new DesktopExportDialogListener());
		exportPcButton.setIcon(new ImageIcon(DDEStartPanel.class.getResource("/ua/com/integer/dde/res/icon/pc.png")));
		exportPcButton.setPreferredSize(new Dimension(220, 70));
		exportPcButton.setMinimumSize(new Dimension(220, 70));
		exportPcButton.setMaximumSize(new Dimension(220, 70));
		exportPcButton.setBackground(Color.GRAY);
		exportPanel.add(exportPcButton);
		
		horizontalGlue_2 = Box.createHorizontalGlue();
		exportPanel.add(horizontalGlue_2);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setVisible(false);
		centerPanel.add(verticalStrut_2);
		
		otherPanel = new JPanel();
		otherPanel.setBackground(Color.LIGHT_GRAY);
		otherPanel.setBorder(new TitledBorder(null, "Other", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerPanel.add(otherPanel);
		otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.X_AXIS));
		
		btnExtension = new JButton("Extensions");
		btnExtension.addActionListener(new ExtensionListener());
		btnExtension.setIcon(new ImageIcon(DDEStartPanel.class.getResource("/ua/com/integer/dde/res/icon/extension_icon.png")));
		btnExtension.setPreferredSize(new Dimension(220, 70));
		btnExtension.setMinimumSize(new Dimension(220, 70));
		btnExtension.setMaximumSize(new Dimension(220, 70));
		btnExtension.setBackground(Color.GRAY);
		otherPanel.add(btnExtension);
		
		horizontalGlue_4 = Box.createHorizontalGlue();
		otherPanel.add(horizontalGlue_4);
		
		frmDdeControlPanel.pack();
		FrameTools.situateOnCenter(frmDdeControlPanel);
	}
	
	class ImageExplorerListener implements ActionListener {
		private ImageChooser imChooser;
		
		class OkListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				imChooser.dispose();
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			imChooser = new ImageChooser("../dde-tests/data/raw-img");
			imChooser.updatePacks();
			imChooser.getOkButton().addActionListener(new OkListener());
			imChooser.setVisible(true);
		}
	}
	
	class LaunchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			launch();
		}
	}
	
	private LwjglAWTCanvas lCanvas;
	private JFrame gameFrame;
	/**
	 * Запускает приложение, если оно еще не было запущено
	 */
	public void launch() {
		if (gameFrame == null || !gameFrame.isVisible()) {
			config.resizable = false;
			config.title = kernel.getClass().getSimpleName();
			
			lCanvas = new LwjglAWTCanvas(kernel);
			
			JPanel panelForCanvas = new JPanel();
			panelForCanvas.setLayout(new GridLayout(1, 1));
			panelForCanvas.setSize(config.width, config.height);
			panelForCanvas.add(lCanvas.getCanvas());
			
			gameFrame = new JFrame(config.title);
			gameFrame.setSize(config.width, config.height);
			gameFrame.setContentPane(panelForCanvas);
			
			gameFrame.addWindowListener(new ExitFromGameListener());
			gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			gameFrame.setVisible(true);
		}
	}
	
	class ExitFromGameListener extends WindowAdapter {
		@Override
		public void windowClosing(final WindowEvent e) {
			e.getWindow().setVisible(false);
			
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					AbstractScreen.getKernel().exit();
					lCanvas.stop();
				}
			});
		}
	}
	
	public void launchAsFrame() {
		frame = new LwjglFrame(kernel, config);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * Запущено ли приложение. Ядро должно быть создано и метод create 
	 * в нем должен быть вызван, чтобы данный метод возвратил true
	 */
	public boolean isKernelLaunched() {
		return kernel != null && kernel.isCreated();
	}
	
	class CreateStubListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CreateDDKernelStubDialog dlg = new CreateDDKernelStubDialog();
			dlg.setModal(true);
			dlg.setVisible(true);
		}
	}
	
	class ResolutionListener implements ActionListener {
		private WindowSizeDialog dlg;
		
		class OkayListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				okay();
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dlg = new WindowSizeDialog();
			dlg.setModal(true);
			dlg.getOkButton().addActionListener(new OkayListener());
			dlg.setVisible(true);
		}
		
		private void okay() {
			if (dlg.isFullScreen()) {
				config.fullscreen = true;
				config.width = dlg.getScreenWidth();
				config.height = dlg.getScreenHeight();
				dlg.saveSettings();
				dlg.dispose();
			} else {
				if (dlg.isScreenSizeCorrect()) {
					config.fullscreen = false;
					float w = dlg.getScreenWidth();
					float h = dlg.getScreenHeight();
					if (!dlg.isLandscape()) {
						w = dlg.getScreenHeight();
						h = dlg.getScreenWidth();
					}
					config.width = (int) (w * dlg.getScaleCoeff());
					config.height = (int) (h * dlg.getScaleCoeff());
					dlg.saveSettings();
					dlg.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Please set correct window size!");
				}
			}
		}
	}
	
	class AndroidExportDialogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Dialog dialog =new AndroidApkExporter();
			dialog.setVisible(true);
		}
	}
	
	class DesktopExportDialogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Dialog dialog =new DesktopExporter();
			dialog.setVisible(true);
		}
	}
	
	class MorphToDDEListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CreateDDKernelStubDialog dlg = new CreateDDKernelStubDialog();
			dlg.setModal(true);
			dlg.setVisible(true);
		}
	}
	
	class ExtensionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ExtensionDialog dlg = new ExtensionDialog();
			dlg.setModal(true);
			dlg.setVisible(true);
		}
	}
	
	class OptionsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DDEOptions options = new DDEOptions();
			options.setModal(true);
			options.setVisible(true);
		}
	}
	
	/**
	 * Используется ли панель управления - по сути, был ли 
	 * вызван метод getInstance(kernel)
	 * @return
	 */
	public static boolean isInitialized() {
		return instance != null;
	}
}
