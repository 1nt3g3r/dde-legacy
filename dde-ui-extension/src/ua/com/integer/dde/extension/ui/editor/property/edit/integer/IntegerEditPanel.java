package ua.com.integer.dde.extension.ui.editor.property.edit.integer;


import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.startpanel.FrameTools;
import java.awt.Color;

public class IntegerEditPanel extends JPanel implements PropertyEditComponent {
	private static final long serialVersionUID = -5563346076050032646L;

	private UiConfig config;
	private String uiPropertyName;

	private PropertyChangeListener listener;

	protected JSpinner valueSpinner;
	private JLabel propertyName;
	
	private String defaultValue = "0";

	/**
	 * Create the panel.
	 */
	public IntegerEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		
		propertyName = new JLabel("Property name:");
		propertyName.setMaximumSize(new Dimension(100, 20));
		propertyName.setPreferredSize(new Dimension(100, 20));
		propertyName.setMinimumSize(new Dimension(100, 20));
		
		valueSpinner = new JSpinner();
		valueSpinner.setForeground(Color.LIGHT_GRAY);
		valueSpinner.setBackground(Color.LIGHT_GRAY);
		valueSpinner.addChangeListener(new IntegerValueChangeListener());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(propertyName);
		add(valueSpinner);

	}
	
	@Override
	public void setConfig(UiConfig config) {
		this.config = config;
		updateUIFromConfig();
	}
	
	@Override
	public void setUiPropertyName(String propertyName) {
		this.uiPropertyName = propertyName;
		updateUIFromConfig();
	}
	
	private void updateUIFromConfig() {
		if (config !=null && uiPropertyName != null) {
			setSpinnerValue(config.get(uiPropertyName, getDefaultValue()));
		}
	}
	
	protected void setSpinnerValue(String value) {
		valueSpinner.setValue((int) Float.parseFloat(value));
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		this.propertyName.setText(propertyName);
	}
	
	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String getDefaultValue() {
		return defaultValue;
	}
	
	class IntegerValueChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (config != null && uiPropertyName != null) {
				if(!config.get(uiPropertyName, getDefaultValue()).equals(valueSpinner.getValue().toString())) {
					config.set(uiPropertyName, valueSpinner.getValue() + "");
					if (listener != null) listener.propertyChanged();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					IntegerEditPanel panel = new IntegerEditPanel();
					panel.setConfig(new UiConfig());
					panel.setUiPropertyName("test-ing");
					panel.setPropertyName("Rotation");
					
					FrameTools.testingFrame(panel);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
