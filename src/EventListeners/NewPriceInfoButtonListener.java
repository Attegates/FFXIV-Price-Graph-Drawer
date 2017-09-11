/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import CustomExceptions.UniqueConstraintException;
import Main.ListPopulator;
import Main.SQLOperator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author laaks
 */
public class NewPriceInfoButtonListener implements ActionListener {

    SQLOperator sqlops;
    JComboBox itemList;
    JComboBox fromDateList;
    JComboBox toDateList;

    public NewPriceInfoButtonListener(SQLOperator sqlOperator, JComboBox itemList, JComboBox fromDateList, JComboBox toDateList) {
        this.sqlops = sqlOperator;
        this.itemList = itemList;
        this.fromDateList = fromDateList;
        this.toDateList = toDateList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox items = getItems();
        items.setSelectedItem(itemList.getSelectedItem());
        JTextField dateField = new JTextField();
        JTextField priceField = new JTextField();
        Object[] message = {
            "Item:", items,
            "Date:", dateField,
            "Price:", priceField
        };

        // Fill the datefield with current date.
        String date = getDate();
        dateField.setText(date);
        String price;
        String item;

        //
        int choice;
        do {
            choice = showConfirmDialog(message);
            item = items.getSelectedItem().toString();
            date = dateField.getText();
            price = priceField.getText();

            // Add errors to the message. These will be shown after first cycle of the loop.
            // message[2] to set the text above dateField
            message[2] = "Date:"
                    + "\n<html><font color=red>Use YYYY-MM-DD format.</font></html>";
            // message[4] to set the text above pricefield
            message[4] = "Price:\n"
                    + "<html><font color=red>Use only numbers.</font></html>\n"
                    + "<html><font color=red>Price must be larger than 0.</font></html>";

        } while (choice == 0 && !validateInput(date, price));

        // If user did not cancel (choice == 0) and input was valid
        // add the new price to the database and update the date lists.
        if (choice == 0) {
            int priceAsInt = Integer.parseInt(price);
            try {
                sqlops.newPriceInfo(item, priceAsInt, date);
                updateDateLists(item);
            } catch (UniqueConstraintException ex) {
                JOptionPane.showMessageDialog(null, "Price info for the given date already exists.\n"
                        + "Only one price info per day is permitted.");
            }
        }

    }

    /**
     * Used to make a copy of the itemList JComboBox
     *
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

    /**
     * A simple method to validate the format of the user given input. Checks
     * only the format of the date, not that the actual date is valid. Checks
     * that price is an integer larger than zero.
     *
     * @param date The date to check as a String.
     * @return True if valid false if invalid.
     */
    private boolean validateInput(String date, String price) {

        return (date.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")
                && price.matches("([1-9]+)([0-9]*)"));

    }

    private int showConfirmDialog(Object[] message) {
        return JOptionPane.showConfirmDialog(null, message, "Add new price info.", JOptionPane.OK_CANCEL_OPTION);
    }

    /**
     * 
     * @return Current date as a String in YYYY-MM--DD format. 
     */
    private String getDate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.now();
        return dtf.format(time);

    }

    private void updateDateLists(String itemName) {

        // Get the dates with SELECT from the database
        ArrayList<String> dateList = sqlops.getAvailableDates(itemName);

        //Populate the JComboBox lists.
        ListPopulator.populateItemList(fromDateList, dateList);
        ListPopulator.populateItemList(toDateList, dateList);
    }

}
