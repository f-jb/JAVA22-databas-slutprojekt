package org.example;

import org.example.database.Database;
import org.example.view.MenuView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Create a first connection to make sure the tables are initialized.
        Connection dbConnection = Database.getConnection();
        MenuView.main();
    }
}