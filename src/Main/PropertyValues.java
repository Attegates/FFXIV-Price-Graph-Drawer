/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Atte
 */
public class PropertyValues {

    InputStream inputStream = null;

    public String getValue(String key) throws IOException {
        String value = "";

        try {
            Properties prop = new Properties();
            String fileName = "Resources/config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file " + fileName + " not found.");
            }

            value = prop.getProperty(key);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        return value;
    }

}
