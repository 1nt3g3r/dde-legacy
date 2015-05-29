package ua.com.integer.dde.extension.ui.editor.property.imp;

import static ua.com.integer.dde.extension.ui.editor.property.edit.base.BaseEditPanel.ITEM_HEIGHT;
import static ua.com.integer.dde.extension.ui.editor.property.edit.base.BaseEditPanel.ITEM_WIDTH;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.com.integer.dde.extension.ui.UiConfig;
import ua.com.integer.dde.extension.ui.editor.EditorKernel;
import ua.com.integer.dde.extension.ui.editor.property.ConfigEditor;
import ua.com.integer.dde.extension.ui.editor.property.edit.PropertyChangeListener;
import ua.com.integer.dde.extension.ui.editor.property.edit.base.PropertyEditComponent;
import ua.com.integer.dde.extension.ui.editor.property.edit.builder.Builders;

public abstract class ExpandableConfigEditor extends ConfigEditor implements PropertyChangeListener {
	private static final long serialVersionUID = -6256422816758571349L;
	
	private static Icon PLUS_ICON, MINUS_ICON;
	
	private PropertyChangeListener propertyChangeListener;
	
	private JPanel componentsPanel;
	
	private int itemCount = 5;
	
	private JLabel lblTitle;
	private JButton expandButton;

	protected UiConfig config;
	
	protected boolean updateUIConfigWhenPropertyChanged = true;
	
	protected Builders builders = new Builders(this);
	
	static {
		try {
			PLUS_ICON = new ImageIcon(ImageIO.read(ExpandableConfigEditor.class.getResource("/ua/com/integer/dde/extension/ui/res/icon/plus-16-16.png")));
			MINUS_ICON = new ImageIcon(ImageIO.read(ExpandableConfigEditor.class.getResource("/ua/com/integer/dde/extension/ui/res/icon/minus-16-16.png")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public ExpandableConfigEditor() {
		setBorder(null);
		hardFixSize(ITEM_WIDTH, ITEM_HEIGHT);

		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel expandPanel = new JPanel();
		expandPanel.setBackground(Color.GRAY);
		setSizeForComponent(ITEM_WIDTH, ITEM_HEIGHT, expandPanel);
		add(expandPanel);
		expandPanel.setLayout(new BoxLayout(expandPanel, BoxLayout.X_AXIS));
		
		lblTitle = new JLabel("Label properties");
		lblTitle.setForeground(Color.GREEN);
		expandPanel.add(lblTitle);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		expandPanel.add(horizontalGlue);
		
		expandButton = new JButton();
		expandButton.addActionListener(new ExpandListener());
		expandButton.setBackground(Color.GRAY);
		expandButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		expandPanel.add(expandButton);
		setSizeForComponent(ITEM_HEIGHT, ITEM_HEIGHT, expandButton);
		
		componentsPanel = new JPanel();
		componentsPanel.setBackground(Color.GRAY);
		add(componentsPanel);
		componentsPanel.setLayout(new BoxLayout(componentsPanel, BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		collapse();
	}
	
	private void hardFixSize(int hardWidth, int hardHeight) {
		Dimension hardSize = new Dimension(hardWidth, hardHeight);
		setSize(hardSize);
		setMaximumSize(hardSize);
		setMinimumSize(hardSize);
		setPreferredSize(hardSize);
	}
	
	private void setSizeForComponent(int width, int height, Component cmp) {
		Dimension size = new Dimension(width, height);
		cmp.setSize(size);
		cmp.setMinimumSize(size);
		cmp.setMaximumSize(size);
		cmp.setPreferredSize(size);
	}
	
	class ExpandListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getHeight() <= ITEM_HEIGHT) {
				expand();
			} else {
				collapse();
			}
			
			validate();
			updateUI();
			getParent().validate();
			getParent().repaint();
		}
	}
	
	public void setContent(JComponent ... components) {
		componentsPanel.removeAll();
		itemCount = components.length;
		
		for(JComponent component: components) {
			componentsPanel.add(component);
		}
		
		collapse();
	}
	
	public void addContent(JComponent ... components) {
		itemCount += components.length;
		for(JComponent component: components) {
			componentsPanel.add(component);
		}
		
		collapse();
	}
	
	protected void collapse() {
		hardFixSize(ITEM_WIDTH, ITEM_HEIGHT);
		expandButton.setIcon(PLUS_ICON);
	}
	
	protected void expand() {
		hardFixSize(ITEM_WIDTH, ITEM_HEIGHT * (itemCount + 1));
		expandButton.setIcon(MINUS_ICON);
	}
	
	public void setTitle(String title) {
		lblTitle.setText(title);
	}
	
	public String getTitle() {
		return lblTitle.getText();
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public void setConfig(UiConfig config) {
		this.config = config;
		
		for(Component c : componentsPanel.getComponents()) {
			if (c instanceof PropertyEditComponent) {
				PropertyEditComponent editor = (PropertyEditComponent) c;
				editor.setConfig(config);
			}
		}
	};
	
	public void setPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeListener = propertyChangeListener;
	}
	
	public void notifyPropertyChangeListener() {
		if (propertyChangeListener != null) {
			propertyChangeListener.propertyChanged();
		}
	}
	
	@Override
	public void propertyChanged() {
		notifyPropertyChangeListener();
		if (updateUIConfigWhenPropertyChanged) {
			EditorKernel.editorScreen().updateConfig();
		}
	}
	
	public static JPanel header(String text) {
		JPanel panel = new JPanel();
		panel.setSize(ITEM_WIDTH, ITEM_HEIGHT);
		panel.setBackground(Color.GRAY);
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		JLabel label = new JLabel(text);
		label.setForeground(Color.YELLOW);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(label);
		panel.add(Box.createHorizontalGlue());
		return panel;
	}
	
	public static JPanel subHeader(String text) {
		JPanel panel = new JPanel();
		panel.setSize(ITEM_WIDTH, ITEM_HEIGHT);
		panel.setBackground(Color.GRAY);
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		JLabel label = new JLabel(text);
		label.setForeground(Color.BLUE);
		panel.add(label);
		panel.add(Box.createHorizontalGlue());
		return panel;
	}
	
	public Builders b() {
		return builders;
	}
}
