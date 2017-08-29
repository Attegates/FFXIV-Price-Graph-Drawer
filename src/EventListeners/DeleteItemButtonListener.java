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
import javax.swing.UIManager;

/**
 *
 * @author laaks
 */
public class DeleteItemButtonListener implements ActionListener {

    private SQLOperator sqlOperator;
    private JComboBox itemList;

    public DeleteItemButtonListener(SQLOperator sqlOperator, JComboBox itemList) {
        this.sqlOperator = sqlOperator;
        this.itemList = itemList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String item = (String) JOptionPane.showInputDialog(null, "Select item to be deleted.",
                "Delete item", 0, UIManager.getIcon("OptionPane.warningIcon"),
                getItems(), itemList.getSelectedItem());
        
        if (item != null) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Confirm deletion of " + item + "."
                    + "\n Note that this will delete all info of the selected item!",
                    "Confirm deletion",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            
            // Delete the item and update the item list if user clicked ok.
            if (choice == 0) {
                sqlOperator.deleteItem(item);
                ListPopulator.populateItemList(itemList, sqlOperator.getItems());
            }
        }
    }

    /**
     * Get all items in the item list.
     * Used to populate the list for JOptionPane.showConfirmDialog since
     * it takes the options list as an array of objects.
     * @return All the items in the itemlist as an array of objects.
     */
    private Object[] getItems() {
        int size = this.itemList.getItemCount();
        Object[] options = new Object[size];

        for (int i = 0; i < size; i++) {
            options[i] = this.itemList.getItemAt(i).toString();
        }

        return options;
    }

}
