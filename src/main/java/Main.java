import controller.EventController;
import dao.SqliteJDBCConnector;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;

import  static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--create-tables")) {
            try {
                SqliteJDBCConnector.createTables();
            } catch (SQLException e) {
                System.out.println("Cannot create tables in DB");
                System.out.println(e);
            }
        }

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        EventController eventController = new EventController();

        get("/", (Request req, Response res) -> {
            res.redirect("events");
            return "";
        });
        get("/events", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( eventController.renderProducts(req, res) );
        });
        get("events/filter", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( eventController.renderProductsByCategory(req, res) );
        });
        get("events/search", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( eventController.renderProductsBySearchBox(req, res) );
        });
        get("events/add", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( eventController.renderAddEvent(req, res) );
        });
        post("events/add", (Request req, Response res) -> {
            eventController.addNewEvent(req, res);
            res.redirect("/");
            return "";
        });
        get("events/edit", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( eventController.renderEditEvent(req, res) );
        });
        post("events/edit", (Request req, Response res) -> {
            eventController.editEvent(req, res);
            res.redirect("/");
            return "";
        });
        get("events/remove", (Request req, Response res) -> {
            eventController.removeEvent(req, res);
            res.redirect("/");
            return "";
        });
    }


}