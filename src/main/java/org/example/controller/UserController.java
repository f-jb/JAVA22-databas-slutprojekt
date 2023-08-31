package org.example.controller;

import org.example.database.Database;
import org.example.model.Authentication;
import org.example.model.User;
import org.example.model.Users;
import org.example.view.MessageView;

import java.sql.Connection;
import java.sql.SQLException;

public class UserController {
    public static void create(String personalNumber, String password, String name, String phone, String email, String address) throws SQLException {
        Connection connection = Database.getConnection();
        if (Users.doesUserExist(personalNumber, connection)) {
            MessageView.error("User already exists");
        }
        byte[] hash = Authentication.generateSalt();
        byte[] hashedPassword = Authentication.hashPw(Authentication.stringToByteArray(password), hash);
        User user = new User.UserBuilder()
                .name(name)
                .phone(phone)
                .email(email)
                .address(address)
                .hash(hash)
                .password(hashedPassword)
                .personalNumber(personalNumber)
                .build();
        Users.createUser(user, connection);
    }

    public static void delete(String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        if (Users.doesUserExist(personalNumber, connection)) {
            Users.deleteAccountsAndUser(personalNumber, connection);
        } else {
            MessageView.error("User not found");
        }
    }

    public static User get(String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        if (Users.doesUserExist(personalNumber, connection)) {
            return Users.getUser(personalNumber, connection);
        } else {
            MessageView.error("User not found");
            return null;
        }
    }

    public static void modifyName(String name, String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        Users.updateName(name, personalNumber, connection);
    }

    public static void modifyPhone(String phone, String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        Users.updatePhone(phone, personalNumber, connection);
    }

    public static void modifyEmail(String email, String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        Users.updateEmail(email, personalNumber, connection);
    }

    public static void modifyAddress(String address, String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        Users.updateAddress(address, personalNumber, connection);
    }

    public static void modifyPassword(String password, String personalNumber) throws SQLException {
        Connection connection = Database.getConnection();
        byte[] hash = Authentication.generateSalt();
        byte[] hashedPassword = Authentication.hashPw(Authentication.stringToByteArray(password), hash);
        Users.updatePassword(hashedPassword, hash, personalNumber, connection);
    }
}
