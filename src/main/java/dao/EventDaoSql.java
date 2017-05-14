package dao;

import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public class EventDaoSql implements EventDao {
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
            connection.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    public List<Event> getBySearchBox(String eventName) {
        List<Event> events = new ArrayList<Event>();
        String sql = "SELECT * FROM events WHERE event_name LIKE (?)";
        try {
            Connection connection = SqliteJDBCConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,'%' + eventName + '%');
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Event event = new Event(
                        rs.getString("event_name"),
                        rs.getString("description"),
                        rs.getString("event_date"),
                        rs.getString("category"));
                event.setId(rs.getInt("id"));
                events.add(event);
            }
            connection.close();
        } catch(SQLException e) {
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
            connection.close();
        } catch(SQLException e) {
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
            connection.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public void saveEvent(Event event) {
        String sql;
        if (event.getId() != null) {
            sql = "UPDATE events SET event_name=?, description=?, event_date=?, category=? WHERE id=?";
        } else {
            sql = "INSERT INTO events(event_name,description,event_date,category) VALUES(?,?,?,?)";
        }
        try {
            Connection connection = SqliteJDBCConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,event.getName() );
            statement.setString(2,event.getDescription() );
            statement.setString(3,event.getDate() );
            statement.setString(4,event.getCategory() );
            if (event.getId() != null) {
                statement.setInt(5,event.getId());
            }
            statement.executeUpdate();
            connection.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Event findEvent(Integer id) {
        Event event = null;
        String sql = "SELECT * FROM events WHERE id = (?)";
        try {
            Connection connection = SqliteJDBCConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            event = new Event(
                    rs.getString("event_name"),
                    rs.getString("description"),
                    rs.getString("event_date"),
                    rs.getString("category"));
            event.setId(rs.getInt("id"));
            connection.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return event;
    }

    public void removeEvent(Event event) {
        String sql = "DELETE FROM events WHERE id = ?";
        try {
            Connection connection = SqliteJDBCConnector.connection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,event.getId());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
