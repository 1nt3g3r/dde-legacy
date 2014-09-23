package ua.com.integer.dde.extension.ui.editor.property.edit.drawable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.startpanel.FrameTools;

public class DrawableEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = -1367789460763803249L;

	private PropertyChangeListener listener;
	private UiConfig config;
	private String uiPropertyName;
	
	private JLabel propertyName;

	/**
	 * Create the panel.
	 */
	public DrawableEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(100, 20));
		propertyName.setMinimumSize(new Dimension(100, 20));
		propertyName.setMaximumSize(new Dimension(100, 20));
		add(propertyName);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearDrawableListener());
		btnClear.setBackground(Color.LIGHT_GRAY);
		add(btnClear);
		
		JButton btnSetupDrawable = new JButton("Choose...");
		btnSetupDrawable.addActionListener(new DrawableListener());
		
		Component horizontalGlue = Box.createHorizontalGlue();
		add(horizontalGlue);
		btnSetupDrawable.setBackground(Color.LIGHT_GRAY);
		add(btnSetupDrawable);

	}

	class ClearDrawableListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null) {
				config.properties.remove(uiPropertyName);
				
				if (listener != null) listener.propertyChanged();
			}
		}
	}
	
	class DrawableListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config != null && uiPropertyName != null && listener != null) {
				DrawableEditor editor = new DrawableEditor();
				editor.setConfig(config);
				editor.setUiPropertyName(uiPropertyName);
				editor.setPropertyChangedListener(listener);
				editor.setModal(true);
				FrameTools.situateOnCenter(editor);
				editor.setVisible(true);
			}
		}
	}
	
	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
	}

	@Override
	public void setUiPropertyName(String propertyName) {
		this.uiPropertyName = propertyName;
	}

	@Override
	public void setPropertyName(String propertyName) {
		this.propertyName.setText(propertyName);
	}

	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public String getDefaultValue() {
		return null;
	}
}
