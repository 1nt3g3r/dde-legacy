package ua.com.integer.dde.startpanel.util.color;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Панель для выбора цвета в формате RGBA. Помещается в JoptionPane для удобного пользования
 * 
 * @author 1nt3g3r
 */
public class SelectColorPanel extends JPanel {
	private static final long serialVersionUID = 74528241693545820L;
	private JSlider greenSlider;
	private JSlider redSlider;
	private JSlider alphaSlider;
	private JSlider blueSlider;
	private JPanel examplePanel;
	private JLabel greenLabel;
	private JLabel redLabel;
	private JLabel blueLabel;
	private JLabel alphaLabel;
	
	private ColorListener colorChangeListener;

	/**
	 * Create the panel.
	 */
	public SelectColorPanel() {
		setBackground(Color.GRAY);
		
		JLabel lblRed = new JLabel("Red:");
		lblRed.setForeground(Color.RED);
		lblRed.setBackground(Color.RED);
		
		redSlider = new JSlider();
		redSlider.setValue(100);
		redSlider.setForeground(Color.RED);
		redSlider.setBackground(Color.RED);
		
		JLabel lblGreen = new JLabel("Green:");
		lblGreen.setForeground(Color.GREEN);
		
		greenSlider = new JSlider();
		greenSlider.setValue(100);
		greenSlider.setBackground(Color.GREEN);
		
		JLabel lblBlue = new JLabel("Blue:");
		lblBlue.setForeground(Color.BLUE);
		
		blueSlider = new JSlider();
		blueSlider.setValue(100);
		blueSlider.setBackground(Color.BLUE);
		
		JLabel lblAlpha = new JLabel("Alpha:");
		
		alphaSlider = new JSlider();
		alphaSlider.setValue(100);
		
		examplePanel = new JPanel();
		examplePanel.setBackground(Color.WHITE);
		examplePanel.setBorder(new TitledBorder(null, "Example", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		redLabel = new JLabel("1");
		
		greenLabel = new JLabel("1");
		
		blueLabel = new JLabel("1");
		
		alphaLabel = new JLabel("1");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRed)
								.addComponent(lblGreen)
								.addComponent(lblBlue))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(blueSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(greenSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(redSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAlpha)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(alphaSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(redLabel)
						.addComponent(greenLabel)
						.addComponent(blueLabel)
						.addComponent(alphaLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addComponent(examplePanel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(examplePanel, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
							.addComponent(redLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(redSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRed))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblGreen)
								.addComponent(greenSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(greenLabel, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblBlue)
								.addComponent(blueSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(blueLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAlpha)
								.addComponent(alphaSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(alphaLabel))))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		redSlider.addChangeListener(new ColorChangeListener());
		greenSlider.addChangeListener(new ColorChangeListener());
		blueSlider.addChangeListener(new ColorChangeListener());
		alphaSlider.addChangeListener(new ColorChangeListener());
	}
	
	public void setColor(Color color) {
		redSlider.setValue(to1_100(color.getRed()));
		greenSlider.setValue(to1_100(color.getGreen()));
		blueSlider.setValue(to1_100(color.getBlue()));
		alphaSlider.setValue(to1_100(color.getAlpha()));
	}
	
	class ColorChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			examplePanel.setBackground(getColor());
		
			updateLabels();
			
			if (colorChangeListener != null) colorChangeListener.colorChanged(getColor());
		}
	}
	
	public void setColorListener(ColorListener colorListener) {
		this.colorChangeListener = colorListener;
	}
	
	public Color getColor() {
		return new Color(to1(redSlider.getValue()), to1(greenSlider.getValue()), to1(blueSlider.getValue()), to1(alphaSlider.getValue()));
	}
	
	private void updateLabels() {
		redLabel.setText(to1(redSlider.getValue()) + "");
		greenLabel.setText(to1(greenSlider.getValue()) + "");
		blueLabel.setText(to1(blueSlider.getValue()) + "");
		alphaLabel.setText(to1(alphaSlider.getValue()) + "");
	}
	
	private float to1(int value) {
		return ((float) value) / 100f;
	}
	
	private int to1_100(int value) {
		return (int) (((float) value / 255f) * 100f);
	}
	public JSlider getGreenSlider() {
		return greenSlider;
	}
	public JSlider getRedSlider() {
		return redSlider;
	}
	public JSlider getAlphaSlider() {
		return alphaSlider;
	}
	public JSlider getBlueSlider() {
		return blueSlider;
	}
	public JPanel getExamplePanel() {
		return examplePanel;
	}
	public JLabel getGreenLabel() {
		return greenLabel;
	}
	public JLabel getRedLabel() {
		return redLabel;
	}
	public JLabel getBlueLabel() {
		return blueLabel;
	}
	public JLabel getAlphaLabel() {
		return alphaLabel;
	}
}
