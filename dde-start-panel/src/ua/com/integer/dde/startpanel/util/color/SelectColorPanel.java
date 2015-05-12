package ua.com.integer.dde.startpanel.util.color;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.startpanel.FrameTools;

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
	private JTextField htmlColorNotation;

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
		
		JLabel lblHtml = new JLabel("HTML:");
		
		htmlColorNotation = new JTextField();
		htmlColorNotation.addKeyListener(new HTMLTextListener());
		htmlColorNotation.setColumns(10);
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
								.addComponent(lblBlue, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
								.addComponent(lblAlpha))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblHtml)
							.addGap(17)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(htmlColorNotation)
						.addComponent(alphaSlider, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
						.addComponent(blueSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(greenSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(redSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(redLabel)
						.addComponent(greenLabel)
						.addComponent(blueLabel)
						.addComponent(alphaLabel))
					.addGap(57)
					.addComponent(examplePanel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
							.addComponent(examplePanel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
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
								.addComponent(blueSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(blueLabel)
								.addComponent(lblBlue))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAlpha)
								.addComponent(alphaLabel)
								.addComponent(alphaSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblHtml)
								.addComponent(htmlColorNotation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(50, Short.MAX_VALUE))
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
			System.out.println("State changed");
			examplePanel.setBackground(getColor());
		
			updateLabels();
			
			String htmlColor = getHTMLColor();
			if (!htmlColor.equals(htmlColorNotation.getText())) {
				htmlColorNotation.setText(htmlColor);
			}
			
			if (colorChangeListener != null) colorChangeListener.colorChanged(getColor());
		}
	}
	
	private String getHTMLColor() {
		String result = "";
		String hexR = Integer.toHexString(getColor().getRed());
		String hexG = Integer.toHexString(getColor().getBlue());
		String hexB = Integer.toHexString(getColor().getGreen());
		String hexA = Integer.toHexString(getColor().getAlpha());
		result = hexR + hexG + hexB;

		if (!hexA.toLowerCase().equals("ff")) {
			result += hexA;
		}
		
		return result.toLowerCase();
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
	
	class HTMLTextListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String htmlColor = htmlColorNotation.getText();
				if (htmlColor.length() == 6 || htmlColor.length() == 8) {
					String r = htmlColor.substring(0, 2);
					String g = htmlColor.substring(2, 4);
					String b = htmlColor.substring(4, 6);
					String a = "ff";
					if (htmlColor.length() == 8) {
						a = htmlColor.substring(6, 8);
					}
					
					try {
						int intR = Integer.valueOf(r, 16);
						int intG = Integer.valueOf(g, 16);
						int intB = Integer.valueOf(b, 16);
						int intA = Integer.valueOf(a, 16);
						
						Color newColor = new Color(intR, intG, intB, intA);
						setColor(newColor);
					} catch (Exception ex) {
						showError();
					}
				} else {
					showError();
				}
			}
			super.keyReleased(e);
		}
		
		private void showError() {
			JOptionPane.showMessageDialog(null, "Input color in html notation. Example: \"rrggbb\" or \"rrggbbaa\"");
		}
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
	public JTextField getHtmlColorNotation() {
		return htmlColorNotation;
	}
	
	public static void main(String[] args) {
		FrameTools.testingFrame(new SelectColorPanel());
	}
}
