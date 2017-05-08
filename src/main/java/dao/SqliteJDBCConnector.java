package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteJDBCConnector {

    public static Connection connection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        } catch (SQLException e) {
            System.out.println("Connection to DB failed");
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void createTables() throws SQLException {
        Connection connection = connection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS events\n" +
            "(\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    event_name VARCHAR NOT NULL,\n" +
            "    description TEXT,\n" +
            "    event_date TEXT,\n" +
            "    category TEXT\n" +
            ")");
    }

}
