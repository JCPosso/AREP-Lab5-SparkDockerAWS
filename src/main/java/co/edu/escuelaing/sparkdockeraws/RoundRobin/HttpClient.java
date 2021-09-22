package co.edu.escuelaing.sparkdockeraws.RoundRobin;

import spark.Request;
import spark.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 */
public class HttpClient {

    public String getMessage()  {
        return null;
    };

    public String postMessage(Request req, Response res)  {
        System.out.println("llegó"+req.body());
        String str = null;
        try {
            URL obj = new URL("http://localhost:4568/api/addmessage");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);
            String jsonInputString = req.body();
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                str =  response.toString();
            }
        } catch (IOException e) {
            res.status(404); //no se encuentra
        }
        System.out.println("str "+str);
        return  str;
    }
}