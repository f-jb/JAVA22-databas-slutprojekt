package org.example.view;

import org.example.controller.TransactionsController;
import org.example.controller.UserInput;
import org.example.model.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionsView {
    public static void showTransactions(String personalNumber, String accountNumber, String startDate, String endDate) throws SQLException {
        ArrayList<Transaction> transactions = TransactionsController.getHistory(personalNumber, accountNumber, startDate, endDate);
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(i + " " + transactions.get(i).toString());
        }
        UserInput.getEnter();


    }

}
