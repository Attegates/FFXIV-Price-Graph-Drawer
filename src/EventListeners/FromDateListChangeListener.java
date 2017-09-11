/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import Main.ListPopulator;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author laaks
 */
public class FromDateListChangeListener implements ItemListener {

    JComboBox fromDateList;
    JComboBox toDateList;

    public FromDateListChangeListener(JComboBox fromDateList, JComboBox toDateList) {
        this.fromDateList = fromDateList;
        this.toDateList = toDateList;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {

        if (ie.getStateChange() == ItemEvent.SELECTED) {
            ArrayList<String> dateList = new ArrayList<>();

            // Get the selected date and all dates after that.
            int i = this.fromDateList.getSelectedIndex();
            int j = this.fromDateList.getItemCount();
            int fromIndex = i;
            int toIndex = this.toDateList.getSelectedIndex();
            for (; i < j; i++) {
                dateList.add(fromDateList.getItemAt(i).toString());
            }

            // Populate the toDateList.
            ListPopulator.populateItemList(toDateList, dateList);

        }

    }

}
