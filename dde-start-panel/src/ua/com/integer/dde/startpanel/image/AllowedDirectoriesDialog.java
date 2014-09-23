package ua.com.integer.dde.startpanel.image;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import ua.com.integer.dde.startpanel.FrameTools;

public class AllowedDirectoriesDialog extends JDialog {
	private static final long serialVersionUID = 4719185138222429503L;
	private final JPanel contentPanel = new JPanel();
	private List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	private JButton okButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AllowedDirectoriesDialog dialog = new AllowedDirectoriesDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AllowedDirectoriesDialog() {
		setTitle("Choose image directories");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setSize(FrameTools.getQuarterOfScreen());
		FrameTools.situateOnCenter(this);
	}
	
	public void setDirectories(List<String> directories) {
		contentPanel.removeAll();
		checkboxes.clear();
		for(String directory : directories) {
			JCheckBox checkbox = new JCheckBox(directory);
			checkbox.setSelected(true);
			checkbox.setBackground(Color.GRAY);
			checkboxes.add(checkbox);
			contentPanel.add(checkbox);
		}
	}
	
	public List<String> getSelelectedDirectories() {
		List<String> toReturn = new ArrayList<String>();
		for(JCheckBox box : checkboxes) {
			if (box.isSelected()) {
				toReturn.add(box.getText());
			}
		}
		return toReturn;
	}
	
	public JButton getOkButton() {
		return okButton;
	}
}
