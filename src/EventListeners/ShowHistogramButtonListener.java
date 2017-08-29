/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventListeners;

import Main.Graphs;
import Main.SQLOperator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.SwingWrapper;

/**
 *
 * @author Atte
 */
public class ShowHistogramButtonListener implements ActionListener {

    JComboBox itemList;
    JComboBox fromDateList;
    JComboBox toDateList;
    SQLOperator sqlOperator;

    public ShowHistogramButtonListener(JComboBox itemList, JComboBox fromDateList, JComboBox toDateList, SQLOperator sqlOperator) {
        this.itemList = itemList;
        this.fromDateList = fromDateList;
        this.toDateList = toDateList;
        this.sqlOperator = sqlOperator;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String item = itemList.getSelectedItem().toString();
        String fromDate = fromDateList.getSelectedItem().toString();
        String toDate = toDateList.getSelectedItem().toString();

        ArrayList<Integer> values = sqlOperator.getPrices(item, fromDate, toDate);
        ArrayList<String> dates = sqlOperator.getDatesBetween(item, fromDate, toDate);

        Graphs chart = new Graphs();
        CategoryChart histogram = chart.getHistogram(item, values, dates);

        Thread t = new Thread(() -> {
            new SwingWrapper<>(histogram).displayChart().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
        t.start();
    }

}
