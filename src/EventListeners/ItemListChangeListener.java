/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import Main.ListPopulator;
import Main.SQLOperator;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Atte
 */
public class ItemListChangeListener implements ItemListener {

    SQLOperator sqlOperator;
    JComboBox fromDateList;
    JComboBox toDateList;

    public ItemListChangeListener(SQLOperator sqlOperator, JComboBox fromDateList, JComboBox toDateList) {
        this.sqlOperator = sqlOperator;
        this.fromDateList = fromDateList;
        this.toDateList = toDateList;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {

        // Get only the item that is selected
        // Not 2 items (selected and deselected)
        if (ie.getStateChange() == ItemEvent.SELECTED) {         

            // Get the selected items name
            String itemName = ie.getItem().toString();
            // Get the dates with SELECT from the database
            ArrayList<String> dateList = sqlOperator.getAvailableDates(itemName);            
            
            //Remove the itemChangeListener from the fromDateList to prevent it from firing
            ItemListener[] iList = fromDateList.getItemListeners();
            fromDateList.removeItemListener(iList[0]);

            //Populate the JComboBox lists.
            ListPopulator.populateItemList(fromDateList, dateList);
            ListPopulator.populateItemList(toDateList, dateList);            
            
            //Add the itemChangeListener back to the fromDateList
            fromDateList.addItemListener(iList[0]);
                       
        }

    }

}
