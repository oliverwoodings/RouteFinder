package uk.co.oliwali.RouteFinder;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RouteFinder {
	
	public static RouteManager routeManager = new RouteManager();
	
	public static JFrame mainFrame = new JFrame();
	public static JTabbedPane tabs = new JTabbedPane();
	public static UserPanel userPanel;
	public static AdminPanel adminPanel;
	
	public static void main(String[] args) {
		
		//Set up main frame
		mainFrame.setSize(600, 275);
		mainFrame.setLocation(100, 150);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		//Set up the two tabs and initiate panels
		mainFrame.add(tabs, BorderLayout.CENTER);
		userPanel = new UserPanel();
		adminPanel = new AdminPanel();
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				switch (tabs.getSelectedIndex()) {
					case 0:
						userPanel.updateInfo();
						break;
					case 1:
						adminPanel.updateStopList();
						break;
				}
			}
		});
		
		//Set main title
		RouteFinder.setTitle("Main");
		
	}
	
	/**
	 * Sets the title of the window, prefixed by 'RouteFinder | '
	 * @param suffix string you wish to append to the title
	 */
	public static void setTitle(String suffix) {
		mainFrame.setTitle("RouteFinder | " + suffix);
	}
	
}
