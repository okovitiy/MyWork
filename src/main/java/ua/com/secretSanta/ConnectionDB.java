package main.java.ua.com.secretSanta;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * User: Andrew
 * Date: 09.01.14
 */

public class ConnectionDB {
    private static Connection connection;

    public static Connection getConnection(String login, String password) throws Exception {
        String url = "jdbc:mysql://localhost:3306/secretsantadb";
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver connected.");
            connection = DriverManager.getConnection(url, login, password);
            System.out.println("Connection established.");
        }
        return connection;
    }

    public static void closeConnection() throws Exception {
        connection.close();
        System.out.println("The connection is closed");
    }
}
