package dao;

import model.Event;

import java.util.List;

/**
 * Created by rybeusz on 14.05.17.
 */
public interface EventDao {

    List<Event> getAll();
    List<Event> getBySearchBox(String eventName);
    List<Event> getByCategory(String category);
    List<String> getCategories();
    Event findEvent(Integer id);
    void saveEvent(Event event);
    void removeEvent(Event event);

}

