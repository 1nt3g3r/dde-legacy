package ua.com.integer.dde.startpanel;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

public class WaitWindow extends JDialog {
	private static final long serialVersionUID = 8588066632262414152L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WaitWindow dialog = new WaitWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WaitWindow() {
		setBackground(Color.LIGHT_GRAY);
		setModal(true);
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				JLabel lblWaitPlease = new JLabel("Wait please...");
				lblWaitPlease.setBackground(Color.LIGHT_GRAY);
				panel.add(lblWaitPlease);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			{
				JProgressBar progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				panel.add(progressBar);
			}
		}

		pack();
		FrameTools.situateOnCenter(this);
	}

}
