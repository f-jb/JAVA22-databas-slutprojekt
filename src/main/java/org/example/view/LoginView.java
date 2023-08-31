package org.example.view;

import org.example.controller.LoginController;
import org.example.controller.UserInput;

import java.sql.SQLException;

public class LoginView {
    static void login() throws SQLException {
        System.out.println("Please enter your personal number");
        String personalNumber = UserInput.getString();
        System.out.println("Please enter your password");
        String password = UserInput.getString();
        LoginController.login(personalNumber, password);

    }

}
