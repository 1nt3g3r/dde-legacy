package ua.com.integer.dde.extension.ui.editor.property.edit;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 3257176535303434370L;
	private JLabel lblTitleText;

	/**
	 * Create the panel.
	 */
	public TitlePanel() {
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut);
		
		lblTitleText = new JLabel("Title text");
		lblTitleText.setForeground(Color.GREEN);
		add(lblTitleText);
	}
	
	public void setText(String text) {
		lblTitleText.setText(text);
	}
}
