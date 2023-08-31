package org.example.view;

import org.example.controller.UserInput;

import java.sql.SQLException;

public class MenuView {
    public static void main() throws SQLException {
        boolean loop = true;
        while (loop) {
            System.out.println("""
                    Welcome to Swosh, please make your selection:
                    1. Login
                    2. Create an user
                    3. Delete an user
                    4. Show an user
                    0. Exit
                    """);
            int choice = UserInput.getInt();
            switch (choice) {
                case 1 -> LoginView.login();
                case 2 -> UserView.createUser();
                case 3 -> UserView.deleteUser();
                case 4 -> UserView.showUser();
                case 0 -> loop = false;
                default -> System.out.println("Please choose a valid number.");
            }
        }
    }
}
