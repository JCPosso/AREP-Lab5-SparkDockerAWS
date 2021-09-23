package co.edu.escuelaing.sparkdockeraws.LogService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoService {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private FindIterable findIterable;
    private MongoCollection<Document> collection;
    MongoClientURI uri;;

    public MongoService() {
        uri = new MongoClientURI("mongodb+srv://camilo:camilo@clusterroundrobin.tgzxt.mongodb.net/Messages?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase("RoundRobin");
    }

    public ArrayList<Message> getMessages() {
        ArrayList<Message> messages = new ArrayList<Message>();
        ArrayList<Document> documents = new ArrayList<Document>();
        MongoCollection<Document> collection= db.getCollection("messages");
        findIterable = collection.find();
        findIterable.into(documents);
        Integer end = documents.size()-10;
        if (documents.size()<=10){end= 0;}
        for (int i =documents.size()-1;i>=end;i--) {
            if (documents.get(i).get("user") != null && documents.get(i).get("message") != null && documents.get(i).get("date") != null) {
                messages.add(new Message((String) documents.get(i).get("user"),(String) documents.get(i).get("message"), (Date) documents.get(i).get("date")));
            }
        }
        return messages;
    }

    public void putMessage(String user, String message) {
        collection = db.getCollection("messages");
        Date sysdate = new Date();
        Document document = new Document("user", user).append("message", message).append("date", sysdate);
        collection.insertOne(document);
    }
}
