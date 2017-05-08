package dao;

import model.Event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public class EventDao {
    public List<Event> getAll() {
        List<Event> events = new ArrayList<Event>();

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from events");
            while(rs.next()) {
                Event event = new Event(
                        rs.getString("event_name"),
                        rs.getString("description"),
                        rs.getString("event_date"),
                        rs.getString("category")
                );
                event.setId(rs.getInt("id"));
                events.add(event);
            }
        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }

        return events;
    }

    public List<Event> getByCategory(String category) {
        List<Event> events = new ArrayList<Event>();

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from events WHERE category = " + String.format("'%s'", category));
            while(rs.next()) {
                Event event = new Event(
                        rs.getString("event_name"),
                        rs.getString("description"),
                        rs.getString("event_date"),
                        rs.getString("category")
                );
                event.setId(rs.getInt("id"));
                events.add(event);
            }
        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }

        return events;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<String>();

        try {
            Connection connection = SqliteJDBCConnector.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT category FROM events GROUP BY category");
            while(rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch(SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }

        return categories;
    }
}
