package ua.com.integer.dde.extension.resource.monitor;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import ua.com.integer.dde.res.load.PathDescriptorLoadManager;

public class ResourceTab extends JPanel {
	private static final long serialVersionUID = 516238331338183580L;

	private JList<String> resList;
	private JLabel totalCount;
	
	/**
	 * Create the panel.
	 */
	public ResourceTab() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane resScroll = new JScrollPane();
		resScroll.setViewportBorder(null);
		add(resScroll, BorderLayout.CENTER);
		
		resList = new JList<String>();
		resList.setBackground(Color.LIGHT_GRAY);
		resScroll.setViewportView(resList);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panel, BorderLayout.SOUTH);
		
		JLabel lblTotalLoadedAsset = new JLabel("Total count:");
		
		totalCount = new JLabel("10");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblTotalLoadedAsset)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(totalCount)
					.addContainerGap(339, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalLoadedAsset)
						.addComponent(totalCount))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}
	
	public void update() {
		if (resList.getModel() != null && resList.getModel() instanceof ResourceListModel) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					((ResourceListModel) resList.getModel()).update();
					totalCount.setText(resList.getModel().getSize() + "");
					resList.updateUI();
				}
			});
		}
	}
	
	public void setPathDescriptorLoadManager(PathDescriptorLoadManager loadManager) {
		resList.setModel(new ResourceListModel(loadManager));
	}
	public JLabel getTotalCount() {
		return totalCount;
	}
}
