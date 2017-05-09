import controller.EventController;
import dao.SqliteJDBCConnector;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import  static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        if(args.length > 0 && args[0].equals("--create-tables")) {
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

        // Always add generic routes to the end
        get("/", EventController::renderProducts, new ThymeleafTemplateEngine());
        // Equivalent with above
        get("/index", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( EventController.renderProducts(req, res) );
        });
        get("/filter", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( EventController.renderProductsByCategory(req, res) );
        });
        get("/add", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( EventController.renderAddEvent() );
        });
        post("/add", (Request req, Response res) -> {
            res.redirect("index");
            return "";
        });
    }


}