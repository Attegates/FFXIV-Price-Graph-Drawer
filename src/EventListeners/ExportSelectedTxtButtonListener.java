/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import Main.FileExporter;
import Main.SQLOperator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author laaks
 */
public class ExportSelectedTxtButtonListener implements ActionListener {

    private SQLOperator sqlOperator;
    private JComboBox itemField;
    private JComboBox fromDateField;
    private JComboBox toDateField;

    public ExportSelectedTxtButtonListener(SQLOperator sqlOperator, JComboBox itemField, JComboBox fromDateField, JComboBox toDateField) {
        this.sqlOperator = sqlOperator;
        this.itemField = itemField;
        this.fromDateField = fromDateField;
        this.toDateField = toDateField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String itemName = itemField.getSelectedItem().toString();
            String fromDate = fromDateField.getSelectedItem().toString();
            String toDate = toDateField.getSelectedItem().toString();

            ArrayList<String> dates = sqlOperator.getDatesBetween(itemName, fromDate, toDate);
            ArrayList<Integer> prices = sqlOperator.getPrices(itemName, fromDate, toDate);
            
            String absPath = fileChooser.getSelectedFile().getAbsolutePath();
            new FileExporter().createTxtFile(absPath, itemName, dates, prices);
        }

    }

}
