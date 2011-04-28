package uk.co.oliwali.RouteFinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserPanel implements ItemListener, ChangeListener {

	private SpringLayout layout = new SpringLayout();
	private JPanel userPanel = new JPanel(layout);
	private JComboBox fromList = new JComboBox();
	private JComboBox toList = new JComboBox();
	private JLabel priceTime = new JLabel("");
	private JSpinner dateInput = new JSpinner(new SpinnerDateModel());
	private JPanel stopPanel = new JPanel();
	private JLabel toText = new JLabel("to");
	private JLabel dateText = new JLabel("Date: ");
	private JLabel bus1 = new JLabel(new ImageIcon("images/bus.png"));
	private JLabel bus2 = new JLabel(new ImageIcon("images/bus.png"));
	
	/**
	 * Creates a user tab. Multiple instances possible.
	 */
	public UserPanel() {
		
		//Initiate content
		priceTime.setFont(new Font("Dialog", 1, 18));
		stopPanel.setBorder(BorderFactory.createTitledBorder("Stops"));
		stopPanel.setPreferredSize(new Dimension(570, 100));
		dateInput.addChangeListener(this);
		
		//Add to tab pane
		RouteFinder.tabs.add("Main", userPanel);
		
		//Add content
		userPanel.add(fromList);
		userPanel.add(toList);
		userPanel.add(priceTime);
		userPanel.add(toText);
		userPanel.add(dateText);
		userPanel.add(dateInput);
		userPanel.add(stopPanel);
		userPanel.add(bus1);
		userPanel.add(bus2);
		
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
		
		layout.putConstraint(SpringLayout.WEST, priceTime, 135, SpringLayout.WEST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, priceTime, 10, SpringLayout.SOUTH, dateInput);
		
		layout.putConstraint(SpringLayout.WEST, stopPanel, 10, SpringLayout.WEST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, stopPanel, 10, SpringLayout.SOUTH, priceTime);
		
		layout.putConstraint(SpringLayout.WEST, bus1, 30, SpringLayout.WEST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, bus1, 20, SpringLayout.NORTH, userPanel);
		
		layout.putConstraint(SpringLayout.EAST, bus2, -25, SpringLayout.EAST, userPanel);
		layout.putConstraint(SpringLayout.NORTH, bus2, 20, SpringLayout.NORTH, userPanel);
		
		populateCombos(true);
		
	}
	
	/**
	 * Re-populates the two combo boxes, making sure they cannot be the same.
	 * @param start if true, indicates that it is initial run and does not
	 *              preserve selected items
	 */
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

		updateInfo();
		
	}
	
	/**
	 * Updates price, time, stops and page title
	 */
	public void updateInfo() {
		
		Route route = RouteFinder.routeManager.getRoute((String)fromList.getSelectedItem(), (String)toList.getSelectedItem());
		priceTime.setText("Price: " + route.getPrice(((SpinnerDateModel)dateInput.getModel()).getDate()) + " Time: " + route.getFormattedTime());
		
		//Remove old stops and repaint
		stopPanel.removeAll();
		stopPanel.repaint();
		
		//Add in new stops
		JLabel start = new JLabel(route.start.getName());
		start.setForeground(Color.GRAY);
		start.setFont(new Font("Dialog", 1, 16));
		stopPanel.add(start);
		stopPanel.add(new JLabel("=>"));
		for (String stop : route.getStops()) {
			JLabel stopLabel = new JLabel(stop);
			stopLabel.setForeground(Color.GRAY);
			stopLabel.setFont(new Font("Dialog", 1, 16));
			stopPanel.add(stopLabel);
			stopPanel.add(new JLabel("=>"));
		}
		JLabel end = new JLabel(route.end.getName());
		end.setForeground(Color.GRAY);
		end.setFont(new Font("Dialog", 1, 16));
		stopPanel.add(end);
		
		//Set page title
		RouteFinder.setTitle("Main");
	}
	
	/**
	 * Listener for item state changes. Should not be manually called.
	 * @param event {@link ItemEvent} to parse
	 */
	public void itemStateChanged(ItemEvent event) {
		Object source = event.getSource();
		if (source == fromList || source == toList)
			populateCombos(false);
	}
	
	/**
	 * Listener for state changes. Should not be manually called.
	 * @param event {@link ChangeEvent} to parse
	 */
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
		if (source == dateInput)
			populateCombos(false);
	}

}