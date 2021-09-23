package co.edu.escuelaing.sparkdockeraws.RoundRobin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.*;
import java.util.Enumeration;

public class HttpClient{
    private String[] ports = {":35001",":35002",":35003"};
    private int nServer = 0;
    private String url="http://192.168.0.7";

    public HttpClient() throws UnknownHostException {
    }
    /**
     */
    public String getMessage() throws UnirestException {
        System.out.println(url+ports[nServer]+"/connect");
        HttpResponse<String> apiResponse = Unirest.get(url+ports[nServer]+"/connect").asString();
        return apiResponse.getBody();
    }
    /**
     */
    public String postMessage(String message) throws UnirestException {
        System.out.println(url+ports[nServer]+"/connect");
        HttpResponse<String> apiResponse = Unirest.post(url+ports[nServer]+"/connect")
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