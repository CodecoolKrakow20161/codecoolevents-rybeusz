package controller;

import dao.EventDao;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public class EventController {
    public static ModelAndView renderProducts(Request req, Response res) {
        //Get events from database by Dao
        EventDao eventDao = new EventDao();
        List<Event> events = eventDao.getAll();
        List<String> categories = eventDao.getCategories();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        params.put("categories", categories);

        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderProductsByCategory(Request req, Response res) {
        //Get events from database by Dao
        EventDao eventDao = new EventDao();
        String category = req.queryParams("category");
        List<Event> events = eventDao.getByCategory(category);
        List<String> categories = eventDao.getCategories();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        params.put("categories", categories);

        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderAddEvent(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/add");
    }

}
