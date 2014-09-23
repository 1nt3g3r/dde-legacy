package ua.com.integer.dde.startpanel.export;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

public class ExportTextListener implements BuildListener {
	private JTextPane text;
	
	public ExportTextListener(JTextPane text) {
		this.text = text;
	}
	
	@Override
	public void buildFinished(BuildEvent event) {
		System.out.println("finished " + event.getMessage());
	}

	@Override
	public void buildStarted(BuildEvent event) {
		System.out.println("start");
	}

	@Override
	public void messageLogged(BuildEvent event) {
		print(event.getMessage());
	}

	@Override
	public void targetFinished(BuildEvent event) {
		print("End target: " + event.getTarget().getName());
	}

	@Override
	public void targetStarted(BuildEvent event) {
		print("Begin target: " + event.getTarget().getName());
	}

	@Override
	public void taskFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	private void print(final String txt) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				text.setText(text.getText() + "\n" + txt);
			}
		});
	}
}
