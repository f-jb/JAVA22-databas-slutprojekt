package org.example.controller;

import org.example.database.Database;
import org.example.model.Authentication;
import org.example.model.Users;
import org.example.view.AccountsView;
import org.example.view.MessageView;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {
    public static void login(String personalNumber, String password) throws SQLException {
        Connection connection = Database.getConnection();
        if (Users.doesUserExist(personalNumber, connection)) {
            byte[][] passwordAndHash = Users.getPasswordAndHash(personalNumber, connection);
            byte[] storedPassword = passwordAndHash[0];
            byte[] storedHash = passwordAndHash[1];
            byte[] hashedSuppliedPassword = Authentication.hashPw(Authentication.stringToByteArray(password), storedHash);
            if (Authentication.comparePassword(storedPassword, hashedSuppliedPassword)) {
                Users.updateLastLogin(personalNumber, connection);
                AccountsView.showAccounts(personalNumber);
            } else {
                MessageView.error("Invalid personal number or password");
            }
        } else {
            MessageView.error("Invalid personal number or password");
        }
    }
}
