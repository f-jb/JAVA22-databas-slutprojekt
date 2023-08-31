package org.example.view;

import org.example.controller.AccountsController;
import org.example.controller.TransactionsController;
import org.example.controller.UserInput;
import org.example.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountsView {
    public static void showAccounts(String personalNumber) throws SQLException {
        boolean loop = true;
        while (loop) {
            ArrayList<Account> accounts = AccountsController.getAccounts(personalNumber);
            System.out.println("Account number\tAccount Name\tBalance");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println(i + " " + accounts.get(i).toString());
            }

            System.out.println("""
                    1. Create a new account
                    2. Delete an account. Make sure the account is empty.
                    3. Deposit money
                    4. Withdraw money
                    5. Transfer money
                    6. Show transactions
                    9. Change account info
                    0. Exit
                    """);
            int choice = UserInput.getInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter account name");
                    String accountName = UserInput.getString();
                    AccountsController.createAccount(personalNumber, accountName);
                }
                case 2 -> {
                    System.out.println("Enter the account number of the account you want to delete");
                    String accountNumber = accounts.get(UserInput.getInt()).getAccountNumber();
                    AccountsController.deleteAccount(personalNumber, accountNumber);
                }
                case 3 -> {
                    System.out.println("To which account: ");
                    String accountNumber = accounts.get(UserInput.getInt(0, accounts.size())).getAccountNumber();
                    System.out.println("How much to deposit?");
                    BigDecimal amountToDeposit = UserInput.getBigDecimal();
                    TransactionsController.depositMoney(personalNumber, accountNumber, amountToDeposit);
                }
                case 4 -> {
                    System.out.println("From which account (accnt number): ");
                    String accountNumber = accounts.get(UserInput.getInt(0, accounts.size())).getAccountNumber();
                    System.out.println("How much to withdraw?");
                    BigDecimal amountToWithdraw = UserInput.getBigDecimal();
                    TransactionsController.withdrawMoney(personalNumber, accountNumber, amountToWithdraw);
                }
                case 5 -> {
                    System.out.println("Choose from what account");
                    String fromAccount = accounts.get(UserInput.getInt(0, accounts.size())).getAccountNumber();
                    System.out.println("Please enter the account you want to transfer to");
                    String toAccount = UserInput.getString();
                    System.out.println("Choose amount");
                    BigDecimal amount = UserInput.getBigDecimal();
                    System.out.println("Enter optional comment");
                    String comment = UserInput.getString();
                    TransactionsController.transferMoney(fromAccount, toAccount, amount, comment);


                }
                case 6 -> {
                    System.out.println("History of which account?");
                    String accountNumber = accounts.get(UserInput.getInt(0, accounts.size())).getAccountNumber();
                    System.out.println("From which date? Enter in the format YYYY-MM-DD");
                    String startDate = UserInput.getString();
                    System.out.println("To which date? Enter in the format YYYY-MM-DD");
                    String endDate = UserInput.getString();

                    TransactionsView.showTransactions(personalNumber, accountNumber, startDate, endDate);
                }
                case 9 -> UserView.modifyUser(personalNumber);

                case 0 -> loop = false;
                default -> System.out.println("Please choose a valid number.");
            }

        }
    }
}
