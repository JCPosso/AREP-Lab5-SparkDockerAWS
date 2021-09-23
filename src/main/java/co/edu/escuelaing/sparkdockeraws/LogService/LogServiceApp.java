package co.edu.escuelaing.sparkdockeraws.LogService;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static spark.Spark.*;
/**
 */
public class LogServiceApp {
    /**
     */
    public static void main( String[] args ) {
        MongoService service = new MongoService();
        staticFileLocation("/public");
        port(getPort());
        get("hello", (req, res) -> "Hello Docker!!!");
        get("/", (req, res) -> {res.redirect( "index.html");return null;});
        get("/api/getmessages", (req, res) -> {
                    res.status(200);
                    res.type("application/json");
                    return new Gson().toJson(service.getMessages());
                });
        post("/api/addmessage", (req, res) -> {
            JsonObject json = (JsonObject) JsonParser.parseString(req.body());
            String user = json.get("user").getAsString();
            String message = json.get("message").getAsString();
            service.putMessage(user,message);
            return "ok";
        });
    }

    /**
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }
}