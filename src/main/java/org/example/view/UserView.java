package org.example.view;

import org.example.controller.UserController;
import org.example.controller.UserInput;
import org.example.model.User;

import java.sql.SQLException;

public class UserView {
    static void createUser() throws SQLException {
        String personalNumber = null;
        String password = null;
        String name = null;
        String phone = null;
        String email = null;
        String address = null;
        boolean loop = true;
        while (loop) {
            System.out.println("1. Personal Number (yyyymmddnnnn): " + personalNumber);
            System.out.println("2. Password: " + password);
            System.out.println("3. Name: " + name);
            System.out.println("4. Phone: " + phone);
            System.out.println("5. Email: " + email);
            System.out.println("6. Address: " + address);
            System.out.println("9. Commit");
            System.out.println("0. Quit");

            int choice = UserInput.getInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Please enter your personal number");
                    personalNumber = UserInput.getString(12);
                }
                case 2 -> {
                    System.out.println("Please enter your password");
                    password = UserInput.getString();
                }
                case 3 -> {
                    System.out.println("Please enter your name");
                    name = UserInput.getString();
                }
                case 4 -> {
                    System.out.println("Please enter your phone number");
                    phone = UserInput.getString();
                }
                case 5 -> {
                    System.out.println("Please enter your email");
                    email = UserInput.getString();
                }
                case 6 -> {
                    System.out.println("Please enter your address");
                    address = UserInput.getString();
                }
                case 9 -> {
                    loop = false;
                    System.out.println("Creating user");
                    UserController.create(personalNumber, password, name, phone, email, address);
                }
                case 0 -> {
                    loop = false;
                    System.out.println("Exiting");
                }
                default -> System.out.println("Please choose a valid number.");
            }
        }

    }

    public static void deleteUser() throws SQLException {
        System.out.println("""
                WARNING! YOU ARE ABOUT TO DELETE A USER. ALL OF THE USERS FUNDS WILL BE APPROPRIATED TO THE BANK.
                Please enter the personal number of the user you want to delete.
                """);
        String personalNumber = UserInput.getString(12);
        System.out.println("ARE YOU SURE?");
        if (UserInput.getYesOrNo()) {
            UserController.delete(personalNumber);
        }

    }

    public static void showUser() throws SQLException {
        System.out.println("Please enter the personal number of the user you want more information about");
        String personalNumber = UserInput.getString(12);
        User user = UserController.get(personalNumber);
        if (user != null) {
            System.out.println(user);
        }
    }

    public static void modifyUser(String personalNumber) throws SQLException {
        boolean loop = true;
        while (loop) {
            User user = UserController.get(personalNumber);
            System.out.println("Name: " + user.getName());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Address: " + user.getAddress());
            System.out.println("""
                    Modify
                    1. Name
                    2. Phone
                    3. Email
                    4. Address
                    5. Password
                    0. Quit
                    """);
            int choice = UserInput.getInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Please write your name");
                    String name = UserInput.getString();
                    UserController.modifyName(name, personalNumber);
                }
                case 2 -> {
                    System.out.println("Please write your phone");
                    String phone = UserInput.getString();
                    UserController.modifyPhone(phone, personalNumber);
                }
                case 3 -> {
                    System.out.println("Please write your email");
                    String email = UserInput.getString();
                    UserController.modifyEmail(email, personalNumber);
                }
                case 4 -> {
                    System.out.println("Please write your address");
                    String address = UserInput.getString();
                    UserController.modifyAddress(address, personalNumber);
                }
                case 5 -> {
                    System.out.println("Please write the new password");
                    String password = UserInput.getString();
                    UserController.modifyPassword(password, personalNumber);
                }
                case 0 -> loop = false;
                default -> System.out.println("Please choose a valid number.");
            }


        }


    }
}
