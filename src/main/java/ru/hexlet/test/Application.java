package ru.hexlet.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:hexlet-test")) {

            String sqlCreate  = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(20))";
            try (Statement statement = conn.createStatement()) {
                statement.execute(sqlCreate);
            }

            User user1 = new User("Tommy", "+4873573547");
            User user2 = new User("John", "+7386738654");

            var dao = new UserDAO(conn);
            dao.save(user1);
            dao.save(user2);
            dao.delete(user1);
            user2.setName("Bob");
            dao.save(user2);

            for (var user : dao.findAll()) {
                System.out.println(user.getName() + ", " + user.getPhone());
            }
        }
    }
}
