package ua.com.integer.dde.extension.ui.actor.animation.spine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledEditPanel;

public class AnimationNamesEditPanel extends LabeledEditPanel {
	private static final long serialVersionUID = -1237539892388085963L;
	
	private JComboBox<String> animationNames;
	private AnimationNameChangeListener nameChangeListener = new AnimationNameChangeListener();
	
	public AnimationNamesEditPanel() {
		animationNames = new JComboBox<String>();
		animationNames.addActionListener(nameChangeListener);
		add(animationNames);
	}

	@Override
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			animationNames.removeActionListener(nameChangeListener);
			
			String animationConfigName = config.get("spine-animation-config");
			if (!animationConfigName.equals("")) {
				animationNames.setModel(new DefaultComboBoxModel<String>(SpineAnimations.getInstance().getAnimationList(animationConfigName)));
			}
			
			if (!config.get(uiPropertyName).equals("")) {
				animationNames.setSelectedItem(config.get(uiPropertyName));
			} else if (animationNames.getItemCount() > 0) {
				config.set(uiPropertyName, animationNames.getItemAt(0));
				if(listener != null) {
					listener.propertyChanged();
				}
			}
			
			animationNames.addActionListener(nameChangeListener);
		}
	}
	
	class AnimationNameChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(config != null && uiPropertyName != null && animationNames.getSelectedItem() != null) {
				config.set(uiPropertyName, animationNames.getSelectedItem().toString());
			}
			
			if (listener != null) {
				listener.propertyChanged();
			}
		}
	}
	
}
