import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class AdminPanel implements ItemListener, ActionListener {

	private SpringLayout adminLayout = new SpringLayout();
	private JPanel adminPanel = new JPanel(adminLayout);
	private SpringLayout routeLayout = new SpringLayout();
	private JPanel routePanel = new JPanel(routeLayout);
	private JComboBox fromList = new JComboBox();
	private JComboBox toList   = new JComboBox();
	private JTextArea stops = new JTextArea();
	private JScrollPane stopsScroll = new JScrollPane(stops);
	private JButton saveStops = new JButton("Save Stops");
	private JLabel toText = new JLabel("to");
	private SpringLayout saveLayout = new SpringLayout();
	private JPanel savePanel = new JPanel(saveLayout);
	private JButton save = new JButton("Save");
	private JButton load = new JButton("Load");
	private JFileChooser saveFile = new JFileChooser();
	private JFileChooser loadFile = new JFileChooser();
	private JLabel info = new JLabel("Enter 1 stop per line");
	
	
	/**
	 * Creates an administration tab. Multiple instances possible.
	 */
	public AdminPanel() {
		
		//Initiate content
		routePanel.setPreferredSize(new Dimension(400, 215));
		routePanel.setBorder(BorderFactory.createTitledBorder("Stops"));
		saveStops.addActionListener(this);
		stopsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		stopsScroll.setPreferredSize(new Dimension(240, 172));
		savePanel.setPreferredSize(new Dimension(172, 215));
		savePanel.setBorder(BorderFactory.createTitledBorder("Save/Load"));
		save.addActionListener(this);
		save.setPreferredSize(new Dimension(110, 50));
		load.addActionListener(this);
		load.setPreferredSize(new Dimension(110, 50));
		
		//Add to tab pane
		RouteFinder.tabs.add("Admin", adminPanel);
		
		//Add content
		adminPanel.add(routePanel);
		routePanel.add(fromList);
		routePanel.add(toText);
		routePanel.add(toList);
		routePanel.add(saveStops);
		routePanel.add(info);
		routePanel.add(stopsScroll);
		adminPanel.add(savePanel);
		savePanel.add(save);
		savePanel.add(load);
		
		//Set constraints
		adminLayout.putConstraint(SpringLayout.WEST, routePanel, 5, SpringLayout.WEST, adminPanel);
		
		routeLayout.putConstraint(SpringLayout.WEST, fromList, 13, SpringLayout.WEST, routePanel);
		routeLayout.putConstraint(SpringLayout.NORTH, fromList, 25, SpringLayout.NORTH, routePanel);
		
		routeLayout.putConstraint(SpringLayout.WEST, toText, 60, SpringLayout.WEST, routePanel);
		routeLayout.putConstraint(SpringLayout.NORTH, toText, 10, SpringLayout.SOUTH, fromList);
		
		routeLayout.putConstraint(SpringLayout.WEST, toList, 13, SpringLayout.WEST, routePanel);
		routeLayout.putConstraint(SpringLayout.NORTH, toList, 10, SpringLayout.SOUTH, toText);
		
		routeLayout.putConstraint(SpringLayout.WEST, saveStops, 19, SpringLayout.WEST, routePanel);
		routeLayout.putConstraint(SpringLayout.NORTH, saveStops, 20, SpringLayout.SOUTH, toList);
		
		routeLayout.putConstraint(SpringLayout.WEST, info, 140, SpringLayout.WEST, routePanel);
		routeLayout.putConstraint(SpringLayout.NORTH, info, -9, SpringLayout.NORTH, routePanel);
		
		routeLayout.putConstraint(SpringLayout.WEST, stopsScroll, 140, SpringLayout.WEST, routePanel);
		routeLayout.putConstraint(SpringLayout.NORTH, stopsScroll, 3, SpringLayout.SOUTH, info);

		adminLayout.putConstraint(SpringLayout.WEST, savePanel, 5, SpringLayout.EAST, routePanel);
		
		saveLayout.putConstraint(SpringLayout.WEST, save, 25, SpringLayout.WEST, savePanel);
		saveLayout.putConstraint(SpringLayout.NORTH, save, 25, SpringLayout.NORTH, savePanel);
		
		saveLayout.putConstraint(SpringLayout.WEST, load, 25, SpringLayout.WEST, savePanel);
		saveLayout.putConstraint(SpringLayout.NORTH, load, 30, SpringLayout.SOUTH, save);
		
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
		
		updateStopList();
		
	}
	
	/**
	 * Gets the selected route from the combo boxes and reads them into the text area.
	 */
	public void updateStopList() {
		stops.setText("");
		Route route = RouteFinder.routeManager.getRoute((String)fromList.getSelectedItem(), (String)toList.getSelectedItem());
		for (String stop : route.getStops())
			stops.append(stop + "\n");
		RouteFinder.setTitle("Admin");
	}
	
	/**
	 * Saves the inputted stops in the text area to the selected route
	 */
	private void saveStops() {
		Route route = RouteFinder.routeManager.getRoute((String)fromList.getSelectedItem(), (String)toList.getSelectedItem());
		route.saveStops(stops.getText());
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
	 * Listener for buttons. Should not be manually called
	 * @param event - {@link ActionEvent} to parse
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == saveStops)
			saveStops();
		else if (source == save) {
			if (saveFile.showSaveDialog(adminPanel) == JFileChooser.APPROVE_OPTION)
				RouteFinder.routeManager.saveRoutesToFile(saveFile.getSelectedFile());
		}
		else if (source == load) {
		    if (loadFile.showOpenDialog(adminPanel) == JFileChooser.APPROVE_OPTION) {
				RouteFinder.routeManager.loadRoutesFromFile(loadFile.getSelectedFile());
				updateStopList();
		    }
		}
	}

}
