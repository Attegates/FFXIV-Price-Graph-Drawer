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
public class ExportAllTxtButtonListener implements ActionListener {

    private SQLOperator sqlOperator;
    private JComboBox itemField;


    public ExportAllTxtButtonListener(SQLOperator sqlOperator, JComboBox itemField) {
        this.sqlOperator = sqlOperator;
        this.itemField = itemField;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String itemName = itemField.getSelectedItem().toString();

            ArrayList<String> dates = sqlOperator.getAvailableDates(itemName);
            ArrayList<Integer> prices = sqlOperator.getPrices(itemName);

            String absPath = fileChooser.getSelectedFile().getAbsolutePath();
            new FileExporter().createTxtFile(absPath, itemName, dates, prices);
        }

    }

}
