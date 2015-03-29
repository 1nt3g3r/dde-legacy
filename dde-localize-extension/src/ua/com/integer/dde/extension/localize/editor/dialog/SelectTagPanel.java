package ua.com.integer.dde.extension.localize.editor.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ua.com.integer.dde.extension.localize.Localize;

public class SelectTagPanel extends JPanel {
	private static final long serialVersionUID = 1000144274130872822L;
	private JTextField resultTextfield;
	@SuppressWarnings("rawtypes")
	private JComboBox tagCombobox;
	@SuppressWarnings("rawtypes")
	private JComboBox langCombobox;

	private LocalizeSelectListener localizeListener;
	
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("rawtypes")
	public SelectTagPanel() {
		
		JLabel tagLabel = new JLabel("Tag:");
		
		tagCombobox = new JComboBox();
		
		JLabel langLabel = new JLabel("Lang:");
		
		langCombobox = new JComboBox();
		
		JLabel resultLabel = new JLabel("Result:");
		
		resultTextfield = new JTextField();
		resultTextfield.setEnabled(false);
		resultTextfield.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tagLabel)
						.addComponent(langLabel)
						.addComponent(resultLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(resultTextfield, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
						.addComponent(langCombobox, Alignment.LEADING, 0, 196, Short.MAX_VALUE)
						.addComponent(tagCombobox, 0,196, Short.MAX_VALUE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tagLabel)
						.addComponent(tagCombobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(langLabel)
						.addComponent(langCombobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(resultLabel)
						.addComponent(resultTextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(5, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		setup();
	}
	
	public void setLocalizeSelectedListener(LocalizeSelectListener listener) {
		this.localizeListener = listener;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setup() {
		tagCombobox.setModel(new DefaultComboBoxModel(Localize.getInstance().getTags()));
		langCombobox.setModel(new DefaultComboBoxModel(Localize.getInstance().getLanguages()));
		
		tagCombobox.addActionListener(new TagBoxSelectListener());
		langCombobox.addActionListener(new LanguageSelectListener());
	}
	
	class TagBoxSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (localizeListener != null) localizeListener.tagSelected(tagCombobox.getSelectedItem().toString());
			
			updateResult();
		}
	}
	
	class LanguageSelectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Localize.getInstance().setLanguage(langCombobox.getSelectedItem().toString());
			if (localizeListener != null) localizeListener.languageSelected(langCombobox.getSelectedItem().toString());
			
			updateResult();
		}
	}
	
	private void updateResult() {
		resultTextfield.setText(Localize.getInstance().translate(getSelectedTag()));
	}

	public void setTag(String tag) {
		tagCombobox.setSelectedItem(tag);
	}
	
	public void setLanguage(String language) {
		langCombobox.setSelectedItem(language);
	}
	
	public void setLocalizeSelectListener(LocalizeSelectListener selectListener) {
		localizeListener = selectListener;
	}

	public String getSelectedTag() {
		return tagCombobox.getSelectedItem().toString();
	}
}
