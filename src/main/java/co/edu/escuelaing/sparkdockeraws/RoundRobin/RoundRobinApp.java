package co.edu.escuelaing.sparkdockeraws.RoundRobin;

import static spark.Spark.*;
/**
 */
public class RoundRobinApp {
    /**
     */
    public static void main( String[] args ) {
        port(getPort());
        staticFileLocation("/public");
        HttpClient client = new HttpClient();
        get("/", (req, res) -> {
            res.redirect( "index.html");
            return null;
        });
        get("/api/getmessages", (req, res) -> {
            res.status(200);
            res.type("application/json");
            String resp = client.getMessage();
            return resp;
        });
        post("/api/addmessage", (req, res) -> {
            client.postMessage(req,res);
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