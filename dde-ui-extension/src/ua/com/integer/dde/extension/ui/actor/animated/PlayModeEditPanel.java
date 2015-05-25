package ua.com.integer.dde.extension.ui.actor.animated;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledBaseEditPanel;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class PlayModeEditPanel extends LabeledBaseEditPanel {
	private static final long serialVersionUID = 8434740669668473954L;

	private JComboBox<PlayMode> playMode;

	public PlayModeEditPanel() {
		setLabel("Play mode");
		
		setDefaultValue(PlayMode.LOOP + "");
		
		playMode = new JComboBox<PlayMode>();
		playMode.addActionListener(new PlayModeChangeListener());
		playMode.setModel(new DefaultComboBoxModel<PlayMode>(PlayMode.values()));
		playMode.setSelectedIndex(2);
		add(playMode);
	}
	
	@Override
	protected void updateUIFromConfig() {
		if (config != null) {
			String playModeStr = config.get(uiPropertyName, getDefaultValue());
			PlayMode mode = PlayMode.valueOf(playModeStr);
			playMode.setSelectedItem(mode);
		}
	}
	
	class PlayModeChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null) {
				config.set(uiPropertyName, playMode.getSelectedItem() + "");
				
				if (listener != null) {
					listener.propertyChanged();
				}
			}
		}
	}
	
	
}
