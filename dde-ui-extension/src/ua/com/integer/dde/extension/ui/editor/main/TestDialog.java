package ua.com.integer.dde.extension.ui.editor.main;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ua.com.integer.dde.extension.ui.editor.EditorKernel;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

public class TestDialog extends JDialog {
	private static final long serialVersionUID = 592988096643896569L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TestDialog dialog = new TestDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TestDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		LwjglAWTCanvas canvas = new LwjglAWTCanvas(EditorKernel.getInstance());
		canvas.getCanvas().setSize(400, 400);
		contentPanel.add(canvas.getCanvas());
	}

}
