package uk.co.oliwali.RouteFinder;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserPanel implements ItemListener {

	public JPanel userPanel = new JPanel(false);

	public JComboBox fromList;
	public JComboBox toList;
	public JLabel priceTime;
	
	public UserPanel() {
		
		//Add to tab pane
		RouteFinder.tabs.add("Main", userPanel);
		
		//Initiate combo boxes
		fromList = new JComboBox();
		toList   = new JComboBox();
		userPanel.add(fromList);
		userPanel.add(toList);
		
		populateCombos(true);
		
		/*//Initial setup of combo boxes
		for (Depot depot : Depot.values()) {
			toList.addItem(depot.getName());
			fromList.addItem(depot.getName());
		}
		
		//Set different indexes and remove opposites
		toList.setSelectedIndex(1);
		toList.removeItemAt(0);
		fromList.setSelectedIndex(0);
		fromList.removeItemAt(1);
		
		//Add item listener
		fromList.addItemListener(this);
		toList.addItemListener(this);*/
		
		priceTime = new JLabel("");
		userPanel.add(priceTime);
		
	}
	
	private void populateCombos(boolean start) {
		
		String from;
		String to;
		
		if (start) {
			from = RouteFinder.routeManager.getDepot(1).getName();
			to   = RouteFinder.routeManager.getDepot(2).getName();
		}
		else {
			fromList.removeItemListener(this);
			toList.removeItemListener(this);
			from = (String) fromList.getSelectedItem();
			to   = (String) toList.getSelectedItem();
			fromList.removeAllItems();
			toList.removeAllItems();
		}
		
		//Compile array of depot names
		for (Depot depot : Depot.values()) {
			toList.addItem(depot.getName());
			fromList.addItem(depot.getName());
		}
		fromList.setSelectedItem(from);
		toList.setSelectedItem(to);
		fromList.removeItem(to);
		toList.removeItem(from);
		
		fromList.addItemListener(this);
		toList.addItemListener(this);
		
		Route route = RouteFinder.routeManager.getRoute(from, to);
		priceTime.setText("Price: " + route.start.getName());
		
	}
	
	public void itemStateChanged(ItemEvent event) {
		
		Object source = event.getSource();
		if (source == fromList || source == toList)
			this.populateCombos(false);
		
	}

}
