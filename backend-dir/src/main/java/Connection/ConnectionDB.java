/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VuNT13
 */
public class ConnectionDB {

    // just connect Java -> DB MySQL.
    public static Connection getConnection() {
        try {
            // Initialize all the information regarding
            // Database Connection
            String dbDriver = "com.mysql.jdbc.Driver";
            String dbURL = "jdbc:mysql://localhost:3306/";
            // Database name to access
            String dbName = "demo5";
            String dbUsername = "root";
            String dbPassword = "123456";            
            Class.forName(dbDriver);
            Connection connection = DriverManager.getConnection(dbURL + dbName,
                    dbUsername,
                    dbPassword);
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
