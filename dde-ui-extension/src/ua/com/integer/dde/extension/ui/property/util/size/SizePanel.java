package ua.com.integer.dde.extension.ui.property.util.size;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import ua.com.integer.dde.extension.ui.size.Size;
import ua.com.integer.dde.extension.ui.size.SizeType;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SizePanel extends JPanel {
	private static final long serialVersionUID = 559562481036278137L;
	private JSpinner valueSpinner;
	private JComboBox sizeTypeBox;
	
	private SizeListener sizeListener;

	/**
	 * Create the panel.
	 */
	public SizePanel() {
		setBackground(Color.GRAY);
		
		sizeTypeBox = new JComboBox();
		sizeTypeBox.addActionListener(new SizeChangedListener());
		sizeTypeBox.setModel(new DefaultComboBoxModel(SizeType.values()));
		
		JLabel label = new JLabel("*");
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		
		valueSpinner = new JSpinner();
		valueSpinner.addChangeListener(new SizeChangedListener());
		valueSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.01f)));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(sizeTypeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(valueSpinner, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(sizeTypeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(valueSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	public void setAllowedSizeTypes(SizeType ... types) {
		sizeTypeBox.setModel(new DefaultComboBoxModel(types));
	}
	
	public SizeType[] getAllowedSizeTypes() {
		int count = sizeTypeBox.getModel().getSize();
		SizeType[] toReturn = new SizeType[count];
		
		for(int i = 0; i < count; i++) {
			toReturn[i] = SizeType.valueOf(sizeTypeBox.getModel().getElementAt(i).toString());
		}
		
		return toReturn;
	}
	
	public void setSize(Size size) {
		sizeTypeBox.setSelectedItem(size.getSizeType());
		valueSpinner.setValue(size.getSizeValue());
	}
	
	public void setSizeListener(SizeListener sizeListener) {
		this.sizeListener = sizeListener;
	}
	
	public Size getSelectedSize() {
		Size toReturn = new Size();
		
		toReturn.setType(SizeType.valueOf(sizeTypeBox.getSelectedItem().toString()));
		toReturn.setSizeValue(Float.parseFloat(valueSpinner.getValue().toString()));
		
		return toReturn;
	}
	
	class SizeChangedListener implements ActionListener, ChangeListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			valueChanged();
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			valueChanged();
		}
	}
	
	private void valueChanged() {
		if (sizeListener != null) sizeListener.sizeChanged(getSelectedSize());
	}
	
	public static Size showSizeDialog(Size initialSize, SizeListener sizeListener) {
		SizePanel panel = new SizePanel();
		panel.setSize(initialSize);
		panel.setSizeListener(sizeListener);
		int result = JOptionPane.showConfirmDialog(null, panel, "Select size", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			return panel.getSelectedSize();
		} else {
			return initialSize;
		}
	}
	
	public static Size showSizeDialog(Size initialSize) {
		return showSizeDialog(initialSize, null);
	}
	
	public static Size showSizeDialog(SizeListener sizeListener) {
		return showSizeDialog(new Size(), sizeListener);
	}
	
	public static Size showSizeDialog() {
		return showSizeDialog(new Size(), null);
	}
	
	public static void main(String[] args) {
		showSizeDialog();
	}
}
