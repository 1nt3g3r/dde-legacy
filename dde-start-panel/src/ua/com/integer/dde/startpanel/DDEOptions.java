package ua.com.integer.dde.startpanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EtchedBorder;

/**
 * Класс для хранения настроек панели управления DDE
 * 
 * @author 1nt3g3r
 */
public class DDEOptions extends JDialog {
	private static final long serialVersionUID = -8892532957139603839L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox imageModifyCheckbox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DDEOptions dialog = new DDEOptions();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DDEOptions() {
		setTitle("DDE Options");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		imageModifyCheckbox = new JCheckBox("Check if source images were modified");
		imageModifyCheckbox.setBackground(Color.LIGHT_GRAY);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(imageModifyCheckbox)
					.addContainerGap(303, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(imageModifyCheckbox)
					.addContainerGap(224, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveSettingsButton = new JButton("Save settings");
				saveSettingsButton.addActionListener(new SaveSettingsListener());
				saveSettingsButton.setBackground(Color.LIGHT_GRAY);
				saveSettingsButton.setActionCommand("OK");
				buttonPane.add(saveSettingsButton);
				getRootPane().setDefaultButton(saveSettingsButton);
			}
		}
		
		loadSettings();
		
		FrameTools.situateOnCenter(this);
	}
	
	private void loadSettings() {
		Settings sets = Settings.getInstance();
		
		imageModifyCheckbox.setSelected(sets.getBoolean("check-source-images-modified", true));
	}
	
	class SaveSettingsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveSettings();
		}
	}
	
	private void saveSettings() {
		Settings sets = Settings.getInstance();
		
		sets.putBoolean("check-source-images-modified", imageModifyCheckbox.isSelected());
		JOptionPane.showMessageDialog(null, "Settings was succesfully saved!");
		dispose();
	}
	
}
