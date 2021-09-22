package co.edu.escuelaing.sparkdockeraws.LogService;

import java.util.Date;

public class Message {
    private String user;
    private String message;
    private Date date;

    public Message(String body) {
    }

    public Message(String user, String message, Date date) {
        this.user = user;
        this.message = message;
        this.date = date;
    }
}