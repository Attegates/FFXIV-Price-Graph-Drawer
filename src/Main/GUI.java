/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import EventListeners.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Atte
 */
public class GUI implements Runnable {

    private JFrame frame;
    private SQLOperator sqlOperator;

    public GUI(SQLOperator sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    @Override
    public void run() {
        frame = new JFrame("Price Graph Drawer");
        frame.setPreferredSize(new Dimension(400, 200));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {

        JComboBox fromDateList = new JComboBox();
        JComboBox toDateList = new JComboBox();
        JComboBox itemList = new JComboBox();
        
        JButton newPriceButton = new JButton("Add price info");
        JButton showHistogramButton = new JButton("Show price histogram");

        itemList.addItemListener(new ItemListChangeListener(this.sqlOperator, fromDateList, toDateList));
        ListPopulator.populateItemList(itemList, this.sqlOperator.getItems());
        fromDateList.addItemListener(new FromDateListChangeListener(fromDateList, toDateList));
        newPriceButton.addActionListener(new NewPriceInfoButtonListener(sqlOperator, itemList, fromDateList, toDateList));
        showHistogramButton.addActionListener(new ShowHistogramButtonListener(itemList, fromDateList, toDateList, sqlOperator));

        // LAYOUT, quite ugly but will suffice for the time being.
        GridBagLayout layout = new GridBagLayout();
        container.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        container.add(itemList, c);
        
        c.gridx = 1;
        c.gridy = 0;
        container.add(newPriceButton, c);

        c.gridx = 0;
        c.gridy = 1;
        container.add(fromDateList, c);

        c.gridx = 1;
        c.gridy = 1;
        container.add(toDateList, c);
        
        c.gridx = 0;
        c.gridy = 2;
        container.add(showHistogramButton, c);

        // LAYOUT
        
        // MenuBar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New Item");
        JMenuItem deleteItem = new JMenuItem("Delete Item");
        JMenuItem updateItem = new JMenuItem("Update Item");
        JMenuItem deletePrice = new JMenuItem("Delete price");
        
        JMenu exportMenu = new JMenu("Export");
        JMenu exportTxtSubMenu = new JMenu("As txt");
        exportMenu.add(exportTxtSubMenu);
        JMenuItem exportAllTxt = new JMenuItem("All values for currently selected item");
        JMenuItem exportSelectTxt = new JMenuItem("Currently selected values");      
        
        newItem.addActionListener(new NewItemButtonListener(this.sqlOperator, itemList));
        deleteItem.addActionListener(new DeleteItemButtonListener(this.sqlOperator, itemList));
        updateItem.addActionListener(new UpdateItemButtonListener(sqlOperator, itemList));
        deletePrice.addActionListener(new DeletePriceButtonListener(sqlOperator, itemList, toDateList));
        exportAllTxt.addActionListener(new ExportAllTxtButtonListener(sqlOperator, itemList));
        exportSelectTxt.addActionListener(new ExportSelectedTxtButtonListener(sqlOperator, itemList, fromDateList, toDateList));        

        fileMenu.add(newItem);
        fileMenu.add(deleteItem);
        fileMenu.add(updateItem);
        fileMenu.add(deletePrice);
        exportTxtSubMenu.add(exportAllTxt);
        exportTxtSubMenu.add(exportSelectTxt);

        menuBar.add(fileMenu);
        menuBar.add(exportMenu);
        frame.setJMenuBar(menuBar);
        // MenuBar
    }
}
