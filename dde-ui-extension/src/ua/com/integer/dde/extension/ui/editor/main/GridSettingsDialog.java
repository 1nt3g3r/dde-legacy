package ua.com.integer.dde.extension.ui.editor.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
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
import ua.com.integer.dde.extension.ui.editor.UiEditorScreen;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.Settings;

public class GridSettingsDialog extends JDialog {
	private static final long serialVersionUID = 8754643432150851241L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox showGridCheckbox;
	private JLabel gridSizeLabel;
	private JSpinner cellSize;

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
		setBounds(100, 100, 350, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		showGridCheckbox = new JCheckBox("Show grid");
		showGridCheckbox.setBackground(Color.GRAY);
		
		gridSizeLabel = new JLabel("Grid cell size(%):");
		
		cellSize = new JSpinner();
		cellSize.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(showGridCheckbox)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gridSizeLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cellSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(showGridCheckbox)
						.addComponent(gridSizeLabel)
						.addComponent(cellSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		loadSettings();
		
		FrameTools.situateOnCenter(this);
	}
	
	private void loadSettings() {
		Settings sets = Settings.getInstance();
		sets.setSettingsClass(UiEditorScreen.class);
		
		boolean gridEnabled = sets.getBoolean("draw-grid", true);
		showGridCheckbox.setSelected(gridEnabled);
		
		gridSizeLabel.setVisible(gridEnabled);
		cellSize.setVisible(gridEnabled);
		
		float percent = Float.parseFloat(sets.getString("grid-percent", "0.1f"));
		int intPercent = (int) (percent * 100f);
		cellSize.setValue(intPercent);
		
		showGridCheckbox.addActionListener(new ShowGridStateChangeListener());
		cellSize.addChangeListener(new GridSizeChangedListener());
	}
	
	class ShowGridStateChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gridSizeLabel.setVisible(showGridCheckbox.isSelected());
			cellSize.setVisible(showGridCheckbox.isSelected());
			
			EditorKernel.editorScreen().setDrawGrid(showGridCheckbox.isSelected());
		}
	}
	
	class GridSizeChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			int percent = Integer.parseInt(cellSize.getValue().toString());
			float floatPercent = (float) percent / 100f;
			EditorKernel.editorScreen().setGridPercent(floatPercent);
		}
		
	}
	
	public JCheckBox getShowGridCheckbox() {
		return showGridCheckbox;
	}
	public JLabel getGridSizeLabel() {
		return gridSizeLabel;
	}
	public JSpinner getCellSize() {
		return cellSize;
	}
}
