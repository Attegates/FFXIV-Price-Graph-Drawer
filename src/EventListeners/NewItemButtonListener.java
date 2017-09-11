/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import Main.ListPopulator;
import Main.SQLOperator;
import CustomExceptions.UniqueConstraintException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Atte
 */
public class NewItemButtonListener implements ActionListener {

    SQLOperator sqlops;
    JComboBox itemList;

    public NewItemButtonListener(SQLOperator sqlOperator, JComboBox itemList) {
        this.sqlops = sqlOperator;
        this.itemList = itemList;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Get the items name from the user.
        String itemName = JOptionPane.showInputDialog("Add a new item.");

        // Add the item to the database and the JComboBox list.
        if (itemName != null && !itemName.equals("")) {
            try {
                sqlops.newItem(itemName);
                ListPopulator.populateItemList(itemList, sqlops.getItems());
            } catch (UniqueConstraintException e) {
                JOptionPane.showMessageDialog(null, "Item already exists.");
            }
            itemList.setSelectedItem(itemName);
        }
    }

}
