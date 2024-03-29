package controller;

import dao.EventDao;
import dao.EventDaoSql;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public class EventController {
    private EventDao eventDao;

    public EventController() {
        eventDao = new EventDaoSql();
    }

    public ModelAndView renderProducts(Request req, Response res) {
        //Get events from database by Dao
        List<Event> events = eventDao.getAll();
        List<String> categories = eventDao.getCategories();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        params.put("categories", categories);

        return new ModelAndView(params, "product/index");
    }

    public ModelAndView renderProductsByCategory(Request req, Response res) {
        String category = req.queryParams("category");
        List<Event> events = eventDao.getByCategory(category);
        List<String> categories = eventDao.getCategories();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        params.put("categories", categories);

        return new ModelAndView(params, "product/index");
    }

    public ModelAndView renderProductsBySearchBox(Request req, Response res) {
        String searchBoxValue = req.queryParams("event_name");
        List<Event> events = eventDao.getBySearchBox(searchBoxValue);
        List<String> categories = eventDao.getCategories();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        params.put("categories", categories);

        return new ModelAndView(params, "product/index");
    }

    public ModelAndView renderAddEvent(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/add");
    }

    public void addNewEvent(Request req, Response res) {
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String time = req.queryParams("time");
        String category = req.queryParams("category");
        Event event = new Event(name, description, time, category);
        eventDao.saveEvent(event);
    }

    public ModelAndView renderEditEvent(Request req, Response res) {
        Integer id = Integer.valueOf(req.queryParams("id"));
        Event event = eventDao.findEvent(id);
        Map params = new HashMap<>();
        params.put("event", event);
        return new ModelAndView(params, "product/edit");
    }

    public void editEvent(Request req, Response res) {
        Integer id = Integer.valueOf(req.queryParams("id"));
        Event event = eventDao.findEvent(id);
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String time = req.queryParams("time");
        String category = req.queryParams("category");
        event.setName(name);
        event.setDescription(description);
        event.setDate(time);
        event.setCategory(category);
        eventDao.saveEvent(event);
    }

    public void removeEvent(Request req, Response res) {
        Integer id = Integer.valueOf(req.queryParams("id"));
        Event event = eventDao.findEvent(id);
        eventDao.removeEvent(event);
    }

}
