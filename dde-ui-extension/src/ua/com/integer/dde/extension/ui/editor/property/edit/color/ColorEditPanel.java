package ua.com.integer.dde.extension.ui.editor.property.edit.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.LabeledBaseEditPanel;
import ua.com.integer.dde.extension.ui.editor.utils.ColorUtils;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.util.color.ColorDialog;
import ua.com.integer.dde.startpanel.util.color.ColorListener;

public class ColorEditPanel extends LabeledBaseEditPanel implements ColorListener {
	private static final long serialVersionUID = -8356113889471511886L;
	private JPanel colorValue;
	private JButton chooseColor;
	
	private Color defaultColor = Color.WHITE;
	
	public ColorEditPanel() {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(300, 20));
		setMinimumSize(new Dimension(300, 20));
		setMaximumSize(new Dimension(300, 20));
		
		colorValue = new JPanel();
		colorValue.setBackground(Color.WHITE);
		
		chooseColor = new JButton("Choose...");
		chooseColor.setBackground(Color.LIGHT_GRAY);
		chooseColor.addActionListener(new SetColorListener());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(colorValue);
		setSizeForComponent(colorValue, 92, ITEM_HEIGHT);
		
		add(Box.createHorizontalStrut(5));
		add(chooseColor);
	}
	
	protected void updateUIFromConfig() {
		if (config != null && uiPropertyName != null) {
			String colorString = config.get(uiPropertyName, getDefaultValue());
			Color color = ColorUtils.decodeToAWTColor(colorString);
			
			colorValue.setBackground(color);
		}
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		setLabel(propertyName);
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
