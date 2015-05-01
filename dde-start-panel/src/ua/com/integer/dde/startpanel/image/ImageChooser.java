package ua.com.integer.dde.startpanel.image;

import static ua.com.integer.dde.startpanel.image.ImageUtils.setSelectedImageAsIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ua.com.integer.dde.startpanel.DDEStartPanel;
import ua.com.integer.dde.startpanel.FileUtils;
import ua.com.integer.dde.startpanel.Settings;
import ua.com.integer.dde.startpanel.util.AtlasFilenameFilter;
import ua.com.integer.dde.util.JsonWorker;

public class ImageChooser extends JDialog {
	private static final long serialVersionUID = 3680788059259165557L;
	private final JPanel contentPanel = new JPanel();
	private JList<String> packList;
	private JList<String> imageList;
	private JButton previewImageButton;
	private JButton okButton;
	
	private File imageFile;
	private JButton customImageButton;
	
	private String imageDirectory;
	
	private List<String> selectedPacks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ImageChooser dialog = new ImageChooser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImageChooser() {
		this(ImageUtils.DEF_RAW_IMAGES_PATH);
	}
	
	/**
	 * Create the dialog.
	 */
	public ImageChooser(String imageDirectory) {
		this.imageDirectory = imageDirectory;
		setTitle("DDE Image Chooser");
		setBounds(100, 100, 650, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			JPanel packPanel = new JPanel();
			packPanel.setBackground(Color.LIGHT_GRAY);
			packPanel.setBorder(new TitledBorder(null, "Image atlases", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(packPanel);
			packPanel.setLayout(new BoxLayout(packPanel, BoxLayout.X_AXIS));
			{
				JScrollPane scrollPane = new JScrollPane();
				packPanel.add(scrollPane);
				{
					packList = new JList<String>();
					packList.addListSelectionListener(new PackSelectedListener());
					packList.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(packList);
				}
			}
		}
		{
			JPanel imagePanel = new JPanel();
			imagePanel.setBackground(Color.LIGHT_GRAY);
			imagePanel.setBorder(new TitledBorder(null, "Images in the atlas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(imagePanel);
			imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
			{
				JScrollPane scrollPane = new JScrollPane();
				imagePanel.add(scrollPane);
				{
					imageList = new JList<String>();
					imageList.addListSelectionListener(new ImageSelectedListener());
					imageList.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(imageList);
				}
			}
		}
		{
			JPanel previewPanel = new JPanel();
			previewPanel.setBackground(Color.LIGHT_GRAY);
			previewPanel.setMinimumSize(new Dimension(200, 200));
			previewPanel.setMaximumSize(new Dimension(200, 1000));
			previewPanel.setPreferredSize(new Dimension(200, 200));
			previewPanel.setBorder(new TitledBorder(null, "Image preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(previewPanel);
			previewPanel.setLayout(new BorderLayout(0, 0));
			{
				previewImageButton = new JButton("");
				previewImageButton.addActionListener(new CopyPackToClipboardListener());
				previewImageButton.setBackground(Color.LIGHT_GRAY);
				previewImageButton.setPreferredSize(new Dimension(200, 200));
				previewImageButton.setMinimumSize(new Dimension(200, 200));
				previewImageButton.setMaximumSize(new Dimension(200, 200));
				previewPanel.add(previewImageButton, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
			{
				JButton refreshButton = new JButton("Refresh");
				refreshButton.setBackground(Color.LIGHT_GRAY);
				refreshButton.setPreferredSize(new Dimension(200, 30));
				refreshButton.addActionListener(new RefreshListener());
				buttonPane.add(refreshButton);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				buttonPane.add(horizontalStrut);
			}
			{
				customImageButton = new JButton("Custom image");
				customImageButton.setMaximumSize(new Dimension(200, 25));
				customImageButton.setMinimumSize(new Dimension(200, 25));
				customImageButton.setBackground(Color.LIGHT_GRAY);
				customImageButton.setPreferredSize(new Dimension(200, 30));
				customImageButton.addActionListener(new CustomImageButtonListener());
				buttonPane.add(customImageButton);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane.add(horizontalGlue);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				buttonPane.add(horizontalStrut);
			}
			{
				{
					okButton = new JButton("OK");
					okButton.setBackground(Color.LIGHT_GRAY);
					okButton.setPreferredSize(new Dimension(140, 30));
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
			}
		}
		{
			JPanel actionPanel = new JPanel();
			actionPanel.setBackground(Color.GRAY);
			actionPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			getContentPane().add(actionPanel, BorderLayout.NORTH);
			actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
			{
				JButton switchRawDirectory = new JButton("Switch image directory");
				switchRawDirectory.setMinimumSize(new Dimension(200, 25));
				switchRawDirectory.setMaximumSize(new Dimension(200, 25));
				switchRawDirectory.setBackground(Color.LIGHT_GRAY);
				switchRawDirectory.addActionListener(new SwitchRawPackImageDirectoryListener());
				{
					JButton packProperties = new JButton("Pack properties");
					packProperties.setBackground(Color.LIGHT_GRAY);
					packProperties.addActionListener(new PackPropertiesListener());
					packProperties.setMinimumSize(new Dimension(200, 30));
					packProperties.setPreferredSize(new Dimension(200, 30));
					actionPanel.add(packProperties);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(20);
					actionPanel.add(horizontalStrut);
				}
				switchRawDirectory.setPreferredSize(new Dimension(200, 30));
				actionPanel.add(switchRawDirectory);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				actionPanel.add(horizontalStrut);
			}
			{
				JButton btnPackAll = new JButton("Pack'em all");
				btnPackAll.setBackground(Color.LIGHT_GRAY);
				btnPackAll.addActionListener(new PackEmAllListener());
				btnPackAll.setPreferredSize(new Dimension(150, 30));
				actionPanel.add(btnPackAll);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				actionPanel.add(horizontalStrut);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				actionPanel.add(horizontalGlue);
			}
			{
				JButton btnPackSelected = new JButton("Pack selected");
				btnPackSelected.setBackground(Color.LIGHT_GRAY);
				btnPackSelected.addActionListener(new PackCustomListener());
				btnPackSelected.setPreferredSize(new Dimension(140, 30));
				actionPanel.add(btnPackSelected);
			}
		}
		loadSettings();
		pack();
	}
	
	private void loadSettings() {
		if (DDEStartPanel.isInitialized() && DDEStartPanel.getInstance().getKernel() != null) Settings.getInstance().setSettingsClass(DDEStartPanel.getInstance().getKernel().getClass());
		
		imageDirectory = Settings.getInstance().getString("raw-images-directory", "./");
		if (!new File(imageDirectory).exists()) {
			JOptionPane.showMessageDialog(null, "Can't find raw images directory! Set as ./");
			imageDirectory = "./";
		}
		updatePacks();
	}
	
	public void setCustomImageButtonVisible(boolean visible) {
		customImageButton.setVisible(visible);
	}
	
	private File[] getImageDirectories() {
		return new File(imageDirectory).listFiles(new ImagesDirectoryFilter());
	}
	
	private File[] getPackDirectories() {
		File[] imageDirectories = getImageDirectories();
		if (imageDirectories.length > 0) {
			return getImageDirectories()[0].listFiles(new AtlasFilenameFilter());
		} else {
			return new File[0];
		}
	}
	
	class ImageFileFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.getName().endsWith(".png") || file.getName().endsWith(".jpg");
		}
	}
	
	private File[] getImages(File file) {
		return file.listFiles(new ImageFileFilter());
	}
	
	public void updatePacks() {
		packList.setModel(new PackListModel(getPackDirectories()));
		imageList.setModel(new ImageListModel(new File[]{}));
	}
	
	class PackSelectedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedIndex = packList.getSelectedIndex();
			if (selectedIndex >= 0) {
				PackListModel model = (PackListModel) packList.getModel();
				imageList.setModel(new ImageListModel(getImages(model.getFileAt(selectedIndex))));
			}
		}
	}
	
	class ImageSelectedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedIndex = imageList.getSelectedIndex();
			if (selectedIndex < 0) {
				return;
			}
			
			ImageListModel model = (ImageListModel) imageList.getModel();
			imageFile = model.getFileAt(selectedIndex);
			setSelectedImageAsIcon(imageFile, previewImageButton);
		}
	}
	
	public void copyPackAndImageToClipboard() {
		if (!packAndRegionSelected()) {
			return;
		}
		
		String pack = "\"" + packList.getSelectedValue().toString() + "\"";
		String image = "\"" + imageList.getSelectedValue().toString() + "\"";
		String text = pack + ", " + image;
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public boolean packAndRegionSelected() {
		return packList.getSelectedIndex() >= 0 && imageList.getSelectedIndex() >= 0;
	}
	
	public String getPack() {
		return packList.getSelectedValue().toString();
	}
	
	public String getRegion() {
		return imageList.getSelectedValue().toString();
	}
	
	public File getSelectedImageFile() {
		return imageFile;
	}
	
	class RefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			updatePacks();
			previewImageButton.setIcon(null);
		}
	}
	
	class CustomImageButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Settings sets = Settings.getInstance();
			JFileChooser fileChooser = new JFileChooser(new File(sets.getLastCustomImageDirectory()));
			if (fileChooser.showOpenDialog(ImageChooser.this) == JFileChooser.APPROVE_OPTION) {
				imageFile = fileChooser.getSelectedFile();
				setSelectedImageAsIcon(imageFile, previewImageButton);
				try {
					sets.setLastCustomImageDirectory(imageFile.getCanonicalPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	class CopyPackToClipboardListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			copyPackAndImageToClipboard();
		}
	}
	
	class SwitchRawPackImageDirectoryListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fChooser = new JFileChooser(new File(Settings.getInstance().getString("raw-images-directory", "./")));
			fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fChooser.showOpenDialog(ImageChooser.this) == JFileChooser.APPROVE_OPTION) {
				Settings.getInstance().putString("raw-images-directory", fChooser.getSelectedFile().getAbsolutePath());
				imageDirectory = Settings.getInstance().getString("raw-images-directory", ".");
				updatePacks();
			}
		}
	}
	
	class PackEmAllListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			FileUtils.clearDirectory(Settings.getInstance().getString("out-images-directory", "./data/img"));
			TexturePacker packer = new TexturePacker(imageDirectory);
			packer.packAll();
		}
	}
	
	class PackCustomListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (packList.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "Select pack!");
				return;
			}
			String selectedPack = packList.getSelectedValue().toString();
			TexturePacker packer = new TexturePacker(imageDirectory);
			packer.packSelected(selectedPack);
		}
	}
	
	class PackPropertiesListener implements ActionListener {
		private PackSettingsEditor packEditor;
		private AllowedDirectoriesDialog allowedPacksDialog;
		
		class OkListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveSettingsToAllPacks(imageDirectory, packList.getSelectedValue().toString(), packEditor.getSettings());
				packEditor.dispose();
			}	
		}
		
		class AllowedDirectoriesOkDialogListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				allowedPacksDialog.dispose();
				selectedPacks = allowedPacksDialog.getSelelectedDirectories();
				
				if (selectedPacks.size() == 0) {
					JOptionPane.showMessageDialog(null, "Select at least one directory!");
					return;
				}
				
				packEditor = new PackSettingsEditor();
				packEditor.getOkButton().addActionListener(new OkListener());
				packEditor.loadSettings(getSettings(packList.getSelectedValue().toString()));
				packEditor.setModal(true);
				packEditor.setVisible(true);
			}
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(packList.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "Select pack!");
				return;
			}

			allowedPacksDialog = new AllowedDirectoriesDialog();

			List<String> directories = new ArrayList<String>();
			for(File dir : new File(imageDirectory).listFiles()) {
				if (dir.isDirectory() && dir.getName().contains("images-")) {
					directories.add(dir.getName());
				}
			}
			allowedPacksDialog.setDirectories(directories);
			
			allowedPacksDialog.setModal(true);
			allowedPacksDialog.getOkButton().addActionListener(new AllowedDirectoriesOkDialogListener());
			allowedPacksDialog.setVisible(true);
		}
	}
	
	private com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings getSettings(String packName) {
		File packSettingsFile = null;
		for(File dir : new File(imageDirectory).listFiles()) {
			if (dir.getName().contains("images-") && dir.isDirectory() && selectedPacks.contains(dir.getName())) {
				for(File packDir : dir.listFiles()) {
					if (packDir.isDirectory() && packDir.getName().equals(packName)) {
						File tmp = new File(packDir, "pack.json");
						if (tmp.exists()) {
							packSettingsFile = tmp;
							break;
						}
					}
				}
			}
		}
		
		if (packSettingsFile != null && packSettingsFile.exists()) {
			return JsonWorker.fromJson(com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings.class, packSettingsFile);
		} else {
			return new com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings();
		}
	}
	
	private void saveSettingsToAllPacks(String baseDir, String pack, com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings sets) {
		for(File dir : new File(baseDir).listFiles()) {
			if (dir.getName().contains("images-") && selectedPacks.contains(dir.getName())) {
				for(File packDir : dir.listFiles()) {
					if (packDir.isDirectory() && packDir.getName().equals(pack)) {
						try {
							String result = JsonWorker.JSON.toJson(sets);
							FileWriter fWriter = new FileWriter(new File(packDir+"/pack.json"));
							fWriter.write(JsonWorker.JSON.prettyPrint(result));
							fWriter.flush();
							fWriter.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Error during saving pack.json for " + pack +"!");
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public JButton getOkButton() {
		return okButton;
	}
}
