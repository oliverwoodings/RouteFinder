package uk.co.oliwali.RouteFinder;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class RouteFinder {
	
	public static RouteManager routeManager = new RouteManager();
	
	public static JFrame mainFrame = new JFrame();
	public static JTabbedPane tabs = new JTabbedPane();
	public static UserPanel userPanel;
	public static AdminPanel adminPanel;
	
	public static void main(String[] args) {
		
		//Set up main frame
		mainFrame.setSize(600, 300);
		mainFrame.setLocation(100, 150);
		RouteFinder.setTitle("Home");
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		//Set up the two tabs and initiate panels
		mainFrame.add(tabs, BorderLayout.CENTER);
		userPanel = new UserPanel();
		adminPanel = new AdminPanel();
		
	}
	
	public static void setTitle(String suffix) {
		mainFrame.setTitle("RouteFinder | " + suffix);
	}
	
}
