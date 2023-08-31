package org.example.model;

import org.example.view.MessageView;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Transactions {
    public static void deposit(String personalNumber, String accountNumber, BigDecimal amountToDeposit, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement depositFunds = connection.prepareStatement("UPDATE accounts SET balance = balance + (?) WHERE accountNumber = (?) AND owner = (SELECT id FROM users WHERE personalNumber = (?));");
        depositFunds.setBigDecimal(1, amountToDeposit);
        depositFunds.setString(2, accountNumber);
        depositFunds.setString(3, personalNumber);
        depositFunds.executeUpdate();

        PreparedStatement registerTransaction = connection.prepareStatement("INSERT INTO transactions SET fromUser = (SELECT id FROM users WHERE personalNumber = (?)), toUser = (SELECT id FROM users WHERE personalNumber = (?)), toAccount = (?), amount = (?), comment = 'Cash Deposit' ;");
        registerTransaction.setString(1, personalNumber);
        registerTransaction.setString(2, personalNumber);
        registerTransaction.setString(3, accountNumber);
        registerTransaction.setBigDecimal(4, amountToDeposit);
        registerTransaction.executeUpdate();

        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void withdraw(String personalNumber, String accountNumber, BigDecimal amountToWithdraw, Connection connection) throws SQLException {
        if (Accounts.checkBalance(accountNumber, amountToWithdraw, connection)) {
            connection.setAutoCommit(false);
            PreparedStatement withdrawFunds = connection.prepareStatement("UPDATE accounts SET balance = balance - (?) WHERE accountNumber = (?) AND owner = (SELECT id FROM users WHERE personalNumber = (?)); ");
            withdrawFunds.setBigDecimal(1, amountToWithdraw);
            withdrawFunds.setString(2, accountNumber);
            withdrawFunds.setString(3, personalNumber);
            withdrawFunds.executeUpdate();

            PreparedStatement registerTransaction = connection.prepareStatement("INSERT INTO transactions SET fromUser = (SELECT id FROM users WHERE personalNumber = (?)), toUser = (SELECT id FROM users WHERE personalNumber = (?)), toAccount = (?), amount = (?), comment = 'Cash Withdrawal' ;");
            registerTransaction.setString(1, personalNumber);
            registerTransaction.setString(2, personalNumber);
            registerTransaction.setString(3, accountNumber);
            registerTransaction.setBigDecimal(4, amountToWithdraw.negate());
            registerTransaction.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        }

    }

    public static ArrayList<Transaction> getTransactions(String personalNumber, String accountNumber, String startDate, String endDate, Connection connection) throws SQLException {
        PreparedStatement getTransactionsSQL = connection.prepareStatement("SELECT * FROM transactions WHERE (fromUser = (SELECT id FROM users WHERE personalNumber = (?)) OR toUser = (SELECT id FROM users WHERE personalNumber = (?))) AND (toAccount = (?) OR fromAccount = (?)) AND timestamp BETWEEN (?) AND (?) ORDER BY timestamp;");
        getTransactionsSQL.setString(1, personalNumber);
        getTransactionsSQL.setString(2, personalNumber);
        getTransactionsSQL.setString(3, accountNumber);
        getTransactionsSQL.setString(4, accountNumber);
        getTransactionsSQL.setString(5, startDate);
        getTransactionsSQL.setString(6, endDate);
        getTransactionsSQL.executeQuery();

        ResultSet resultSet = getTransactionsSQL.getResultSet();
        ArrayList<Transaction> accounts = new ArrayList<>();
        while (resultSet.next()) {
            accounts.add(new Transaction.TransactionBuilder()
                    .id(resultSet.getInt("id"))
                    .timestamp(resultSet.getString("timestamp"))
                    .fromUser(resultSet.getString("fromUser"))
                    .fromAccount(resultSet.getString("fromAccount"))
                    .toUser(resultSet.getString("toUser"))
                    .toAccount(resultSet.getString("toAccount"))
                    .amount(resultSet.getFloat("amount"))
                    .comment(resultSet.getString("comment"))
                    .build());
        }
        return accounts;
    }

    public static void transfer(String fromAccount, String toAccount, BigDecimal amount, String comment, Connection connection) throws SQLException {
        if (Accounts.checkBalance(fromAccount, amount, connection)) {
            connection.setAutoCommit(false);
            PreparedStatement withdrawFunds = connection.prepareStatement("UPDATE accounts SET balance = balance - (?) WHERE accountNumber = (?); ");
            withdrawFunds.setBigDecimal(1, amount);
            withdrawFunds.setString(2, fromAccount);
            withdrawFunds.executeUpdate();

            PreparedStatement depositFunds = connection.prepareStatement("UPDATE accounts SET balance = balance + (?) WHERE accountNumber = (?);");
            depositFunds.setBigDecimal(1, amount);
            depositFunds.setString(2, toAccount);
            depositFunds.executeUpdate();

            PreparedStatement registerTransaction = connection.prepareStatement("INSERT INTO transactions SET fromUser = (SELECT owner FROM accounts WHERE accountNumber = (?)), toUser = (SELECT owner FROM accounts WHERE accountNumber = (?)),fromAccount = (?), toAccount = (?), amount = (?), comment = (?) ;");
            registerTransaction.setString(1, fromAccount);
            registerTransaction.setString(2, toAccount);
            registerTransaction.setString(3, fromAccount);
            registerTransaction.setString(4, toAccount);
            registerTransaction.setBigDecimal(5, amount);
            registerTransaction.setString(6, comment);
            registerTransaction.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } else {
            MessageView.error("Insufficient funds");
        }
    }
}
