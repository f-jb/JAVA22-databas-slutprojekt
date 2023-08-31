package org.example.model;

import org.example.view.MessageView;

import java.sql.*;

public class Users {
    public static void createUser(User user, Connection connection) throws SQLException {
        PreparedStatement createUserSQL = connection.prepareStatement("INSERT INTO users SET personalNumber = (?), name = (?), password = (?), hash = (?), email = (?), phone = (?), address = (?);", Statement.RETURN_GENERATED_KEYS);
        createUserSQL.setString(1, user.getPersonalNumber());
        createUserSQL.setString(2, user.getName());
        createUserSQL.setBytes(3, user.getPassword());
        createUserSQL.setBytes(4, user.getHash());
        createUserSQL.setString(5, user.getEmail());
        createUserSQL.setString(6, user.getPhone());
        createUserSQL.setString(7, user.getAddress());
        try {
            createUserSQL.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                MessageView.error("User already exists");
            }
        }
    }

    public static void updateName(String name, String personalNumber, Connection connection) throws SQLException {
        PreparedStatement updateNameSQL = connection.prepareStatement("UPDATE users SET name = (?) WHERE personalNumber = (?);");
        updateNameSQL.setString(1, name);
        updateNameSQL.setString(2, personalNumber);
        updateNameSQL.executeUpdate();
    }

    public static void updateEmail(String email, String personalNumber, Connection connection) throws SQLException {
        PreparedStatement updateEmailSQL = connection.prepareStatement("UPDATE users SET email = (?) WHERE personalNumber = (?);");
        updateEmailSQL.setString(1, email);
        updateEmailSQL.setString(2, personalNumber);
        updateEmailSQL.executeUpdate();
    }

    public static void updatePhone(String phone, String personalNumber, Connection connection) throws SQLException {
        PreparedStatement updatePhoneSQL = connection.prepareStatement("UPDATE users SET phone = (?) WHERE personalNumber = (?);");
        updatePhoneSQL.setString(1, phone);
        updatePhoneSQL.setString(2, personalNumber);
        updatePhoneSQL.executeUpdate();
    }

    public static void updateAddress(String address, String personalNumber, Connection connection) throws SQLException {
        PreparedStatement updateAddressSQL = connection.prepareStatement("UPDATE users SET address = (?) WHERE personalNumber= (?);");
        updateAddressSQL.setString(1, address);
        updateAddressSQL.setString(2, personalNumber);
        updateAddressSQL.executeUpdate();
    }

    public static byte[][] getPasswordAndHash(String personalNumber, Connection connection) throws SQLException {
        PreparedStatement getPasswordAndHashSQL = connection.prepareStatement("SELECT password, hash FROM users WHERE personalNumber = (?);");
        getPasswordAndHashSQL.setString(1, personalNumber);
        getPasswordAndHashSQL.executeQuery();
        ResultSet resultSet = getPasswordAndHashSQL.getResultSet();
        byte[][] passwordAndHash = new byte[2][];
        if (resultSet.next()) {
            passwordAndHash[0] = resultSet.getBytes("password");
            passwordAndHash[1] = resultSet.getBytes("hash");
        }
        return passwordAndHash;
    }


    public static void deleteAccountsAndUser(String personalNumber, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement deleteAccounts = connection.prepareStatement("DELETE FROM accounts WHERE owner = (SELECT id FROM users WHERE personalNumber = (?));");
        deleteAccounts.setString(1, personalNumber);
        deleteAccounts.executeUpdate();

        PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM users WHERE personalNumber = (?);");
        deleteUser.setString(1, personalNumber);
        deleteUser.executeUpdate();
        connection.commit();
        connection.setAutoCommit(true);

    }

    public static User getUser(String personalNumber, Connection connection) throws SQLException {
        PreparedStatement getUserSQL = connection.prepareStatement("SELECT * FROM users WHERE personalNumber = (?);");
        getUserSQL.setString(1, personalNumber);
        getUserSQL.executeQuery();
        ResultSet resultSet = getUserSQL.getResultSet();
        if (resultSet.next()) {
            return new User.UserBuilder()
                    .name(resultSet.getString("name"))
                    .personalNumber(resultSet.getString("personalNumber"))
                    .address(resultSet.getString("address"))
                    .latestActivity(resultSet.getString("latestActivity"))
                    .phone(resultSet.getString("phone"))
                    .created(resultSet.getString("created"))
                    .id(resultSet.getInt("id"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .hash(resultSet.getBytes("hash"))
                    .enabled(resultSet.getBoolean("enabled"))
                    .build();
        }
        return new User.UserBuilder().name("ERROR: No user found").build();
    }

    public static void updateLastLogin(String personalNumber, Connection connection) throws SQLException {
        PreparedStatement updateLastLoginSQL = connection.prepareStatement("UPDATE users SET latestActivity = CURRENT_TIMESTAMP WHERE personalNumber = (?);");
        updateLastLoginSQL.setString(1, personalNumber);
        updateLastLoginSQL.execute();
    }

    public static boolean doesUserExist(String personalNumber, Connection connection) throws SQLException {
        PreparedStatement doesUserExistSQL = connection.prepareStatement("SELECT * FROM users WHERE personalNumber = (?);");
        doesUserExistSQL.setString(1, personalNumber);
        doesUserExistSQL.executeQuery();
        return doesUserExistSQL.getResultSet().next();
    }

    public static void updatePassword(byte[] hashedPassword, byte[] hash, String personalNumber, Connection connection) throws SQLException {
        PreparedStatement updatePasswordSQL = connection.prepareStatement("UPDATE users SET password = (?), hash = (?) WHERE personalNumber = (?);");
        updatePasswordSQL.setBytes(1, hashedPassword);
        updatePasswordSQL.setBytes(2, hash);
        updatePasswordSQL.setString(3, personalNumber);
        updatePasswordSQL.executeQuery();
    }
}
