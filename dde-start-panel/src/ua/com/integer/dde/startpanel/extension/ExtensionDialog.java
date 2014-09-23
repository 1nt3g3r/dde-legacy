package ua.com.integer.dde.startpanel.extension;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ua.com.integer.dde.startpanel.FrameTools;

import com.badlogic.gdx.utils.Array;

public class ExtensionDialog extends JDialog {
	private static final long serialVersionUID = -3301498385545630648L;
	private final JPanel contentPanel = new JPanel();
	private JList<String> extensionList;
	private JTextPane extensionInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ExtensionDialog dialog = new ExtensionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ExtensionDialog() {
		getContentPane().setBackground(Color.GRAY);
		setTitle("DDE Extensions");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			extensionList = new JList<String>();
			extensionList.addListSelectionListener(new ExtensionSelectListener());
			extensionList.setBackground(Color.LIGHT_GRAY);
			JScrollPane extensionScroll = new JScrollPane(extensionList);
			extensionScroll.setViewportBorder(new TitledBorder(null, "Available extensions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(extensionScroll, BorderLayout.WEST);
		}
		{
			extensionInfo = new JTextPane();
			extensionInfo.setContentType("text/html");
			JScrollPane textScroll = new JScrollPane(extensionInfo);
			textScroll.setViewportBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Info about selected extension", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			extensionInfo.setBackground(Color.LIGHT_GRAY);
			extensionInfo.setEditable(false);
			contentPanel.add(textScroll, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton launchExtensionListener = new JButton("Launch extension");
			launchExtensionListener.addActionListener(new LaunchExtensionListener());
			launchExtensionListener.setBackground(Color.LIGHT_GRAY);
			buttonPane.add(launchExtensionListener);
		}
		
		updateExtensions();
		setSize(FrameTools.getQuarterOfScreen());
		FrameTools.situateOnCenter(this);
	}

	private void updateExtensions() {
		Array<ExtensionInfo> extensions = new Array<ExtensionInfo>();
		
		File extensionsDirectory = new File("./extensions");
		if (extensionsDirectory.exists() && extensionsDirectory.isDirectory()) {
			for(File file : new File("./extensions").listFiles()) {
				if (file.getName().endsWith(".dde") && file.isFile()) {
					try {
						ExtensionInfo info = ExtensionInfo.getFromFile(file);
						extensions.add(info);
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Can't load extension " + file.getName());
					}
				}
			}
		} else {
			extensionsDirectory.mkdir();
		}
		
		extensionList.setModel(new ExtensionListModel(extensions));
	}
	
	class ExtensionSelectListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			extensionInfo.setText(getSelectedExtensionInfo().getDescription());
		}
	}

	class LaunchExtensionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (getSelectedExtensionInfo() != null) {
					dispose();
				}
				getSelectedExtensionInfo().launchNewInstance();
			} catch (IllegalStateException ex) {
				JOptionPane.showMessageDialog(null, "Select extension to launch!");
			}
		}
	}
	
	private ExtensionInfo getSelectedExtensionInfo() {
		int index = extensionList.getSelectedIndex();
		if (index < 0) {
			throw new IllegalStateException("Select extension!");
		}
		return ((ExtensionListModel) extensionList.getModel()).getExtensionInfo(index);
	}
	
}
