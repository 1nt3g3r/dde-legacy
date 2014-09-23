package ua.com.integer.dde.extension.config.editor.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import ua.com.integer.dde.startpanel.FrameTools;

public class IntegerPropertyEditor extends JDialog implements PropertyEditor {
	private static final long serialVersionUID = 1055948243983114071L;
	private final JPanel contentPanel = new JPanel();
	protected JSpinner valueSpinner;
	
	public IntegerPropertyEditor() {
		setTitle("Integer property editor");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			valueSpinner = new JSpinner();
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(valueSpinner, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(valueSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		
		pack();
	}

	@Override
	public void setValue(String value) {
		System.out.println("set " + value);
		try {
			valueSpinner.setValue(Integer.parseInt(value));
		} catch (IllegalArgumentException ex) {
			valueSpinner.setValue(0);
		}
	}

	@Override
	public String getValue() {
		return valueSpinner.getValue().toString();
	}

	@Override
	public void setEditListener(final ActionListener editListener) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				if (editListener != null) {
					editListener.actionPerformed(null);
				}
			}
		});
	}

	@Override
	public void showEditor() {
		FrameTools.situateOnCenter(this);
		setModal(true);
		setVisible(true);
	}
}
