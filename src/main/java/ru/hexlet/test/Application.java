package ru.hexlet.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:hexlet-test")) {

            String sqlCreate  = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone BIGINT)";
            try (Statement statement = conn.createStatement()) {
                statement.execute(sqlCreate);
            }

            String sqlInsert = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(sqlInsert)) {
                preparedStatement.setString(1, "Tommy");
                preparedStatement.setString(2, "88005553535");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Johny");
                preparedStatement.setString(2, "8080808080");
                preparedStatement.executeUpdate();
            }

            String sqlDelete = "DELETE FROM users WHERE username = ?";
            try (var preparedStatement = conn.prepareStatement(sqlDelete)) {
                preparedStatement.setString(1, "Tommy");
                preparedStatement.executeUpdate();
            }

            String sqlSelect = "SELECT * FROM users";
            try (Statement statement3 = conn.createStatement()) {
                ResultSet resultSet = statement3.executeQuery(sqlSelect);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println(resultSet.getString("phone"));
                }
            }
        }
    }
}
