package co.edu.escuelaing.sparkdockeraws.RoundRobin;

import java.net.UnknownHostException;

import static spark.Spark.*;
/**
 */
public class RoundRobinApp {
    /**
     */
    public static void main( String[] args ) throws UnknownHostException {
        port(getPort());
        staticFileLocation("/public");
        HttpClient client = new HttpClient();
        get("/", (req, res) -> {
            res.redirect( "index.html");
            return null;
        });
        get("/app/getmessages", (req, res) -> {
            res.status(200);
            res.type("application/json");
            client.updateURL(req.url());
            String resp =  client.getMessage();
            client.changeServer();
            return resp;
        });
        post("/app/addmessage", (req, res) -> {
            client.updateURL(req.url());
            client.postMessage(req.body());
            client.changeServer();
            return "";
        });
    }
    /**
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}