package ua.com.integer.dde.extension.config.editor.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.com.integer.dde.startpanel.FrameTools;

public class ColorPropertyEditor extends JDialog implements PropertyEditor {
	private static final long serialVersionUID = -9128198742590743424L;
	private final JPanel contentPanel = new JPanel();
	private JPanel examplePanel;
	private JSlider greenSlider;
	private JSlider blueSlider;
	private JSlider redSlider;
	private JSlider transparencySlider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ColorPropertyEditor dialog = new ColorPropertyEditor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ColorPropertyEditor() {
		setTitle("Color property editor");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblRed = new JLabel("Red:");
		lblRed.setForeground(Color.RED);
		
		JLabel redValue = new JLabel("0");

		redSlider = new JSlider();
		redSlider.addChangeListener(new SliderChangeListener(redSlider, redValue));
		redSlider.setValue(0);
		redSlider.setMaximum(255);
		
		JLabel lblGreen = new JLabel("Green:");
		lblGreen.setForeground(Color.GREEN);
		
		JLabel greenValue = new JLabel("0");

		greenSlider = new JSlider();
		greenSlider.addChangeListener(new SliderChangeListener(greenSlider, greenValue));
		greenSlider.setMaximum(255);
		greenSlider.setValue(0);
		
		JLabel lblBlue = new JLabel("Blue:");
		lblBlue.setForeground(Color.BLUE);
		
		JLabel blueValue = new JLabel("0");

		blueSlider = new JSlider();
		blueSlider.addChangeListener(new SliderChangeListener(blueSlider, blueValue));
		blueSlider.setMaximum(255);
		blueSlider.setValue(0);
		
		JLabel lblExample = new JLabel("Example:");
		
		examplePanel = new JPanel();
		examplePanel.setBackground(Color.BLACK);
		examplePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblTransparency = new JLabel("Transp:");
		
		JLabel transpLabel = new JLabel("0");
		transparencySlider = new JSlider();
		transparencySlider.addChangeListener(new SliderChangeListener(transparencySlider, transpLabel));
		transparencySlider.setMaximum(255);
		transparencySlider.setValue(255);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblRed)
									.addComponent(lblGreen)
									.addComponent(lblBlue))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(greenSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(blueSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addComponent(redSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(blueValue)
									.addComponent(greenValue)
									.addComponent(redValue)))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(100)
								.addComponent(lblExample))
							.addComponent(examplePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblTransparency)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(transparencySlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(transpLabel)))
					.addContainerGap(86, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(redValue)
						.addComponent(lblRed)
						.addComponent(redSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblGreen)
						.addComponent(greenSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(greenValue))
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblBlue)
						.addComponent(blueValue)
						.addComponent(blueSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(transparencySlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTransparency)
						.addComponent(transpLabel))
					.addGap(3)
					.addComponent(lblExample)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(examplePanel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(84, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		pack();
	}
	
	class SliderChangeListener implements ChangeListener {
		private JSlider slider;
		private JLabel sliderValue;
		
		public SliderChangeListener(JSlider slider, JLabel sliderValue) {
			this.slider = slider;
			this.sliderValue = sliderValue;
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			sliderValue.setText(slider.getValue() + "");
			updateColor();
		}
	}
	
	private void updateColor() {
		if (examplePanel != null) {
			examplePanel.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), transparencySlider.getValue()));
		}
	}
	public JPanel getExamplePanel() {
		return examplePanel;
	}
	public JSlider getGreenSlider() {
		return greenSlider;
	}
	public JSlider getBlueSlider() {
		return blueSlider;
	}
	public JSlider getRedSlider() {
		return redSlider;
	}
	public JSlider getTransparencySlider() {
		return transparencySlider;
	}

	@Override
	public void setEditListener(final ActionListener editListener) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				editListener.actionPerformed(null);
			}
		});
	}

	@Override
	public void setValue(String value) {
		String[] colorParts = value.split(" ");
		
		if (colorParts.length < 3) {
			redSlider.setValue(255);
			greenSlider.setValue(255);
			blueSlider.setValue(255);
			transparencySlider.setValue(255);
			updateColor();
			return;
		}
		
		int r = (int) (Float.parseFloat(colorParts[0]) * 255f);
		int g = (int) (Float.parseFloat(colorParts[1]) * 255f);
		int b = (int) (Float.parseFloat(colorParts[2]) * 255f);
		int t = 255;
		if (colorParts.length > 3) {
			t = (int) (Float.parseFloat(colorParts[3]) * 255f);
		}
		
		redSlider.setValue(r);
		greenSlider.setValue(g);
		blueSlider.setValue(b);
		transparencySlider.setValue(t);
		
		updateColor();
	}

	@Override
	public String getValue() {
		float r = (float) redSlider.getValue() / 255f;
		float g = (float) greenSlider.getValue() / 255f;
		float b = (float) blueSlider.getValue() / 255f;
		float t = (float) transparencySlider.getValue() / 255f;
		return r + " " + g + " " + b + " " + t;
	}

	@Override
	public void showEditor() {
		FrameTools.situateOnCenter(this);
		setModal(true);
		setVisible(true);
	}
}
