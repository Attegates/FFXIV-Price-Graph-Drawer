/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author laaks
 */
public class ListPopulator {

    /**
     * *
     * Populate the item list with the items found in the database
     *
     * @param list The JComboBox to populate
     * @param itemList List of items to be added into the JComboBox
     */
    public static void populateItemList(JComboBox list, ArrayList<String> itemList) {
        list.removeAllItems();

        itemList.stream().forEach((item) -> {
            list.addItem(item);
        });

    }
}
