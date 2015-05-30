package ua.com.integer.dde.extension.ui.actor.animation.spine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;

public class SpineConfigListEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -5418314965976598071L;
	protected JComboBox<String> animationList;
	
	private AnimationChangedListener chooseListener = new AnimationChangedListener();

	public SpineConfigListEditPanel() {
		animationList = new JComboBox<String>(SpineAnimations.getInstance().getAnimationConfigs());
		animationList.addActionListener(chooseListener);
		add(animationList);
	}
	
	@Override
	protected void updateUIFromConfig() {
		animationList.removeActionListener(chooseListener);

		if (config != null && uiPropertyName != null) {
			String configName = config.get(uiPropertyName);
			if (configName.equals("") && animationList.getItemCount() > 0) { //load first available config
				config.set(uiPropertyName, animationList.getItemAt(0));
				if (listener != null) {
					listener.propertyChanged();
				}
			} else {
				animationList.setSelectedItem(config.get(uiPropertyName));
			}
		}
		
		animationList.addActionListener(chooseListener);
	}
	
	class AnimationChangedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (config != null && uiPropertyName != null) {
				config.set(uiPropertyName, animationList.getSelectedItem().toString());
			}
			
			if (listener != null) {
				listener.propertyChanged();
			}
		}
	}
	
	public String getSelectedAnimation() {
		return animationList.getSelectedItem().toString();
	}
}
