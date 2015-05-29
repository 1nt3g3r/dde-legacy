package ua.com.integer.dde.extension.ui.editor.property.edit.base;
import javax.swing.JLabel;


public class LabeledEditPanel extends BaseEditPanel {
	public static final int LABEL_WIDTH = 100;
	
	private static final long serialVersionUID = 1015006686671792425L;
	
	private JLabel propertyNameLabel;

	public LabeledEditPanel() {
		propertyNameLabel = new JLabel("Property name");
		setSizeForComponent(propertyNameLabel, LABEL_WIDTH, ITEM_HEIGHT);
		add(propertyNameLabel);
	}

	public void setLabel(String text) {
		propertyNameLabel.setText(text);
	}
	
	public String getLabel() {
		return propertyNameLabel.getText();
	}
	
	@Override
	public void setPropertyName(String propertyName) {
		super.setPropertyName(propertyName);
		setLabel(propertyName);
	}
}
