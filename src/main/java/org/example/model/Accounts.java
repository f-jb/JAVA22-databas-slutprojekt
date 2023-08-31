package org.example.model;

import org.example.view.MessageView;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class Accounts {

    public static void createAccount(String personalNumber, String accountName, Connection connection) throws SQLException {
        PreparedStatement createAccountSQL = connection.prepareStatement("INSERT INTO accounts SET owner = (SELECT id FROM users WHERE personalNumber = (?)), accountName = (?);", Statement.RETURN_GENERATED_KEYS);
        createAccountSQL.setString(1, personalNumber);
        createAccountSQL.setString(2, accountName);
        try {
            createAccountSQL.executeUpdate();
            ResultSet resultSet = createAccountSQL.getGeneratedKeys();
            if (resultSet.next()) {
                MessageView.information("Your account number is: " + resultSet.getInt(1));
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                MessageView.error("Account already exists");
            }
        }

    }

    public static ArrayList<Account> getAccounts(String personalNumber, Connection connection) throws SQLException {
        PreparedStatement getAccountsSQL = connection.prepareStatement("SELECT accountName, accountNumber, balance, accounts.created FROM accounts INNER JOIN users ON users.id = accounts.owner WHERE users.personalNumber = (?);");
        getAccountsSQL.setString(1, personalNumber);
        getAccountsSQL.executeQuery();
        ResultSet resultSet = getAccountsSQL.getResultSet();
        ArrayList<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            accounts.add(new Account.AccountBuilder(resultSet.getString("accountName"))
                    .balance(resultSet.getBigDecimal("balance"))
                    .accountNumber(resultSet.getInt("accountNumber"))
                    .created(resultSet.getString("created")).build());
        }
        return accounts;
    }

    public static boolean checkIfEmpty(String personalNumber, String accountNumber, Connection connection) throws SQLException {
        PreparedStatement checkIfEmptySQL = connection.prepareStatement("SELECT balance FROM accounts WHERE owner = (SELECT id FROM users WHERE personalNumber = (?)) AND accounts.accountNumber = (?);");
        checkIfEmptySQL.setString(1, personalNumber);
        checkIfEmptySQL.setString(2, accountNumber);
        checkIfEmptySQL.executeQuery();
        ResultSet resultSet = checkIfEmptySQL.getResultSet();
        if (resultSet.next()) {
            return resultSet.getBigDecimal("balance").compareTo(BigDecimal.ZERO) == 0;
        }
        return false;

    }

    public static void deleteAccount(String personalNumber, String accountNumber, Connection connection) throws SQLException {
        PreparedStatement deleteAccountSQL = connection.prepareStatement("DELETE FROM accounts WHERE accountNumber = (?) AND owner = (SELECT id FROM users WHERE personalNumber = (?));");
        deleteAccountSQL.setString(1, accountNumber);
        deleteAccountSQL.setString(2, personalNumber);
        deleteAccountSQL.executeUpdate();
    }

    public static boolean checkBalance(String accountNumber, BigDecimal amountToCheck, Connection connection) throws SQLException {
        PreparedStatement checkBalanceSQL = connection.prepareStatement("SELECT balance FROM accounts WHERE accountNumber = (?);");
        checkBalanceSQL.setString(1, accountNumber);
        checkBalanceSQL.executeQuery();
        ResultSet resultSet = checkBalanceSQL.getResultSet();
        if (resultSet.next()) {
            return resultSet.getBigDecimal("balance").compareTo(amountToCheck) >= 0;
        }

        return false;
    }

    public static boolean doesAccountExist(String account, Connection connection) throws SQLException {
        PreparedStatement doesAccountExistSQL = connection.prepareStatement("SELECT * FROM accounts WHERE accountNumber = (?);");
        doesAccountExistSQL.setString(1, account);
        doesAccountExistSQL.executeQuery();
        return doesAccountExistSQL.getResultSet().next();
    }

}

