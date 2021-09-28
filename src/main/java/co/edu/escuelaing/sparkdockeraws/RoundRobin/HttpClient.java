package co.edu.escuelaing.sparkdockeraws.RoundRobin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.*;

public class HttpClient{
    private String url="";
    private String[] ports = {":35001",":35002",":35003"};
    private int rotate = 0;

    public HttpClient() throws UnknownHostException {
    }

    public void updateURL(String url) {
        String p1 = url.split("//")[1];
        String p2 = p1.split(":")[0];
        this.url = "http://"+p2;
        System.out.println("new url :"+url);
    }
    public void changeServer(){
        rotate=(rotate+1)%ports.length;
    }

    /**
     */
    public String getMessage() throws UnirestException {
        System.out.println("solicitud"+url+ports[rotate]+"/api/getmessages");
        HttpResponse<String> apiResponse = Unirest.get(url+ports[rotate]+"/api/getmessages").asString();
        return apiResponse.getBody();
    }
    /**
     */
    public String postMessage(String message) throws UnirestException {
        System.out.println("solicitud"+url+ports[rotate]+"/api/addmessage");
        HttpResponse<String> apiResponse = Unirest.post(url+ports[rotate]+"/api/addmessage").body(message).asString();
        return apiResponse.getBody();
    }
}