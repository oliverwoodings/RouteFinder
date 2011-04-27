package uk.co.oliwali.RouteFinder;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserPanel implements ItemListener, ChangeListener {

	public SpringLayout layout;
	public JPanel userPanel;
	public JComboBox fromList;
	public JComboBox toList;
	public JLabel priceTime;
	public JSpinner dateInput;
	
	public UserPanel() {
		
		//Set up date spinner
		Calendar end = Calendar.getInstance();
		Calendar start = Calendar.getInstance();
		end.set(Calendar.YEAR + 100, Calendar.MONTH, Calendar.DATE);
		start.set(Calendar.YEAR - 1, Calendar.MONTH, Calendar.DATE);
		dateInput = new JSpinner(new SpinnerDateModel());
		dateInput.addChangeListener(this);
		
		//Initiate content
		layout = new SpringLayout();
		userPanel = new JPanel(layout);
		fromList = new JComboBox();
		toList   = new JComboBox();
		priceTime = new JLabel("");
		priceTime.setFont(new Font("Dialog", 1, 18));
		JLabel toText = new JLabel("to");
		JLabel dateText = new JLabel("Date: ");
		
		//Add to tab pane
		RouteFinder.tabs.add("Main", userPanel);
		
		//Add content
		userPanel.add(fromList);
		userPanel.add(toList);
		userPanel.add(priceTime);
		userPanel.add(toText);
		userPanel.add(dateText);
		userPanel.add(dateInput);
		
		//Set constraints
		layout.putConstraint(SpringLayout.WEST, fromList, 173, SpringLayout.WEST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, fromList, 10, SpringLayout.NORTH, userPanel);
		
		layout.putConstraint(SpringLayout.WEST, toText, 10, SpringLayout.EAST, fromList);
		layout.putConstraint(SpringLayout.NORTH, toText, 13, SpringLayout.NORTH, userPanel);
		
		layout.putConstraint(SpringLayout.WEST, toList, 10, SpringLayout.EAST, toText);
		layout.putConstraint(SpringLayout.NORTH, toList, 10, SpringLayout.NORTH, userPanel);
		
		layout.putConstraint(SpringLayout.WEST, dateText, 225, SpringLayout.WEST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, dateText, 10, SpringLayout.SOUTH, fromList);
		
		layout.putConstraint(SpringLayout.WEST, dateInput, 5, SpringLayout.EAST, dateText);
		layout.putConstraint(SpringLayout.NORTH, dateInput, 10, SpringLayout.SOUTH, fromList);
		
		layout.putConstraint(SpringLayout.WEST, priceTime, 170, SpringLayout.WEST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, priceTime, 10, SpringLayout.SOUTH, dateInput);
		
		populateCombos(true);
		
	}
	
	private void populateCombos(boolean start) {
		
		String from;
		String to;
		
		//If it is initial run
		if (start) {
			from = RouteFinder.routeManager.getDepot(1).getName();
			to   = RouteFinder.routeManager.getDepot(2).getName();
		}
		//Else remove listener so it doesn't bug out and backup old selected
		else {
			fromList.removeItemListener(this);
			toList.removeItemListener(this);
			from = (String) fromList.getSelectedItem();
			to   = (String) toList.getSelectedItem();
			fromList.removeAllItems();
			toList.removeAllItems();
		}
		
		//Add depots back in
		for (Depot depot : Depot.values()) {
			toList.addItem(depot.getName());
			fromList.addItem(depot.getName());
		}
		//Set selected items and remove opposite selected items
		fromList.setSelectedItem(from);
		toList.setSelectedItem(to);
		fromList.removeItem(to);
		toList.removeItem(from);
		
		//Add listeners back
		fromList.addItemListener(this);
		toList.addItemListener(this);

		updatePriceTime();
		
	}
	
	private void updatePriceTime() {
		Route route = RouteFinder.routeManager.getRoute((String)fromList.getSelectedItem(), (String)toList.getSelectedItem());
		priceTime.setText("Price: " + route.getPrice(((SpinnerDateModel)dateInput.getModel()).getDate()) + " Time: " + route.getTime());
	}
	
	public void itemStateChanged(ItemEvent event) {
		Object source = event.getSource();
		if (source == fromList || source == toList)
			this.populateCombos(false);
	}
	
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
		if (source == dateInput)
			updatePriceTime();
	}

}
