package ua.com.integer.dde.extension.resource.monitor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.badlogic.gdx.utils.Array;

import ua.com.integer.dde.kernel.DDKernel;
import ua.com.integer.dde.res.load.LoadManager;
import ua.com.integer.dde.res.load.PathDescriptorLoadManager;
import ua.com.integer.dde.res.screen.AbstractScreen;
import ua.com.integer.dde.startpanel.FrameTools;
import ua.com.integer.dde.startpanel.extension.Extension;

import java.awt.Color;

public class ResourceMonitor extends JFrame implements Extension {
	private static final long serialVersionUID = 8310327913242818626L;

	private JPanel contentPane;
	
	private Array<ResourceTab> resourceTabs = new Array<ResourceTab>();
	
	private boolean shouldStop;
	
	private JTabbedPane tabbedPane;
	
	class UpdateTask extends TimerTask {
		private boolean attached;
		
		public void run() {
			if (shouldStop) {
				cancel();
			} else {
				attach();
				for(ResourceTab tab: resourceTabs) {
					tab.update();
				}
			}
		}
		
		private void attach() {
			if (attached) {
				return;
			}

			if (AbstractScreen.getKernel() != null) {
				startMonitoring(AbstractScreen.getKernel(), new MonitorConfig());
				attached = true;
				System.out.println("Attached");
			}
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ResourceMonitor();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ResourceMonitor() {
		new Timer().schedule(new UpdateTask(), 1000, 1000);
		
		setTitle("ResourceMonitor");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		setVisible(true);
		FrameTools.situateOnCenter(this);
	}
	
	public void startMonitoring(String title, PathDescriptorLoadManager manager) {
		ResourceTab tab = new ResourceTab();
		tab.setPathDescriptorLoadManager(manager);
		resourceTabs.add(tab);
		tabbedPane.add(title, tab);
	}
	
	public void startMonitoring(DDKernel kernel, MonitorConfig config) {
		for(String key: config.managers.keys()) {
			System.out.println("try to use: " + key);
			LoadManager manager = kernel.getResourceManager().getManager(config.managers.get(key));
			System.out.println("manager is : " + manager);
			if (manager != null && manager instanceof PathDescriptorLoadManager) {
				System.out.println("add tab");
				startMonitoring(key, (PathDescriptorLoadManager) manager);
			}
		}
	}
	
	class WindowClosingListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			shouldStop = true;
			dispose();
			super.windowClosing(e);
		}
	}

	@Override
	public void launch() {
	}
}
