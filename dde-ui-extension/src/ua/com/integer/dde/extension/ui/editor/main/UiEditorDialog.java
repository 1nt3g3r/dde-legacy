package ua.com.integer.dde.extension.ui.editor.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ua.com.integer.dde.extension.localize.Localize;
import ua.com.integer.dde.extension.ui.Actors;
import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.WidgetType;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.MenuCreator;
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.extension.ui.editor.command.CommandProcessor;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.imp.common.CommonPropertiesPanel;
import ua.com.integer.dde.extension.ui.property.PropertyUtils;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;
import ua.com.integer.dde.startpanel.util.ExtensionFilenameFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class UiEditorDialog extends JDialog {
	private static final long serialVersionUID = 8201647974528008553L;
	private final JPanel contentPanel = new JPanel();
	
	@SuppressWarnings("rawtypes")
	private JList actorList;
	
	private LwjglAWTCanvas lCanvas;

	private String fullActorPath;
	
	private CommandProcessor commandProcessor = new CommandProcessor();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UiEditorDialog dialog = new UiEditorDialog();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JTabbedPane tabs;
	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public UiEditorDialog() {
		getContentPane().setBackground(Color.GRAY);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new ActorListCloseListener());

		setTitle("DDE Actor Editor");
		setBounds(100, 100, 671, 326);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		tabs = new JTabbedPane();
		tabs.setBackground(Color.GRAY);
		
		actorsPanel = new JPanel();
		actorsPanel.setBackground(Color.DARK_GRAY);
		tabs.addTab("Actors", actorsPanel);
	
		propertyPanel = new JPanel();
		propertyPanel.setBackground(Color.GRAY);
		tabs.addTab("Properties", propertyPanel);
		tabs.setBackgroundAt(1, Color.GRAY);
		propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.X_AXIS));
		
		propertyScroll = new JScrollPane();
		propertyScroll.getViewport().setBackground(Color.GRAY);
		propertyScroll.setForeground(Color.GRAY);
		propertyScroll.setBackground(Color.GRAY);
		propertyScroll.getViewport().setForeground(Color.GRAY);
		propertyPanel.add(propertyScroll);
		
		actorsPanel.setBorder(null);
		
		screenOptionsPanel = new JPanel();
		screenOptionsPanel.setBackground(Color.GRAY);
		screenOptionsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		actorHierarchy = new JPanel();
		actorHierarchy.setBackground(Color.DARK_GRAY);
		actorHierarchy.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Actor hierarchy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		screenPanel = new JPanel();
		screenPanel.setBackground(Color.BLACK);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(tabs, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(screenPanel, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(actorHierarchy, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE))
				.addComponent(screenOptionsPanel, GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabs, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(actorHierarchy, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(screenPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(screenOptionsPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
		);
		
		commandLine = new JTextField();
		commandLine.addKeyListener(new CommandListener());
		commandLine.addMouseListener(new CommandListener());
		commandLine.setFont(new Font("Dialog", Font.BOLD, 14));
		commandLine.setText("Enter command and press Enter...");
		commandLine.setForeground(Color.GREEN);
		commandLine.setBackground(Color.DARK_GRAY);
		
		scrollPane_1 = new JScrollPane();
		GroupLayout gl_screenOptionsPanel = new GroupLayout(screenOptionsPanel);
		gl_screenOptionsPanel.setHorizontalGroup(
			gl_screenOptionsPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(commandLine, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
				.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
		);
		gl_screenOptionsPanel.setVerticalGroup(
			gl_screenOptionsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_screenOptionsPanel.createSequentialGroup()
					.addComponent(commandLine, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
		);
		
		outputText = new JTextArea();
		outputText.setForeground(Color.BLUE);
		outputText.setBackground(Color.LIGHT_GRAY);
		outputText.setText("Some\nmultiline\ntext");
		outputText.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
		scrollPane_1.setViewportView(outputText);
		screenOptionsPanel.setLayout(gl_screenOptionsPanel);
		screenPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		actorTree = new JTree();
		actorTree.setBackground(Color.LIGHT_GRAY);
		actorTree.addMouseListener(new TreeNodeClickListener());
		actorTree.addTreeSelectionListener(new ActorTreeNodeSelectionListener());
		JScrollPane treeScroll = new JScrollPane(actorTree);
		
		GroupLayout gl_actorHierarchy = new GroupLayout(actorHierarchy);
		gl_actorHierarchy.setHorizontalGroup(
			gl_actorHierarchy.createParallelGroup(Alignment.LEADING)
				.addComponent(treeScroll, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
		);
		gl_actorHierarchy.setVerticalGroup(
			gl_actorHierarchy.createParallelGroup(Alignment.LEADING)
				.addComponent(treeScroll, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
		);
		actorHierarchy.setLayout(gl_actorHierarchy);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.GRAY);
		
		actorList = new JList();
		actorList.setBackground(Color.LIGHT_GRAY);
		actorList.addListSelectionListener(new ActorSelectedListener());
		scrollPane.setViewportView(actorList);
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBackground(Color.GRAY);
		actionsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton createActorButton = new JButton("New actor");
		createActorButton.setBackground(Color.LIGHT_GRAY);
		createActorButton.addActionListener(new CreateActorListener());
		
		JButton saveActorButton = new JButton("Save");
		saveActorButton.setBackground(Color.LIGHT_GRAY);
		saveActorButton.addActionListener(new SaveActorListener());
		
		JButton deleteActorButton = new JButton("Delete");
		deleteActorButton.setBackground(Color.LIGHT_GRAY);
		deleteActorButton.addActionListener(new DeleteActorListener());
		
		JButton saveActorAsButton = new JButton("Save as...");
		saveActorAsButton.setBackground(Color.LIGHT_GRAY);
		saveActorAsButton.addActionListener(new SaveActorAsListener());
		GroupLayout gl_actionsPanel = new GroupLayout(actionsPanel);
		gl_actionsPanel.setHorizontalGroup(
			gl_actionsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_actionsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_actionsPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(saveActorAsButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(createActorButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(gl_actionsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_actionsPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(saveActorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_actionsPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(deleteActorButton)))
					.addContainerGap(251, Short.MAX_VALUE))
		);
		gl_actionsPanel.setVerticalGroup(
			gl_actionsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_actionsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_actionsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(createActorButton)
						.addComponent(saveActorButton))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_actionsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveActorAsButton)
						.addComponent(deleteActorButton))
					.addContainerGap())
		);
		actionsPanel.setLayout(gl_actionsPanel);
		GroupLayout gl_actorsPanel = new GroupLayout(actorsPanel);
		gl_actorsPanel.setHorizontalGroup(
			gl_actorsPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
				.addComponent(actionsPanel, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
		);
		gl_actorsPanel.setVerticalGroup(
			gl_actorsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_actorsPanel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(actionsPanel, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
		);
		actorsPanel.setLayout(gl_actorsPanel);
		contentPanel.setLayout(gl_contentPanel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);
		
		mnUi = new JMenu("UI");
		menuBar.add(mnUi);
		
		langMenu = new JMenu("Localize language");
		mnUi.add(langMenu);
		
		mnView = new JMenu("View");
		menuBar.add(mnView);
		
		mnViewActorsAndProperties = new JCheckBoxMenuItem("Actors & properties");
		mnView.add(mnViewActorsAndProperties);
		
		mnViewControlPanel = new JCheckBoxMenuItem("Control panel");
		mnView.add(mnViewControlPanel);
		
		mnViewActorHierarchy = new JCheckBoxMenuItem("Actor hierarchy");
		mnView.add(mnViewActorHierarchy);
		
		JSeparator separator = new JSeparator();
		mnView.add(separator);
		
		mnViewHiglightSelectedActor = new JCheckBoxMenuItem("Higlight selected actor");
		mnView.add(mnViewHiglightSelectedActor);
		
		mnViewHiglightInactiveActors = new JCheckBoxMenuItem("Higlight inactive actors");
		mnView.add(mnViewHiglightInactiveActors);
		
		separator_1 = new JSeparator();
		mnView.add(separator_1);
		
		mnViewBackground = new JMenu("Background");
		mnView.add(mnViewBackground);
		
		mnViewChooseBackground = new JMenuItem("Select...");
		mnViewChooseBackground.addActionListener(new SelectBackgroundImageListener());
		mnViewBackground.add(mnViewChooseBackground);
		
		mnViewShowBackground = new JCheckBoxMenuItem("Show background");
		mnViewShowBackground.addActionListener(new ShowBackgroundImageListener());
		mnViewBackground.add(mnViewShowBackground);
		
		mnInsert = new JMenu("Insert");
		setupInsertWidgetMenu();
		menuBar.add(mnInsert);
		
		mnResolution = new JMenu("Resolution");
		menuBar.add(mnResolution);
		
		mnResolution800x480 = new JMenuItem("800x480");
		mnResolution800x480.addActionListener(new ChangeResolutionListener("800x480"));
		mnResolution.add(mnResolution800x480);
		
		mnViewControlPanel.addActionListener(new ShowControlPanelClickListener());
		mnViewActorHierarchy.addActionListener(new ShowActorHierarchyClickListener());
		
		mnViewActorsAndProperties.addActionListener(new ShowActorListClickListener());

		loadResolutions();
		//TODO если нужно редактировать с помощью Swing Designer, закомментировать эти строки
		setupFullActorPath(); 
		updateActorList();
		insertCanvas();
		loadUiSettings();
		
		FrameTools.situateOnCenter(this);
	}
	
	private void loadResolutions() {
		mnResolution.removeAll();
		String[] multipliers = {
			"precise",
			"half",
			"maximize"
		};
		
		ButtonGroup group = new ButtonGroup();
		for(String multiplier: multipliers) {
			for(String resolution: Constants.RESOLUTIONS) {
				String desiredResolution = resolution + " " + multiplier;
				JRadioButtonMenuItem resolutionItem = new JRadioButtonMenuItem(desiredResolution);
				resolutionItem.addActionListener(new ChangeResolutionListener(desiredResolution));
				mnResolution.add(resolutionItem);
				group.add(resolutionItem);
			}
			
			mnResolution.addSeparator();
		}
		
		JMenuItem maximizeMenuItem = new JMenuItem("Maximize");
		maximizeMenuItem.addActionListener(new ChangeResolutionListener("0x0 fullscreen"));
		mnResolution.add(maximizeMenuItem);
	}
	
	private void setupInsertWidgetMenu() {
		mnInsert.add(MenuCreator.getInstance().createAddWidgetMenu("Simple", WidgetType.SIMPLE_WIDGETS));
		mnInsert.add(MenuCreator.getInstance().createAddWidgetMenu("Container", WidgetType.CONTAINER_WIDGETS));
		mnInsert.add(MenuCreator.getInstance().createAddWidgetMenu("Other", WidgetType.OTHER_WIDGETS));
	}
	
	class LanguageClickListener implements ActionListener {
		private String language;
		
		public LanguageClickListener(String language) {
			this.language = language;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Localize.getInstance().setLanguage(language);
			getEditorScreen().updateConfig();
		}
		
	};
	private void fillLocalizeMenuLanguages() {
		for(String lang : Localize.getInstance().getLanguages()) {
			JMenuItem langItem = new JMenuItem(lang);
			langItem.addActionListener(new LanguageClickListener(lang));
			langMenu.add(langItem);
		}
	}
	
	private void insertCanvas() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 400;
		lCanvas = new LwjglAWTCanvas(EditorKernel.getInstance());
		
		screenPanel.add(lCanvas.getCanvas());
		EditorKernel.getInstance().setActorListDialog(this);
		
		Gdx.app.postRunnable(new Runnable() {
			public void run() {
				fillLocalizeMenuLanguages();
			}
		});
	}
	
	private void setupFullActorPath() {
		if (ProjectFinder.findAndroidProject() != null) {
			fullActorPath = ProjectFinder.findAndroidProject() + "/assets/" + Actors.ACTOR_DIRECTORY;
		} else {
			fullActorPath = Actors.ACTOR_DIRECTORY;
		}
		
		File actorsDirectory = new File(fullActorPath);
		if (!actorsDirectory.exists()) {
			if (!actorsDirectory.mkdirs()) {
				JOptionPane.showMessageDialog(null, "Can not create " + fullActorPath + " directory!");
				dispose();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateActorList() {
		actorList.setModel(new ActorListModel(getActorFiles()));
	}
	
	public Array<File> getActorFiles() {
		Array<File> toReturn = new Array<File>();
		for(File file : new File(fullActorPath).listFiles(new ExtensionFilenameFilter("actor"))) {
			toReturn.add(file);
		}
		toReturn.sort();
		return toReturn;
	}
	
	/**
	 * Слушатель на создание актера. Проверяем, чтобы 
	 * имя не было пустое и чтобы актер с таким именем не существовал
	 * 
	 * @author 1nt3g3r
	 */
	class CreateActorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actorName = JOptionPane.showInputDialog("Input actor name:");
			if (actorName == null) return;
			
			if (actorName.equals("")) {
				JOptionPane.showMessageDialog(null, "Name can not be empty!");
			} else {
				if (getActorModel().containsName(actorName)) {
					JOptionPane.showMessageDialog(null, "Actor <" + actorName + "> exists!");
				} else {
					UiConfig config = new UiConfig();
					config.name = "root";
					String filename = fullActorPath + "/" + actorName + "." + Actors.ACTOR_EXTENSION;
					config.saveToFile(new File(filename));
					if (!new File(filename).exists()) {
						JOptionPane.showMessageDialog(null, "Error during save actor <" + actorName + ">");
					} else {
						updateActorList();
						actorList.setSelectedValue(actorName, true);
						openSelectedActorConfig();
					}
				}
			}
		}
	}
	
	private ActorListModel getActorModel() {
		return (ActorListModel) actorList.getModel();
	}
	
	/**
	 * Слушатель на удаление актера
	 * 
	 * @author 1nt3g3r
	 */
	class DeleteActorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelectedActorFile() != null) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete actor", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						getSelectedActorFile().delete();
						updateActorList();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Error during deleting actor!");
					}
				};
			}
		}
	};
	
	private File getSelectedActorFile() {
		if (actorList.getSelectedIndex() < 0) {
			return null;
		} else {
			return getActorModel().getFileAt(actorList.getSelectedIndex());
		}
	}
	
	private File previousActorFile;
	private JTree actorTree;
	public JPanel screenPanel;
	private JPanel actorsPanel;
	/**
	 * Слушатель на выбор определенного актера
	 * @author integer
	 *
	 */
	class ActorSelectedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				return;
			}
			
			openSelectedActorConfig();
		}
	}
	
	/**
	 * Открываем выбранного актера, перемещаем корневого (root) актера в 
	 * точку (0, 0)
	 */
	private void openSelectedActorConfig() {
		try {
			getSelectedActorConfig();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Incorrect config!");
			return;
		}
		
		if (previousActorFile != null) {
			getCurrentUiConfig().saveToFile(previousActorFile);
		}
		
		showUiConfig(getSelectedActorConfig());
		
		previousActorFile = getSelectedActorFile();
		
		getEditorScreen().getStage().getRoot().setPosition(0, 0);
		
		EditorKernel.getInstance().getActorListDialog().updateActorTree();
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).selectRoot();
	}
	
	private UiConfig getSelectedActorConfig() {
		return UiConfig.fromFile(getSelectedActorFile());
	}
	
	class SaveActorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveCurrentUiConfig();
		}
	}
	
	class SaveActorAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newActorName = JOptionPane.showInputDialog("Input new actor name:");
			if (newActorName == null) return;
			
			if (newActorName.equals("")) {
				JOptionPane.showMessageDialog(null, "Actor name can not be empty!");
			} else {
				if (getActorModel().containsName(newActorName)) {
					int answer = JOptionPane.showConfirmDialog(null, "Actor <" + newActorName + "> exists. Override?", "Override", JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						saveCurrentConfigAs(newActorName);
					}
				} else {
					saveCurrentConfigAs(newActorName);
				}
				updateActorList();
			}
		}
		
		private void saveCurrentConfigAs(String actorName) {
			String newActorFilename = fullActorPath + "/" + actorName + "." + Actors.ACTOR_EXTENSION;
			getCurrentUiConfig().saveToFile(new File(newActorFilename));
			if (!new File(newActorFilename).exists()) {
				JOptionPane.showMessageDialog(null, "Can't save actor <" + actorName + ">");
			}
		}
	}
	
	public void saveCurrentUiConfig() {
		getCurrentUiConfig().saveToFile(getSelectedActorFile());
	}
	
	private UiConfig getCurrentUiConfig() {
		return EditorKernel.getInstance().getScreen(UiEditorScreen.class).getUiConfig();
	}
	
	private void showUiConfig(final UiConfig config) {
		EditorKernel.getInstance().getScreen(UiEditorScreen.class).setConfig(config);
	}
	public JTree getActorTree() {
		return actorTree;
	}
	
	private ObjectMap<UiConfig, DefaultMutableTreeNode> configNodes = new ObjectMap<UiConfig, DefaultMutableTreeNode>();
	public JPanel screenOptionsPanel;
	public JPanel actorHierarchy;
	private JPanel propertyPanel;
	private JScrollPane propertyScroll;
	private JMenu mnUi;
	private JMenu langMenu;
	private JMenu mnView;
	private JCheckBoxMenuItem mnViewActorsAndProperties;
	private JCheckBoxMenuItem mnViewControlPanel;
	private JCheckBoxMenuItem mnViewActorHierarchy;
	private JMenu mnInsert;
	private JCheckBoxMenuItem mnViewHiglightSelectedActor;
	private JCheckBoxMenuItem mnViewHiglightInactiveActors;
	private JSeparator separator_1;
	private JMenu mnViewBackground;
	private JMenuItem mnViewChooseBackground;
	private JCheckBoxMenuItem mnViewShowBackground;
	private JMenu mnResolution;
	private JMenuItem mnResolution800x480;
	private JTextField commandLine;
	private JScrollPane scrollPane_1;
	private JTextArea outputText;
	
	private void addUiConfigIntoNode(UiConfig config, DefaultMutableTreeNode node) {
		DefaultMutableTreeNode nameNode = new DefaultMutableTreeNode(config);
		nameNode.setUserObject(config);
		configNodes.put(config, nameNode);
		node.add(nameNode);
		
		for(UiConfig child : config.children) {
			addUiConfigIntoNode(child, nameNode);
		}
		
	}

	/**
	 * Слушатель на выделение узла дерева. Если выделили узел, 
	 * выделяем актера, который соответсвует этому узлу
	 * 
	 * @author 1nt3g3r
	 */
	class ActorTreeNodeSelectionListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = ((DefaultMutableTreeNode) e.getPath().getLastPathComponent());
			if (node.getUserObject() != null) {
				EditorKernel.getInstance().getScreen(UiEditorScreen.class).selectActorByConfig((UiConfig) node.getUserObject());
			}
		}
	}
	
	class SetConfigRunnable implements Runnable{
		private UiConfig config;
		private ConfigEditor configEditor;
		
		public SetConfigRunnable(UiConfig config, ConfigEditor editor) {
			this.config = config;
			this.configEditor = editor;
		}

		@Override
		public void run() {
			configEditor.setConfig(config);
		}
	};
	
	public void updatePropertyPanelForSelectedActor() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final UiConfig config = EditorKernel.getInstance().getScreen(UiEditorScreen.class).getSelectedConfig();
				
				CommonPropertiesPanel commonEditor = new CommonPropertiesPanel();
				Gdx.app.postRunnable(new SetConfigRunnable(config, commonEditor));
				
				ConfigEditor specificEditor;
				
				try {
					specificEditor = (ConfigEditor) PropertyUtils.getSupporter(config.widgetType).createSetupPanel(config, null);
					Gdx.app.postRunnable(new SetConfigRunnable(config, specificEditor));
				} catch (Exception ex) {
					specificEditor = null;
				}

				int totalWidth = commonEditor.getMaximumSize().width;
				int totalHeight = commonEditor.getMaximumSize().height;
				
				if (specificEditor != null) {
					totalHeight += specificEditor.getMaximumSize().height;
				}
				
				Dimension size = new Dimension(totalWidth, totalHeight);
				
				JPanel resultPanel = new JPanel();
				resultPanel.setBackground(Color.GRAY);
				resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
				resultPanel.add(commonEditor);
				if (specificEditor != null) {
					resultPanel.add(specificEditor);
				}
				
				resultPanel.setPreferredSize(size);
				resultPanel.setMaximumSize(size);
				resultPanel.setMinimumSize(size);
				
				propertyScroll.setViewportView(resultPanel);
				propertyScroll.getVerticalScrollBar().setUnitIncrement(20);
				propertyScroll.setAutoscrolls(true);
				
				propertyScroll.revalidate();
			}
		});
	}
	
	class TreeNodeClickListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
				JPopupMenu menu = MenuCreator.getInstance().createMenu(getEditorScreen().getSelectedActor(), (UiConfig) getEditorScreen().getSelectedActor().getUserObject());
				menu.show(UiEditorDialog.this, getMousePosition().x, getMousePosition().y);
			}
		}
	}
	
	private UiEditorScreen getEditorScreen() {
		return EditorKernel.getInstance().getScreen(UiEditorScreen.class);
	}
	
	public void updateActorTree(UiConfig config) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		
		configNodes.clear();
		addUiConfigIntoNode(config, root);
		
		actorTree.setModel(new DefaultTreeModel(root.getFirstChild()));
		
		for(int i = 0; i < actorTree.getRowCount(); i++) {
			actorTree.expandRow(i);
		}
	}
	
	public void updateActorTree() {
		updateActorTree(getCurrentUiConfig());
	}
	
	class ActorListCloseListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			Settings sets = Settings.getInstance();
			sets.setSettingsClass(UiEditorDialog.class);
			sets.putString("frame-width", getWidth() + "");
			sets.putString("frame-height", getHeight() + "");
			System.exit(0);
		}
	}
	
	class HighlightModeChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Settings sets = Settings.getInstance();
			sets.setSettingsClass(UiEditorScreen.class);
			
			sets.putBoolean("highlight-active-actor", mnViewHiglightSelectedActor.isSelected());
			sets.putBoolean("highlight-inactive-actors", mnViewHiglightInactiveActors.isSelected());
			
			EditorKernel.getInstance().getScreen(UiEditorScreen.class).updateHighlightActorSettings();
		}
	}
	
	private void loadUiSettings() {
		Settings sets = Settings.getInstance();
		sets.setSettingsClass(UiEditorScreen.class);
		
		mnViewHiglightSelectedActor.setSelected(sets.getBoolean("highlight-active-actor", true));
		mnViewHiglightInactiveActors.setSelected(sets.getBoolean("highlight-inactive-actors", true));
		
		HighlightModeChangeListener changeListener = new HighlightModeChangeListener();
		mnViewHiglightInactiveActors.addActionListener(changeListener);
		mnViewHiglightSelectedActor.addActionListener(changeListener);
		
		mnViewActorHierarchy.setSelected(getSettings().getBoolean("show-actor-hierarchy", true));
		mnViewActorsAndProperties.setSelected(getSettings().getBoolean("show-actors-list", true));
		mnViewControlPanel.setSelected(getSettings().getBoolean("show-control-panel", true));
		
		actorHierarchy.setVisible(mnViewActorHierarchy.isSelected());
		tabs.setVisible(mnViewActorsAndProperties.isSelected());
		screenOptionsPanel.setVisible(mnViewControlPanel.isSelected());
		
		int frameWidth = Integer.parseInt(sets.getString("frame-width", "0"));
		int frameHeight = Integer.parseInt(sets.getString("frame-height", "0"));
		if (frameWidth != 0 && frameHeight != 0) {
			setSize(frameWidth, frameHeight);
		}
	}
	
	/**
	 * Слушатель на выбор фонового изображения
	 * 
	 * @author 1nt3g3r
	 */
	class SelectBackgroundImageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Settings sets = Settings.getInstance();
			sets.setSettingsClass(UiEditorScreen.class);
			
			String backgroundPath = sets.getString("background-path", "./");
			
			final JFileChooser fChooser = new JFileChooser(backgroundPath);
			if (fChooser.showOpenDialog(UiEditorDialog.this) == JFileChooser.APPROVE_OPTION) {
				sets.putString("background-path", fChooser.getSelectedFile().getParent());
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						try {
							
							Texture background = new Texture(Gdx.files.absolute(fChooser.getSelectedFile().getAbsolutePath()));

							if(getEditorScreen().getConfig().background != null) {
								getEditorScreen().getConfig().background.getTexture().dispose();
							}
						
							getEditorScreen().setBackground(new TextureRegion(background));
							getEditorScreen().getConfig().needDrawBackgroundImage = true;
							
							mnViewShowBackground.setSelected(true);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Can't load image: " + ex.getMessage());
						}
					}
				});
			};
		}
	}
	
	/**
	 * Слушатель на показ фонового изображения
	 * 
	 * @author 1nt3g3r
	 */
	class ShowBackgroundImageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (mnViewShowBackground.isSelected()) {
				if (getEditorScreen().getConfig().background != null) {
					getEditorScreen().getConfig().needDrawBackgroundImage = true;
				}
			} else {
				getEditorScreen().getConfig().needDrawBackgroundImage = false;
			}
		}
	}
	
	class ShowActorListClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			tabs.setVisible(mnViewActorsAndProperties.isSelected());
			
			getSettings().putBoolean("show-actors-list", mnViewActorsAndProperties.isSelected());
			//TODO resize
		}
	}
	
	class ShowActorHierarchyClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			actorHierarchy.setVisible(mnViewActorHierarchy.isSelected());
			
			getSettings().putBoolean("show-actor-hierarchy", mnViewActorHierarchy.isSelected());
			//TODO resize
		}
	}
	
	/**
	 * Слушатель на показ\скрытие панели управления
	 * 
	 * @author 1nt3g3r
	 */
	class ShowControlPanelClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			screenOptionsPanel.setVisible(mnViewControlPanel.isSelected());
			
			getSettings().putBoolean("show-control-panel", mnViewControlPanel.isSelected());
		}
	}
	
	private Settings getSettings() {
		Settings sets = Settings.getInstance();
		sets.setSettingsClass(UiEditorDialog.class);
		return sets;
	}
	
	public JMenu getLangMenu() {
		return langMenu;
	}
	
	class ChangeResolutionListener implements ActionListener {
		private String resolution;
		
		public ChangeResolutionListener(String resolution) {
			this.resolution = resolution;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String[] parts = resolution.split(" ");
			String[] dimension = parts[0].split("x");
			String multiplier = "precise";
			if (parts.length > 1) {
				multiplier = parts[1];
			}
			sendCommand("res " + dimension[0] + " " + dimension[1] + " " + multiplier);
		}
		
	}
	
	public void setEditorViewportSize(int width, int height) {
		int wholeWindowWidth = getWidth();
		int wholeWindowHeight = getHeight();
		
		int tabWidth = tabs.getWidth();
		
		int hierarchyWidth = actorHierarchy.getWidth();
		
		int editorWidth = screenPanel.getWidth();
		int editorHeight = screenPanel.getHeight();
		
		int controlPanelHeight = screenOptionsPanel.getHeight();

		int borderWidth = wholeWindowWidth - tabWidth - hierarchyWidth - editorWidth;
		int borderHeight = wholeWindowHeight - editorHeight - controlPanelHeight;

		int newWindowWidth = width + borderWidth + tabWidth + hierarchyWidth;
		int newWindowHeight = height + borderHeight + controlPanelHeight;
		setSize(newWindowWidth, newWindowHeight);
	}
	
	class CommandListener extends MouseAdapter implements KeyListener {
		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				String cmd = commandLine.getText().trim();
				sendCommand(cmd);
				commandLine.setText("");
			}
		}
		@Override
		public void keyReleased(KeyEvent event) {
		}
		@Override
		public void keyTyped(KeyEvent event) {
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			commandLine.selectAll();
		}
	}
	
	public void sendCommand(String command) {
		commandProcessor.executeCommand(command, this);
	}
	
	public void setConsoleOutput(String text) {
		outputText.setText(text);
	}
}
