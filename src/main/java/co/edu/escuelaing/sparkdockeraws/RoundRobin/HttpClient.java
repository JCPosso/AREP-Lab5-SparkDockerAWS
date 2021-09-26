package co.edu.escuelaing.sparkdockeraws.RoundRobin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.*;

public class HttpClient{
    private String[] ports = {":35001",":35002",":35003"};
    private int nServer = 0;
    private String url="http://192.168.0.7";

    public HttpClient() throws UnknownHostException {
    }
    /**
     */
    public String getMessages() throws UnirestException {
        System.out.println(url+ports[nServer]+"/api/getmessages");
        HttpResponse<String> apiResponse = Unirest.get(url+ports[nServer]+"/api/getmessages").asString();
        return apiResponse.getBody();
    }
    /**
     */
    public String postMessage(String message) throws UnirestException {
        System.out.println(url+ports[nServer]+"/api/addmessage");
        HttpResponse<String> apiResponse = Unirest.post(url+ports[nServer]+"/api/addmessage")
                .body(message)
                .asString();
        return apiResponse.getBody();
    }
    /**
     * Metodo encargado de cambiar el valor de la variable nServer para cambiar entre los tres puertos disponibles del LogService.
     */
    public void changeServer(){
        nServer=(nServer + 1) % ports.length;
    }

}