package ua.com.integer.dde.remote.files.extension.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ua.com.integer.dde.remote.files.extension.core.RemoteFilesConfig;
import ua.com.integer.dde.startpanel.extension.Extension;
import ua.com.integer.dde.startpanel.util.ExtensionFilenameFilter;
import ua.com.integer.dde.util.JsonWorker;

public class RemoteFilesSetupDialog extends JDialog implements Extension {
	private static final long serialVersionUID = -2593784323690192621L;
	private final JPanel contentPanel = new JPanel();
	private JList<String> configList;
	private JTextField remoteURLValue;
	private JTextField localFolderValue;
	private JCheckBox debugCheckbox;
	private JSpinner maxFailCountValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RemoteFilesSetupDialog dialog = new RemoteFilesSetupDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RemoteFilesSetupDialog() {
		setTitle("Remote files configs");
		setBounds(100, 100, 650, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel remoteURLLabel = new JLabel("Remote URL:");
		
		remoteURLValue = new JTextField();
		remoteURLValue.setColumns(10);
		
		JButton testURLButton = new JButton("Test");
		testURLButton.setBackground(Color.LIGHT_GRAY);
		testURLButton.addActionListener(new TestRemoteURLListener());
		testURLButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JLabel lblLocalFolder = new JLabel("Local folder:");
		
		localFolderValue = new JTextField();
		localFolderValue.setColumns(10);
		
		JLabel lblMaxFailCount = new JLabel("Max fail count:");
		
		maxFailCountValue = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
		
		debugCheckbox = new JCheckBox("Debug");
		
		JButton saveButton = new JButton("Save");
		saveButton.setBackground(Color.LIGHT_GRAY);
		saveButton.addActionListener(new SaveCurrentConfigListener());
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLocalFolder)
						.addComponent(lblMaxFailCount)
						.addComponent(remoteURLLabel)
						.addComponent(debugCheckbox))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addComponent(remoteURLValue, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(testURLButton, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addComponent(localFolderValue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(maxFailCountValue, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
						.addComponent(saveButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(remoteURLLabel)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(remoteURLValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(testURLButton, GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLocalFolder)
						.addComponent(localFolderValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaxFailCount)
						.addComponent(maxFailCountValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(debugCheckbox)
						.addComponent(saveButton))
					.addGap(155))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Available configs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.WEST);
			panel.setLayout(new BorderLayout(0, 0));
			{
				configList = new JList<String>();
				JScrollPane configScroll = new JScrollPane(configList);
				panel.add(configScroll);
			}
			{
				JPanel controlPanel = new JPanel();
				panel.add(controlPanel, BorderLayout.SOUTH);
				controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
				{
					JButton addButton = new JButton("Add");
					addButton.addActionListener(new AddConfigListener());
					controlPanel.add(addButton);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(5);
					controlPanel.add(horizontalStrut);
				}
				{
					JButton removeButton = new JButton("Remove");
					removeButton.addActionListener(new RemoveConfigListener());
					controlPanel.add(removeButton);
				}
				{
					Component horizontalStrut = Box.createHorizontalStrut(5);
					controlPanel.add(horizontalStrut);
				}
				{
					JButton renameButton = new JButton("Rename");
					renameButton.addActionListener(new RenameConfigListener());
					controlPanel.add(renameButton);
				}
			}
		}
		
		updateConfigList();
	}
	
	private void updateConfigList() {
		if (configList.getListSelectionListeners().length > 0) {
			configList.removeListSelectionListener(configList.getListSelectionListeners()[0]);
		}
		File configFolder = new File("../android/assets/" + RemoteFilesConfig.DEFAULT_INTERNAL_FOLDER);
		if (!configFolder.exists()) {
			configFolder.mkdirs();
		}
		
		File[] files = configFolder.listFiles(new ExtensionFilenameFilter("config"));
		configList.setModel(new FileListModel(files));
		configList.addListSelectionListener(new ConfigListSelectionListener());
	}
	
	class ConfigListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (getSelectedFile() != null) {
				RemoteFilesConfig config = JsonWorker.fromJson(RemoteFilesConfig.class, getSelectedFile());
				loadValueFromConfig(config);
			}
		}
	}
	
	private void loadValueFromConfig(RemoteFilesConfig config) {
		remoteURLValue.setText(config.remoteURL);
		localFolderValue.setText(config.localFolder);
		debugCheckbox.setSelected(config.logEnabled);
		maxFailCountValue.setValue(config.maxFailCount);
	}
	
	class AddConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String newConfigName = JOptionPane.showInputDialog("Enter name:");
			
			if (newConfigName == null) {
				return;
			}
			
			if (newConfigName.equals("")) {
				JOptionPane.showMessageDialog(null, "Name can't be empty!");
				return;
			}
			
			if (getListModel().containsNameWithoutExtension(newConfigName)) {
				JOptionPane.showMessageDialog(null, "Config with this name exists!");
				return;
			}
			
			String newFilename = "../android/assets/" + RemoteFilesConfig.DEFAULT_INTERNAL_FOLDER + "/" + newConfigName + ".config";
			JsonWorker.createEmptyJson(new File(newFilename));
			updateConfigList();
		}
	}
	
	class RemoveConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			File selectedFile = getSelectedFile();
			if (selectedFile == null) {
				JOptionPane.showMessageDialog(null, "Choose config to remove!");
			} else {
				int answer = JOptionPane.showConfirmDialog(null, "Are you sure?");
				if (answer == JOptionPane.YES_OPTION) {
					selectedFile.delete();
					updateConfigList();
				}
			}
		}
	}
	
	class RenameConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			File selectedFile = getSelectedFile();
			if (selectedFile == null) {
				JOptionPane.showMessageDialog(null, "Choose config to rename!");
			} else {
				String newConfigName = JOptionPane.showInputDialog("Enter name:");
				
				if (newConfigName == null) {
					return;
				}
				
				if (newConfigName.equals("")) {
					JOptionPane.showMessageDialog(null, "Name can't be empty!");
					return;
				}
				
				if (getListModel().containsNameWithoutExtension(newConfigName) && !selectedFile.getName().split("\\.")[0].equals(newConfigName)) {
					JOptionPane.showMessageDialog(null, "Config with this name exists!");
					return;
				}
				
				String newFilename = "../android/assets/" + RemoteFilesConfig.DEFAULT_INTERNAL_FOLDER + "/" + newConfigName + ".config";
				
				boolean result = selectedFile.renameTo(new File(newFilename));
				if (result) {
					updateConfigList();
				} else {
					JOptionPane.showMessageDialog(null, "Can't rename config!");
				}
			}
		}
	}
	
	private FileListModel getListModel() {
		return (FileListModel) configList.getModel();
	}
	
	private File getSelectedFile() {
		if (configList.getSelectedIndex() < 0) {
			return null;
		}
		
		return getListModel().getFileAt(configList.getSelectedIndex());
	}
	
	class SaveCurrentConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelectedFile() != null) {
				RemoteFilesConfig config = new RemoteFilesConfig();
				config.remoteURL = remoteURLValue.getText();
				config.localFolder = localFolderValue.getText();
				config.maxFailCount = Integer.valueOf(maxFailCountValue.getValue() + "");
				config.logEnabled = debugCheckbox.isSelected();
				
				JsonWorker.toJson(config, getSelectedFile());
			}
		}
	}
	
	class TestRemoteURLListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String request = remoteURLValue.getText();
			if (!request.endsWith("/")) {
				request += "/";
			}
			request += "filelist.json";
			
			URL url;
			try {
				url = new URL(request);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
					JOptionPane.showMessageDialog(null, "Success!");
				} else {
					JOptionPane.showMessageDialog(null, "Fail!");
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Fail!");
			}
		}
	}
	
	public JTextField getLocalFolderValue() {
		return localFolderValue;
	}
	public JCheckBox getDebugCheckbox() {
		return debugCheckbox;
	}
	public JSpinner getMaxFailCountValue() {
		return maxFailCountValue;
	}
	public JTextField getRemoteURLValue() {
		return remoteURLValue;
	}

	@Override
	public void launch() {
		main(null);
	}
}
