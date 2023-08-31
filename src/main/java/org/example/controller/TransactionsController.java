package org.example.controller;

import org.example.database.Database;
import org.example.model.Accounts;
import org.example.model.Transaction;
import org.example.model.Transactions;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionsController {
    public static void depositMoney(String personalNumber, String accountNumber, BigDecimal amountToDeposit) throws SQLException {
        Connection connection = Database.getConnection();
        Transactions.deposit(personalNumber, accountNumber, amountToDeposit, connection);
    }

    public static void withdrawMoney(String personalNumber, String accountNumber, BigDecimal amountToWithdraw) throws SQLException {
        Connection connection = Database.getConnection();
        Transactions.withdraw(personalNumber, accountNumber, amountToWithdraw, connection);
    }

    public static void transferMoney(String fromAccount, String toAccount, BigDecimal amount, String comment) throws SQLException {
        Connection connection = Database.getConnection();
        if (Accounts.doesAccountExist(toAccount, connection) && Accounts.checkBalance(fromAccount, amount, connection)) {
            Transactions.transfer(fromAccount, toAccount, amount, comment, connection);
        }
    }

    public static ArrayList<Transaction> getHistory(String personalNumber, String accountNumber, String startDate, String endDate) throws SQLException {
        Connection connection = Database.getConnection();
        startDate = startDate + "  00:00:00";
        endDate = endDate + "  23:59:59";
        return Transactions.getTransactions(personalNumber, accountNumber, startDate, endDate, connection);
    }
}
