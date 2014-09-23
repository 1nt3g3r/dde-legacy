package ua.com.integer.dde.extension.ui.editor.property.edit.color;

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
import javax.swing.border.LineBorder;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyEditComponent;
import ua.com.integer.dde.extension.ui.editor.utils.ColorUtils;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.util.color.ColorDialog;
import ua.com.integer.dde.startpanel.util.color.ColorListener;

public class ColorEditPanel extends JPanel implements PropertyEditComponent, ColorListener {
	private static final long serialVersionUID = -8356113889471511886L;
	private JLabel propertyName;
	private JPanel colorValue;
	private JButton chooseColor;
	
	private Color defaultColor = Color.WHITE;
	
	private String uiPropertyName;
	private UiConfig config;
	
	private PropertyChangeListener listener;
	private Component horizontalStrut;
	
	public ColorEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		
		propertyName = new JLabel("Property name:");
		propertyName.setPreferredSize(new Dimension(100, 20));
		propertyName.setMaximumSize(new Dimension(100, 20));
		propertyName.setMinimumSize(new Dimension(100, 20));
		
		colorValue = new JPanel();
		colorValue.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorValue.setBackground(Color.WHITE);
		
		chooseColor = new JButton("Choose...");
		chooseColor.setBackground(Color.LIGHT_GRAY);
		chooseColor.addActionListener(new SetColorListener());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(propertyName);
		add(colorValue);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut);
		add(chooseColor);
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
		if (config != null && uiPropertyName != null) {
			String colorString = config.get(uiPropertyName, getDefaultValue());
			Color color = ColorUtils.decodeToAWTColor(colorString);
			
			colorValue.setBackground(color);
		}
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		this.propertyName.setText(propertyName);
	}
	
	@Override
	public void setPropertyChangedListener(PropertyChangeListener listener) {
		this.listener = listener;
	}
	
	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}
	
	@Override
	public String getDefaultValue() {
		return ColorUtils.encodeAWTColor(defaultColor);
	}

	class SetColorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (config == null || uiPropertyName == null) throw new IllegalStateException("Call setConfig() and setUiPropertyName() before!");
			
			Color initialColor = colorValue.getBackground();
			
			ColorDialog.selectColor(initialColor, ColorEditPanel.this);
		}
	}
	
	@Override
	public void colorChanged(Color color) {
		config.set(uiPropertyName, ColorUtils.encodeAWTColor(color));
		
		updateUIFromConfig();
		
		if (listener != null) listener.propertyChanged();
	}
	
	public static void main(String[] args) {
		ColorEditPanel panel = new ColorEditPanel();
		panel.setConfig(new UiConfig());
		panel.setUiPropertyName("font-color");
		panel.setPropertyName("Font color");
		
		FrameTools.testingFrame(panel);
	}
}
