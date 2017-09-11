/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import CustomExceptions.UniqueConstraintException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Atte
 */
public class SQLOperator {

    private Connection conn = null;
    private String dburl = "";
    SQLiteConfig sqlConfig;

    public SQLOperator() {
        try {
            // Set foreign key support on
            sqlConfig = new SQLiteConfig();
            sqlConfig.enforceForeignKeys(true);
            this.dburl = "jdbc:sqlite:" + new PropertyValues().getValue("DBPath");
        } catch (IOException e) {
            System.out.println("Couldn't read the path from the property file.");
        }
    }

    private void connect() {
        try {
            this.conn = DriverManager.getConnection(this.dburl, sqlConfig.toProperties());
            System.out.println("Connection established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void disconnect() {
        try {
            if (this.conn != null) {
                this.conn.close();
                System.out.println("Disconnected.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getItems() {
        this.connect();

        String query = "SELECT Name FROM Item ORDER BY name ASC";

        ArrayList<String> items = new ArrayList<>();

        try (PreparedStatement pstmnt = this.conn.prepareStatement(query);
                ResultSet rs = pstmnt.executeQuery()) {

            while (rs.next()) {
                items.add(rs.getString("Name"));
                System.out.println(rs.getString("Name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }

        return items;

    }

    /**
     * *
     * Get the dates that have price information.
     *
     * @param itemName - Item whose dates to retrieve
     * @return An ArrayList&lt;String&gt; of dates that have price info
     */
    public ArrayList<String> getAvailableDates(String itemName) {

        ArrayList<String> dateList = new ArrayList<>();

        String query = "SELECT checkedDate FROM Price WHERE itemName = (?)";
        this.connect();

        try (PreparedStatement pstmnt = this.conn.prepareStatement(query);) {
            pstmnt.setString(1, itemName);
            try (ResultSet rs = pstmnt.executeQuery();) {

                while (rs.next()) {
                    dateList.add(rs.getString("CheckedDate"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }
        return dateList;
    }

    public ArrayList<String> getAvailableDates(String itemName, String fromDate) {
        ArrayList<String> dateList = new ArrayList<>();

        String query = "SELECT checkedDate FROM Price WHERE itemName = (?) AND CheckedDate >= (?)";
        this.connect();

        try (PreparedStatement pstmnt = this.conn.prepareStatement(query);) {

            pstmnt.setString(1, itemName);
            pstmnt.setString(2, fromDate);
            try (ResultSet rs = pstmnt.executeQuery()) {
                while (rs.next()) {
                    dateList.add(rs.getString("CheckedDate"));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }
        return dateList;
    }

    public ArrayList<String> getDatesBetween(String itemName, String fromDate, String toDate) {
        ArrayList<String> dateList = new ArrayList<>();

        String query = "SELECT checkedDate FROM Price WHERE itemName = (?) AND CheckedDate >= (?) AND CheckedDate <= (?)";
        this.connect();

        try (PreparedStatement pstmnt = this.conn.prepareStatement(query);) {

            pstmnt.setString(1, itemName);
            pstmnt.setString(2, fromDate);
            pstmnt.setString(3, toDate);

            try (ResultSet rs = pstmnt.executeQuery()) {
                while (rs.next()) {
                    dateList.add(rs.getString("CheckedDate"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }

        return dateList;
    }

    public ArrayList<Integer> getPrices(String itemName) {

        ArrayList<Integer> priceList = new ArrayList<>();
        String query = "SELECT price FROM Price WHERE itemname = (?)";
        this.connect();

        try (PreparedStatement pstmnt = this.conn.prepareStatement(query)) {

            pstmnt.setString(1, itemName);

            try (ResultSet rs = pstmnt.executeQuery()) {
                while (rs.next()) {
                    priceList.add(rs.getInt("price"));
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }
        return priceList;

    }

    public ArrayList<Integer> getPrices(String itemName, String fromDate, String toDate) {
        ArrayList<Integer> priceList = new ArrayList<>();
        String query = "SELECT price FROM Price WHERE itemName = (?) AND CheckedDate >= (?) AND CheckedDate <= (?)";
        this.connect();

        try (PreparedStatement pstmnt = this.conn.prepareStatement(query);) {

            pstmnt.setString(1, itemName);
            pstmnt.setString(2, fromDate);
            pstmnt.setString(3, toDate);

            try (ResultSet rs = pstmnt.executeQuery()) {
                while (rs.next()) {
                    priceList.add(rs.getInt("Price"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }
        return priceList;
    }

    /**
     * 
     * @param name Items name
     * @throws UniqueConstraintException 
     */
    public void newItem(String name) throws UniqueConstraintException {

        this.connect();

        String insert = "INSERT INTO Item VALUES(?)";

        try (PreparedStatement pstmnt = this.conn.prepareStatement(insert)) {
            pstmnt.setString(1, name);
            pstmnt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getErrorCode() == 19) {
                throw new UniqueConstraintException("Unique constraint violation.");
            }
        } finally {
            this.disconnect();
        }
    }

    /**
     * Adds a price info to the database
     *
     * @param item - Item whose price to update.
     * @param price - Current price as integer.
     * @param date - Current date as string.
     * @throws CustomExceptions.UniqueConstraintException
     */
    public void newPriceInfo(String item, int price, String date) throws UniqueConstraintException {
        this.connect();

        String insert = "INSERT INTO Price VALUES(?,?,?)";

        try (PreparedStatement pstmnt = this.conn.prepareStatement(insert)) {
            pstmnt.setString(1, item);
            pstmnt.setInt(2, price);
            pstmnt.setString(3, date);
            pstmnt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getErrorCode() == 19) {
                throw new UniqueConstraintException("Unique constraint violation.");
            }
        } finally {
            this.disconnect();
        }
    }

    /**
     * Delete all info of an item
     *
     * @param item the item whose info to delete
     */
    public void deleteItem(String item) {
        this.connect();

        String delete = "DELETE FROM item WHERE name = (?)";

        try (PreparedStatement pstmnt = this.conn.prepareStatement(delete)) {
            pstmnt.setString(1, item);
            pstmnt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }

    }

    /**
     * Update an items name
     *
     * @param itemOldName Current name of item to update
     * @param itemNewName The new name
     */
    public void updateItem(String itemOldName, String itemNewName) {
        this.connect();

        String update = "UPDATE item SET name = (?) WHERE name = (?)";

        try (PreparedStatement pstmnt = this.conn.prepareStatement(update)) {
            pstmnt.setString(1, itemNewName);
            pstmnt.setString(2, itemOldName);
            pstmnt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.disconnect();
        }
    }

}
