package uk.co.oliwali.RouteFinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class AdminPanel implements ActionListener {

	public JPanel adminPanel = new JPanel(false);
	
	public AdminPanel() {
		
		RouteFinder.tabs.add("Admin", adminPanel);
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
	}

}
