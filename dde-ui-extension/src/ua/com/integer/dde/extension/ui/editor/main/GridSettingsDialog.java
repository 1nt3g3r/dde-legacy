package ua.com.integer.dde.extension.ui.editor.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.drag.GridSettings;
import ua.com.integer.dde.startpanel.FrameTools;

public class GridSettingsDialog extends JDialog {
	private static final long serialVersionUID = 8754643432150851241L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox showGridCheckbox;
	private JLabel cellWidthLabel;
	private JSpinner cellWidthSpinner;
	private JSpinner cellHeightSpinner;
	private JCheckBox snapToGridCheckbox;
	private JCheckBox sizeTheSameCheckbox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GridSettingsDialog dialog = new GridSettingsDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GridSettingsDialog() {
		getContentPane().setBackground(Color.GRAY);
		setTitle("Grid settings");
		setBounds(100, 100, 260, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		showGridCheckbox = new JCheckBox("Show grid");
		showGridCheckbox.setBackground(Color.GRAY);
		
		cellWidthLabel = new JLabel("Cell width (%):");
		
		cellWidthSpinner = new JSpinner();
		cellWidthSpinner.setModel(new SpinnerNumberModel(new Float(10), new Float(1), new Float(100), new Float(5)));
		
		snapToGridCheckbox = new JCheckBox("Snap to grid");
		snapToGridCheckbox.setForeground(Color.BLUE);
		snapToGridCheckbox.setBackground(Color.GRAY);
		
		JLabel cellHeightLabel = new JLabel("Cell height (%):");
		
		cellHeightSpinner = new JSpinner();
		cellHeightSpinner.setModel(new SpinnerNumberModel(10, 1, 100, 5));
		
		sizeTheSameCheckbox = new JCheckBox("Make both cells the same");
		sizeTheSameCheckbox.setBackground(Color.GRAY);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(sizeTheSameCheckbox, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(showGridCheckbox)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(snapToGridCheckbox))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(cellWidthLabel)
								.addComponent(cellHeightLabel))
							.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cellHeightSpinner)
								.addComponent(cellWidthSpinner))))
					.addContainerGap(110, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(showGridCheckbox)
						.addComponent(snapToGridCheckbox))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cellWidthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cellWidthLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cellHeightLabel)
						.addComponent(cellHeightSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(sizeTheSameCheckbox)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
		
		loadSettings();
		
		FrameTools.situateOnCenter(this);
	}
	
	private void loadSettings() {
		showGridCheckbox.setSelected(sets().needShowGrid());
		snapToGridCheckbox.setSelected(sets().needSnapToGrid());
		sizeTheSameCheckbox.setSelected(sets().isNeedMakeBothDirectionsTheSame());
		
		cellHeightSpinner.setEnabled(!sizeTheSameCheckbox.isSelected());
		
		cellWidthSpinner.setValue(getFloatPercentAs100(sets().getGridPercentX()));
		cellHeightSpinner.setValue(getFloatPercentAs100(sets().getGridPercentY()));
		
		showGridCheckbox.addActionListener(new ShowGridStateChangeListener());
		sizeTheSameCheckbox.addActionListener(new MakeBothDirectionCellSizeTheSameListener());
		snapToGridCheckbox.addActionListener(new SnapToGridStateChangeListener());
		
		cellWidthSpinner.addChangeListener(new CellWidthChangedListener());
		cellHeightSpinner.addChangeListener(new CellHeightChangedListener());
	}
	
	class MakeBothDirectionCellSizeTheSameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sets().setNeedMakeBothDirectionsTheSame(sizeTheSameCheckbox.isSelected());
			cellHeightSpinner.setEnabled(!sizeTheSameCheckbox.isSelected());
		}
	}
	
	class ShowGridStateChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (showGridCheckbox.isSelected()) {
				EditorKernel.sendCommand("grid show");
			} else {
				EditorKernel.sendCommand("grid hide");
			}
		}
	}
	
	class SnapToGridStateChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (snapToGridCheckbox.isSelected()) {
				EditorKernel.sendCommand("grid on");
			} else {
				EditorKernel.sendCommand("grid off");
			}
		}
	}
	
	class CellWidthChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			try {
				int cellWidth = (int) Float.parseFloat(cellWidthSpinner.getValue().toString());
				sets().setGridPercentX(get100PercentAsFloat(cellWidth));
				if (sizeTheSameCheckbox.isSelected()) {
					sets().setGridPercentY(get100PercentAsFloat(cellWidth));
					cellHeightSpinner.setValue(cellWidth);
				}
				
				EditorKernel.editorScreen().setGridSize(sets().getGridPercentX(), sets().getGridPercentY());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	class CellHeightChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			try {
				int cellHeight = (int) Float.parseFloat(cellHeightSpinner.getValue().toString());
				sets().setGridPercentY(get100PercentAsFloat(cellHeight));
				EditorKernel.editorScreen().setGridSize(sets().getGridPercentX(), sets().getGridPercentY());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
	}
	
	private float get100PercentAsFloat(int percent) {
		return (float) percent / 100f;
	}
	
	private float getFloatPercentAs100(float percent) {
		return (int) (100f * percent);
	}
	
	private GridSettings sets() {
		return GridSettings.getInstance();
	}
}
