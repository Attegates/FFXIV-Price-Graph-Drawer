/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Atte
 */
import java.util.ArrayList;
import org.knowm.xchart.*;

/**
 * Used to create a Histogram chart To use call the getHistogram method and create a
 CategoryChart object. Show the created chart by calling new
 * SwingWrapper&lt;&gt;(chart).displayChart();
 */
public class Graphs {

    /**
     * Returns a Histogram style CategoryChart object.
     *
     * @param itemName - The item whose info is displayed.
     * @param values - Y axis values (prices).
     * @param dates - X axis values (dates).
     * @return A histogram style CategoryChart object
     */
    
    public CategoryChart getHistogram(String itemName, ArrayList<Integer> values, ArrayList<String> dates) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Histogram").xAxisTitle("Date").yAxisTitle("Price").build();

        chart.getStyler().setHasAnnotations(true);

        chart.addSeries(itemName, dates, values);

        return chart;
    }

}
