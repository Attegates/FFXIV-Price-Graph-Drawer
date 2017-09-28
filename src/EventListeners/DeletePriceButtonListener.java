/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import Main.ListPopulator;
import Main.SQLOperator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author laaks
 */
public class DeletePriceButtonListener implements ActionListener {

    private SQLOperator sqlOperator;
    private JComboBox itemList;
    private JComboBox dateList;

    public DeletePriceButtonListener(SQLOperator sqlOperator, JComboBox itemList, JComboBox dateList) {
        this.sqlOperator = sqlOperator;
        this.itemList = itemList;
        this.dateList = dateList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox itemField = getItems();
        JComboBox dateField = new JComboBox();

        itemField.addActionListener(ae -> {
            ListPopulator.populateItemList(dateField, sqlOperator.getAvailableDates(itemField.getSelectedItem().toString()));
        });
        
        // Set currently selected values. Also forces itemField actionlistener to populate the datefield.
        itemField.setSelectedItem(itemList.getSelectedItem());
        dateField.setSelectedItem(dateList.getSelectedItem());

        Object[] message = {
            "Item", itemField,
            "Date", dateField
        };

        int choice = JOptionPane.showConfirmDialog(null, message, "abc", JOptionPane.OK_CANCEL_OPTION);
        
        if (choice == 0) {
           String item = itemField.getSelectedItem().toString();
           String date = dateField.getSelectedItem().toString();
           sqlOperator.deletePrice(item, date);
           // Reset the choice in the original list to ensure datelists get updated.
           this.itemList.setSelectedItem(null);
           this.itemList.setSelectedItem(item);
        }

    }

    /**
     * Used to make a copy of the itemList JComboBox
     * The copy is used to prevent visual bugs. That would happen
     * if using the original list.
     * @return A copy of the itemList
     */
    private JComboBox getItems() {
        int size = this.itemList.getItemCount();
        JComboBox items = new JComboBox();

        for (int i = 0; i < size; i++) {
            items.addItem(this.itemList.getItemAt(i).toString());
        }

        return items;
    }

}
