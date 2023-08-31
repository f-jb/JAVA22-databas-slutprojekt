package org.example.database;

import org.mariadb.jdbc.MariaDbDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {


    private static Database db;

    MariaDbDataSource dataSource;

    public static Connection getConnection() throws SQLException {
        if (db == null) {
            db = new Database();
            db.initializeDataSource();
        }
        return db.createConnection();

    }

    private void initializeDataSource() {
        // Reads database properties from src/main/resources/db.properties
        Properties props = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/main/resources/db.properties");
            props.load(fis);
            dataSource = new MariaDbDataSource();
            dataSource.setUrl(props.getProperty("MARIADB_URL"));
            dataSource.setUser(props.getProperty("MARIADB_USERNAME"));
            dataSource.setPassword(props.getProperty("MARIADB_PASSWORD"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        checkTables(connection);
        return connection;

    }

    private void checkTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        // Create users table if not found
        String users = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, latestActivity TIMESTAMP, personalNumber VARCHAR(12) UNIQUE, password VARBINARY(200), hash VARBINARY(100), name VARCHAR(50), email VARCHAR(50), enabled BOOLEAN DEFAULT TRUE, phone VARCHAR(20), address VARCHAR(100));";
        statement.execute(users);

        // Create transactions table if not found
        String transactions = "CREATE TABLE IF NOT EXISTS transactions (id INT PRIMARY KEY AUTO_INCREMENT, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, fromUser VARCHAR(50), fromAccount INT, toUser VARCHAR(50), toAccount INT, amount DECIMAL(18,2) SIGNED, comment VARCHAR(100));";
        statement.execute(transactions);

        // create accounts table if not found
        String accounts = "CREATE TABLE IF NOT EXISTS accounts (owner INT ,accountNumber INT(9) UNIQUE AUTO_INCREMENT PRIMARY KEY, accountName VARCHAR(50), created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, balance DECIMAL(18,2) SIGNED DEFAULT 0 NOT NULL) AUTO_INCREMENT = 500000000;";
        statement.execute(accounts);
        statement.close();
    }

}