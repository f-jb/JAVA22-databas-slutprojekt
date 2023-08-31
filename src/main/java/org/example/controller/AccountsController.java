package org.example.controller;

import org.example.database.Database;
import org.example.model.Account;
import org.example.model.Accounts;
import org.example.view.MessageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountsController {
    public static ArrayList<Account> getAccounts(String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        return Accounts.getAccounts(personalNumber, connection);

    }

    public static void createAccount(String personalNumber, String accountName) throws SQLException {
        Connection connection = Database.getConnection();
        Accounts.createAccount(personalNumber, accountName, connection);
    }

    public static void deleteAccount(String personalNumber, String accountNumber) throws SQLException {
        Connection connection = Database.getConnection();
        if (Accounts.checkIfEmpty(personalNumber, accountNumber, connection)) {
            Accounts.deleteAccount(personalNumber, accountNumber, connection);
        } else {
            MessageView.error("The account is not empty");
        }

    }

}
