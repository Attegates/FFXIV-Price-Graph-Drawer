/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author laaks
 */
public class FileExporter {

    private String ls;

    public FileExporter() {
        this.ls = System.getProperty("line.separator");
    }

    /**
     * Writes a txt file that contains the given items date-price information.
     * @param absPath - Absolute path to the file.
     * @param itemName
     * @param dates
     * @param values 
     */
    public void createTxtFile(String absPath, String itemName, ArrayList<String> dates, ArrayList<Integer> values) {

        try (FileWriter fw = new FileWriter(absPath)) {
            fw.write("Item: " + itemName + ls + ls);

            // Date and value lists have the same amount of items.
            for (int i = 0; i < dates.size(); i++) {
                fw.write(dates.get(i) + "\t-\t" + values.get(i) + ls);
            }
        } catch (IOException ex) {
            System.out.println("Error writing file.");
        }
    }
    
    /*
    public void createCsvFile() {
        
    }
    */

}
