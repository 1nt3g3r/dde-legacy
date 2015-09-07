package ua.com.integer.dde.extension.localize.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import ua.com.integer.dde.extension.localize.Dictionary;
import ua.com.integer.dde.extension.localize.Localize;
import ua.com.integer.dde.startpanel.ddestub.ProjectFinder;
import ua.com.integer.dde.startpanel.util.ExtensionFilenameFilter;
import ua.com.integer.dde.util.JsonWorker;

import com.badlogic.gdx.utils.ObjectMap;

public class LocalizeEditor extends JDialog {
	private static final long serialVersionUID = 4311652295200306518L;
	private final JPanel contentPanel = new JPanel();
	@SuppressWarnings("rawtypes")
	private JList tagList;

	private Localize translation;
	@SuppressWarnings("rawtypes")
	private JList langList;
	
	private ObjectMap<String, JScrollPane> editors;
	private JTabbedPane translationTabs;
	private JCheckBox highlightUntranslatedCheckbox;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LocalizeEditor dialog = new LocalizeEditor();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setModal(true);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public LocalizeEditor() {
		editors = new ObjectMap<String, JScrollPane>();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowExitListener());
		setTitle("DDE Localize Editor");
		setBounds(100, 100, 750, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel tagPanel = new JPanel();
		tagPanel.setBorder(new TitledBorder(null, "Tags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tagPanel.setBackground(Color.GRAY);
		
		JPanel langPanel = new JPanel();
		langPanel.setBorder(new TitledBorder(null, "Languages", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		langPanel.setBackground(Color.GRAY);
		
		JPanel translatePanel = new JPanel();
		translatePanel.setBorder(new TitledBorder(null, "Translation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		translatePanel.setBackground(Color.GRAY);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(langPanel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tagPanel, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(translatePanel, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tagPanel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
				.addComponent(langPanel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
				.addComponent(translatePanel, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
		);
		translatePanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		translationTabs = new JTabbedPane(JTabbedPane.TOP);
		translatePanel.add(translationTabs);
		
		JButton newLanguageButton = new JButton("New");
		newLanguageButton.addActionListener(new CreateLanguageListener());
		newLanguageButton.setBackground(Color.LIGHT_GRAY);
		
		JButton deleteLanguageButton = new JButton("Delete");
		deleteLanguageButton.addActionListener(new DeleteLanguageListener());
		deleteLanguageButton.setBackground(Color.LIGHT_GRAY);
		
		JPanel langScrollPanel = new JPanel();
		GroupLayout gl_langPanel = new GroupLayout(langPanel);
		gl_langPanel.setHorizontalGroup(
			gl_langPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_langPanel.createSequentialGroup()
					.addComponent(newLanguageButton)
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
					.addComponent(deleteLanguageButton))
				.addComponent(langScrollPanel, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
		);
		gl_langPanel.setVerticalGroup(
			gl_langPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_langPanel.createSequentialGroup()
					.addComponent(langScrollPanel, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_langPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(newLanguageButton)
						.addComponent(deleteLanguageButton)))
		);
		langScrollPanel.setLayout(new BoxLayout(langScrollPanel, BoxLayout.X_AXIS));
		
		JScrollPane langScrollPane = new JScrollPane();
		langScrollPanel.add(langScrollPane);
		
		langList = new JList();
		langList.addListSelectionListener(new LanguageSelectionListener());
		langScrollPane.setViewportView(langList);
		langPanel.setLayout(gl_langPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		
		tagList = new JList();
		tagList.addListSelectionListener(new TagSelectedListener());
		scrollPane.setViewportView(tagList);
		
		JButton newTagButton = new JButton("New");
		newTagButton.addActionListener(new CreateTagListener());
		newTagButton.setBackground(Color.LIGHT_GRAY);
		
		JButton deleteTagButton = new JButton("Delete");
		deleteTagButton.addActionListener(new DeleteTagListener());
		deleteTagButton.setBackground(Color.LIGHT_GRAY);
		GroupLayout gl_tagPanel = new GroupLayout(tagPanel);
		gl_tagPanel.setHorizontalGroup(
			gl_tagPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagPanel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_tagPanel.createSequentialGroup()
					.addComponent(newTagButton)
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addComponent(deleteTagButton, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
		);
		gl_tagPanel.setVerticalGroup(
			gl_tagPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tagPanel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tagPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(newTagButton)
						.addComponent(deleteTagButton)))
		);
		tagPanel.setLayout(gl_tagPanel);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setBackground(Color.GRAY);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveTranslation = new JButton("Save translation");
				saveTranslation.addActionListener(new SaveTranslationListener());
				
				JButton btnCreateReport = new JButton("Create report...");
				btnCreateReport.addActionListener(new CreateReportListener());
				buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
				
				Component horizontalStrut = Box.createHorizontalStrut(5);
				buttonPane.add(horizontalStrut);
				
				highlightUntranslatedCheckbox = new JCheckBox("Highlight untranslated");
				highlightUntranslatedCheckbox.setSelected(true);
				highlightUntranslatedCheckbox.addActionListener(new HightlihtClickedListener());
				highlightUntranslatedCheckbox.setBackground(Color.GRAY);
				buttonPane.add(highlightUntranslatedCheckbox);
				
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane.add(horizontalGlue);
				btnCreateReport.setBackground(Color.LIGHT_GRAY);
				buttonPane.add(btnCreateReport);
				
				Component horizontalStrut_1 = Box.createHorizontalStrut(20);
				buttonPane.add(horizontalStrut_1);
				saveTranslation.setBackground(Color.LIGHT_GRAY);
				saveTranslation.setActionCommand("Cancel");
				buttonPane.add(saveTranslation);
			}
			
			Component horizontalStrut = Box.createHorizontalStrut(5);
			buttonPane.add(horizontalStrut);
		}
		
		update();
	}
	
	private File translationFile;
	
	private void update() {
		if (ProjectFinder.findAndroidProject() == null) {
			translationFile = new File("");
		} else {
			translationFile = new File(ProjectFinder.findAndroidProject() + "/assets/data/dde-translation");
		}
		
		if (!translationFile.exists()) {
			translationFile.mkdirs();
		} 
		
		translation = Localize.getInstance();
		translation.setInnerLocalizeFolder(translationFile.getPath());

		initTagList();
		updateLanguageList();
	}
	
	@SuppressWarnings("unchecked")
	private void initTagList() {
		tagList.setModel(new TagListModel(translation));
		updateTagList();
	}
	
	private void updateTagList() {
		tagList.updateUI();
	}
	
	@SuppressWarnings("unchecked")
	private void updateLanguageList() {
		File[] translationFiles = translationFile.listFiles(new ExtensionFilenameFilter("dictionary"));
		for(File file: translationFiles) {
			String[] parts = file.getName().split("\\.");
			String language = parts[0].split("-")[0];
			translation.setLanguage(language);
		}
		langList.setModel(new LangListModel(translation));
	}
	
	class SaveTranslationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				translation.saveAllToDirectory(translationFile);
				JOptionPane.showMessageDialog(null, "Translation was succesfully saved!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error during save translation!");
			}
		}
	}
	
	class CreateTagListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String tagName = JOptionPane.showInputDialog("Input tag name:");
			if (tagName != null && tagName != "") {
				if (translation.containsTag(tagName)) {
					JOptionPane.showMessageDialog(null, "Tag " + tagName + " already exists!");
				} else {
					translation.addTag(tagName);
					updateTagList();
					updateTables();
				}
			}
		}
	}
	
	class DeleteTagListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelectedTag() == null) {
				JOptionPane.showMessageDialog(null, "Select tag to delete!");
			} else {
				int answer = JOptionPane.showConfirmDialog(null, "Do you want to delete selected tag?");
				if (answer == JOptionPane.YES_OPTION) {
					translation.removeTag(getSelectedTag());
					updateTagList();
					updateTables();
				}
			}
		}
		
	}
	
	private String getSelectedTag() {
		if (tagList.getSelectedIndex() < 0) {
			return null;
		}
		return tagList.getSelectedValue().toString();
	}
	
	class CreateLanguageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String language = JOptionPane.showInputDialog("Input new language:");
			if (language != null && !language.equals("")) {
				if (translation.getLanguages().contains(language, false)) {
					JOptionPane.showMessageDialog(null, "Language " + language + " already exists!");
				} else {
					File newTranslation = new File(translationFile, language + "-translation.dictionary");
					Dictionary newDict = new Dictionary();
					newDict.language = language;
					JsonWorker.toJson(newDict, newTranslation);
					translation.addDictionary(Dictionary.fromJson(newTranslation));
					updateLanguageList();
				}
			}
		}
	}
	
	class DeleteLanguageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelectedLanguage() == null) {
				JOptionPane.showMessageDialog(null, "Select language to delete!");
				return;
			}
			
			int answer = JOptionPane.showConfirmDialog(null, "Do you want to delete language <" + getSelectedLanguage() + ">");
			if (answer == JOptionPane.YES_OPTION) {
				String language = getSelectedLanguage();
				new File(translationFile, language + "-translation.dictionary").delete();
				translationTabs.remove(editors.get(language));
				updateLanguageList();
			}
		}
	}
	
	private String getSelectedLanguage() {
		if (langList.getSelectedIndex() < 0) {
			return null;
		} else {
			return langList.getSelectedValue().toString();
		}
	}
	
	class LanguageSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (getSelectedLanguage() != null) {
				openLanguageTab(getSelectedLanguage());
			}
		}
	}
	
	private void openLanguageTab(String language) {
		JScrollPane langScroll = editors.get(language);
		if (langScroll == null) {
			final JTable translationTable = new JTable(new TranslationTableModel(translation.getTranslateMap(language)));
		
			class CustomRenderer extends DefaultTableCellRenderer {
				private static final long serialVersionUID = -1886002552151556961L;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
				{
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if(translationTable.getValueAt(row, 0).equals(translationTable.getValueAt(row, 1)) && highlightUntranslatedCheckbox.isSelected()) {
						c.setBackground(Color.YELLOW);
					} else {
						c.setBackground(Color.WHITE);
					}
					
					if (isSelected) {
						c.setBackground(Color.LIGHT_GRAY);
					}
					return c;
				}
			}
			translationTable.setDefaultRenderer(Object.class, new CustomRenderer());
			
			translationTable.getColumnModel().getColumn(0).setPreferredWidth(200);
			translationTable.getColumnModel().getColumn(0).setMaxWidth(200);
			langScroll = new JScrollPane(translationTable);
			editors.put(language, langScroll);
			translationTabs.addTab(language, langScroll);
		} else {
			translationTabs.setSelectedComponent(editors.get(language));
		}
	}
	
	private void updateTables() {
		for(JScrollPane scroll : editors.values()) {
			JTable table = (JTable) scroll.getViewport().getView();
			table.updateUI();
		}
	}
	
	class TagSelectedListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (getSelectedTag() != null) {
				for(JScrollPane scroll : editors.values()) {
					JTable table = (JTable) scroll.getViewport().getView();
					TranslationTableModel model = (TranslationTableModel) table.getModel();
					
					String selectedTag = getSelectedTag();
					int selectedIndex = model.getIndexOfTag(selectedTag);
					table.setRowSelectionInterval(selectedIndex, selectedIndex);
				}
			}
		}
	}
	
	class WindowExitListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			translation.saveAllToDirectory(translationFile);
			dispose();
		}
	}
	
	class CreateReportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fChooser = new JFileChooser();
			fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedWriter fWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fChooser.getSelectedFile()), "utf-8"));
					for(String tag : translation.getTags()) {
						fWriter.write(tag + "\n");
						for(String lang : translation.getLanguages()) {
							translation.setLanguage(lang);
							fWriter.write("\t<" + lang + ">: " +translation.translate(tag) + "\n");
						}
					}
					fWriter.flush();
					fWriter.close();
					JOptionPane.showMessageDialog(null, "Report created: " + fChooser.getSelectedFile().getAbsolutePath());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Can't create report!" + ex.getMessage());
				}
			}

		}
	}
	
	class HightlihtClickedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for(JScrollPane scroll : editors.values()) {
				scroll.updateUI();
			}
		}
	};
}
