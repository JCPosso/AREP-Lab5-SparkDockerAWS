package co.edu.escuelaing.sparkdockeraws.LogService;

import java.util.ArrayList;
import java.util.Date;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoService {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private FindIterable findIterable;
    private MongoCollection<Document> collection;
    private ArrayList<Message> messages = new ArrayList<Message>();

    public MongoService(String host, int port) {
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDatabase("RoundRobin");
    }

    public MongoService() {

    }

    public ArrayList<Message> getMessages() {
        ArrayList<Document> documents = new ArrayList<Document>();
        MongoCollection<Document> collection= db.getCollection("Messages");
        findIterable = collection.find();
        findIterable.into(documents);
        for (Document file : documents) {
            if (file.get("message") != null && file.get("date") != null) {
                messages.add(new Message((String) file.get("user"),(String) file.get("message"), (Date) file.get("date")));
            }
        }
        return messages;
    }

    public void putMessage(String user, String message) {
        collection = db.getCollection("messages");
        Date sysdate = new Date();
        Document document = new Document("user", user).append("content", message).append("date", sysdate);
        collection.insertOne(document);
    }
}
