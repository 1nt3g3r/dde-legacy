package ua.com.integer.dde.extension.config.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ua.com.integer.dde.extension.config.Config;
import ua.com.integer.dde.extension.config.editor.property.ColorPropertyEditor;
import ua.com.integer.dde.extension.config.editor.property.FloatPropertyEditor;
import ua.com.integer.dde.extension.config.editor.property.IntegerPropertyEditor;
import ua.com.integer.dde.extension.config.editor.property.PropertyEditor;
import java.awt.SystemColor;

public class ConfigEditor extends JDialog {
	private static final long serialVersionUID = 8436930764233796281L;
	private final JPanel contentPanel = new JPanel();
	private JTextField descriptionText;
	private JTextField valueText;
	private JList<String> configList;
	private JList<String> propertyList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConfigEditor dialog = new ConfigEditor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConfigEditor() {
		getContentPane().setBackground(SystemColor.window);
		setBackground(SystemColor.window);
		setTitle("DDE Property editor");
		setBounds(100, 100, 850, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.window);
		contentPanel.setBorder(new EmptyBorder(5, 5, 15, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			JPanel configsPanel = new JPanel();
			configsPanel.setBackground(Color.GRAY);
			configsPanel.setBorder(new TitledBorder(null, "Available configs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(configsPanel);
			
			JButton btnNewConfig = new JButton("New config...");
			btnNewConfig.addActionListener(new CreateConfigListener());
			btnNewConfig.setBackground(Color.LIGHT_GRAY);
			
			JButton btnDeleteConfig = new JButton("Delete");
			btnDeleteConfig.addActionListener(new DeleteConfigListener());
			btnDeleteConfig.setBackground(Color.LIGHT_GRAY);
			
			JPanel configScrollPanel = new JPanel();
			
			JButton renameConfigListener = new JButton("Rename");
			renameConfigListener.addActionListener(new RenameConfigListener());
			renameConfigListener.setBackground(Color.LIGHT_GRAY);
			GroupLayout gl_configsPanel = new GroupLayout(configsPanel);
			gl_configsPanel.setHorizontalGroup(
				gl_configsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_configsPanel.createSequentialGroup()
						.addComponent(btnNewConfig)
						.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
						.addComponent(renameConfigListener)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnDeleteConfig, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
					.addComponent(configScrollPanel, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
			);
			gl_configsPanel.setVerticalGroup(
				gl_configsPanel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_configsPanel.createSequentialGroup()
						.addComponent(configScrollPanel, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_configsPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewConfig)
							.addComponent(btnDeleteConfig)
							.addComponent(renameConfigListener)))
			);
			configScrollPanel.setLayout(new BoxLayout(configScrollPanel, BoxLayout.X_AXIS));
			
			JScrollPane scrollPane = new JScrollPane();
			configScrollPanel.add(scrollPane);
			
			configList = new JList<String>();
			configList.addListSelectionListener(new ConfigSelectedListener());
			configList.setBackground(Color.WHITE);
			scrollPane.setViewportView(configList);
			configsPanel.setLayout(gl_configsPanel);
		}
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPanel.add(horizontalStrut);
		{
			JPanel propertiesPanel = new JPanel();
			propertiesPanel.setBackground(Color.GRAY);
			propertiesPanel.setBorder(new TitledBorder(null, "Properties of selected config", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(propertiesPanel);
			
			JButton btnNewProperty = new JButton("New property...");
			btnNewProperty.addActionListener(new CreatePropertyListener());
			btnNewProperty.setBackground(Color.LIGHT_GRAY);
			
			JButton btnDeleteProperty = new JButton("Delete");
			btnDeleteProperty.addActionListener(new DeletePropertyListener());
			btnDeleteProperty.setBackground(Color.LIGHT_GRAY);
			
			JPanel scrollPropertyPanel = new JPanel();
			
			JButton renamePropertyButton = new JButton("Rename");
			renamePropertyButton.addActionListener(new RenamePropertyListener());
			renamePropertyButton.setBackground(Color.LIGHT_GRAY);
			GroupLayout gl_propertiesPanel = new GroupLayout(propertiesPanel);
			gl_propertiesPanel.setHorizontalGroup(
				gl_propertiesPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_propertiesPanel.createSequentialGroup()
						.addComponent(btnNewProperty)
						.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
						.addComponent(renamePropertyButton)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnDeleteProperty, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
					.addComponent(scrollPropertyPanel, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
			);
			gl_propertiesPanel.setVerticalGroup(
				gl_propertiesPanel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_propertiesPanel.createSequentialGroup()
						.addComponent(scrollPropertyPanel, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_propertiesPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewProperty)
							.addComponent(btnDeleteProperty)
							.addComponent(renamePropertyButton)))
			);
			scrollPropertyPanel.setLayout(new BoxLayout(scrollPropertyPanel, BoxLayout.X_AXIS));
			
			JScrollPane propertyScroll = new JScrollPane();
			scrollPropertyPanel.add(propertyScroll);
			
			propertyList = new JList<String>();
			propertyList.addListSelectionListener(new PropertySelectedListener());
			propertyScroll.setViewportView(propertyList);
			propertiesPanel.setLayout(gl_propertiesPanel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Selected property", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JLabel lblDescription = new JLabel("Description:");
			
			descriptionText = new JTextField();
			descriptionText.addKeyListener(new EditListener());
			descriptionText.setColumns(10);
			
			JLabel lblValue = new JLabel("Value:");
			
			valueText = new JTextField();
			valueText.addKeyListener(new EditListener());
			valueText.setColumns(10);
			
			JButton btnSaveProperty = new JButton("Save property");
			btnSaveProperty.addActionListener(new SavePropertyListener());
			
			btnSaveProperty.setBackground(Color.LIGHT_GRAY);
			
			JButton btnEditAs = new JButton("Edit as...");
			btnEditAs.addActionListener(new EditAsListener());
			btnEditAs.setBackground(Color.LIGHT_GRAY);
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_buttonPane.createSequentialGroup()
								.addGroup(gl_buttonPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblDescription)
									.addComponent(lblValue))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_buttonPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_buttonPane.createSequentialGroup()
										.addComponent(valueText, GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnEditAs, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
									.addComponent(descriptionText, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)))
							.addComponent(btnSaveProperty, Alignment.TRAILING))
						.addContainerGap())
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDescription)
							.addComponent(descriptionText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblValue)
							.addComponent(valueText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnEditAs))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnSaveProperty))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		
		pack();
		updateConfigs();
	}
	
	private void updateConfigs() {
		configList.setModel(new ConfigListModel(Configs.getInstance().getConfigs()));
		configList.updateUI();
	}
	
	class CreateConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String configName = JOptionPane.showInputDialog("Input new config name. Use English letters and digits only:");
			if (configName != null) {
				Configs.getInstance().saveConfig(null, configName);
				updateConfigs();
			}
		}
	}
	
	class DeleteConfigListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (configList.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "Select config to remove!");
			} else {
				if (JOptionPane.showConfirmDialog(null, "Do you want to delete this config?") == JOptionPane.YES_OPTION) {
					Configs.getInstance().deleteConfig(getSelectedConfigName());
					updateConfigs();
				}
			}
		}
	}
	
	private Config getSelectedConfig() {
		int configIndex = configList.getSelectedIndex();
		if (configIndex < 0) {
			throw new IllegalStateException("Config isn't selected!");
		} else {
			return getConfigModel().getConfigAt(configIndex);
		}
	}
	
	private String getSelectedConfigName() {
		int configIndex = configList.getSelectedIndex();
		if (configIndex < 0) {
			throw new IllegalStateException("Config isn't selected!");
		} else {
			return getConfigModel().getConfigNameAt(configIndex);
		}
	}
	
	private ConfigListModel getConfigModel() {
		return (ConfigListModel) configList.getModel();
	}

	class ConfigSelectedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			updatePropertyList();
		}
	}
	
	private void updatePropertyList() {
		if (configList.getSelectedIndex() >= 0) {
			propertyList.setModel(new PropertyListModel(getSelectedConfig()));
			propertyList.updateUI();
		} else {
			propertyList.setModel(new PropertyListModel(new Config()));
		}
	}
	
	class CreatePropertyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (configList.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "Select config!");
				return;
			}
			
			String propertyName = JOptionPane.showInputDialog("Input property name. Use English letters and digits only:");
			if (propertyName != null) {
				Config config = getSelectedConfig();
				config.setString(propertyName, "0");
				config.setDescription(propertyName, "<No description>");
				
				Configs.getInstance().saveConfig(config, getSelectedConfigName());
				
				updatePropertyList();
			}
		}
	}
	
	class DeletePropertyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (configList.getSelectedIndex() >= 0) {
				if (propertyList.getSelectedIndex() >= 0) {
					if (JOptionPane.showConfirmDialog(null, "Do you want to delete selected property?") == JOptionPane.YES_OPTION) {
						getSelectedConfig().deleteProperty(getSelectedPropertyName());
						Configs.getInstance().saveConfig(getSelectedConfig(), getSelectedConfigName());
						updatePropertyList();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Select property!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Select config!");
			}
		}
	}

	private String getSelectedPropertyName() {
		return propertyList.getSelectedValue().toString();
	}
	
	private String getSelectedPropertyValue() {
		return getSelectedConfig().getString(getSelectedPropertyName(), "0");
	}
	
	private String getSelectedPropertyDescription() {
		return getSelectedConfig().getDescription(getSelectedPropertyName());
	}
	
	class PropertySelectedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (propertyList.getSelectedIndex() >= 0) {
				valueText.setText(getSelectedPropertyValue());
				descriptionText.setText(getSelectedPropertyDescription());
				
				valueText.setBackground(Color.WHITE);
				descriptionText.setBackground(Color.WHITE);
			} else {
				valueText.setText("");
				descriptionText.setText("");
			}
		}
	}
	
	class EditListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			if (configList.getSelectedIndex() >= 0 && propertyList.getSelectedIndex() >= 0) {
				e.getComponent().setBackground(Color.YELLOW);
			}
		}
	}
	
	class SavePropertyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (configList.getSelectedIndex() >= 0 && propertyList.getSelectedIndex() >= 0) {
				getSelectedConfig().setString(getSelectedPropertyName(), valueText.getText());
				getSelectedConfig().setDescription(getSelectedPropertyName(), descriptionText.getText());
				
				Configs.getInstance().saveConfig(getSelectedConfig(), getSelectedConfigName());
				
				valueText.setBackground(Color.WHITE);
				descriptionText.setBackground(Color.WHITE);
			}
		}
	}
	
	class RenamePropertyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (propertyList.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "Select property to rename!");
				return;
			}
			
			String newPropertyName = JOptionPane.showInputDialog("Input new property name:", getSelectedPropertyName());
			if (newPropertyName != null && !newPropertyName.equals("")) {
				String propertyName = getSelectedPropertyName();
				String propertyValue = getSelectedPropertyValue();
				String propertyDescription = getSelectedPropertyDescription();
				
				getSelectedConfig().deleteProperty(propertyName);
				getSelectedConfig().setString(newPropertyName, propertyValue);
				getSelectedConfig().setDescription(newPropertyName, propertyDescription);
				
				Configs.getInstance().saveConfig(getSelectedConfig(), getSelectedConfigName());
				
				valueText.setBackground(Color.WHITE);
				descriptionText.setBackground(Color.WHITE);
				
				updatePropertyList();
				propertyList.setSelectedValue(newPropertyName, true);
			}
		}
	}
	
	class RenameConfigListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (configList.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "Select config to rename!");
				return;
			}

			String newConfigName = JOptionPane.showInputDialog("Input new config name:", getSelectedConfigName());
			if (newConfigName != null && !newConfigName.equals("")) {
				Config config = getSelectedConfig();
				
				Configs.getInstance().deleteConfig(getSelectedConfigName());
				Configs.getInstance().saveConfig(config, newConfigName);
				updateConfigs();
			}
		}
	}
	
	class EditAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (propertyList.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "Select property to edit!");
				return;
			}
			getPopupMenu().show(ConfigEditor.this, getMousePosition().x, getMousePosition().y);
		}
	}
	
	private JPopupMenu getPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(getMenuItem(new IntegerPropertyEditor(), "Integer"));
		menu.add(getMenuItem(new FloatPropertyEditor(), "Float"));
		menu.add(getMenuItem(new ColorPropertyEditor(), "RGBA Color"));
		return menu;
	}
	
	private JMenuItem getMenuItem(final PropertyEditor editor, String itemName) {
		JMenuItem result = new JMenuItem(itemName);
		result.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setValue(getSelectedPropertyValue());
				editor.setEditListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						valueText.setText(editor.getValue());
						getSelectedConfig().setString(getSelectedPropertyName(), valueText.getText());
						Configs.getInstance().saveConfig(getSelectedConfig(), getSelectedConfigName());
					}
				});
				editor.showEditor();
			}
		});
		return result;
	}
	
	public JTextField getDescriptionText() {
		return descriptionText;
	}
	public JTextField getValueText() {
		return valueText;
	}
}
