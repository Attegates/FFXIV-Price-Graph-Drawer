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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author laaks
 */
public class UpdateItemButtonListener implements ActionListener {

    private SQLOperator sqlOperator;
    private JComboBox itemList;

    public UpdateItemButtonListener(SQLOperator sqlOperator, JComboBox itemList) {
        this.sqlOperator = sqlOperator;
        this.itemList = itemList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox itemField = getItems();
        JTextField newNameField = new JTextField();
        itemField.setSelectedItem(this.itemList.getSelectedItem().toString());
        Object[] message = {
            "Item:", itemField,
            "New name:", newNameField
        };

        int choice;
        String oldName;
        String newName;

        do {
            choice = showConfirmDialog(message);
            oldName = itemField.getSelectedItem().toString();
            newName = newNameField.getText();

            message[2] = "New name:"
                    + "\n<html><font color=red>New name cannot be empty.</font></html>";
        } while (choice == 0 && (newName == null || newName.equals("")));

        if (choice == 0) {
            sqlOperator.updateItem(oldName, newName);
            ListPopulator.populateItemList(this.itemList, sqlOperator.getItems());
            this.itemList.setSelectedItem(newName);
        }

    }

    private int showConfirmDialog(Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, "Update item's name", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * Used to make a copy of the itemList JComboBox
     *
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
