package xyz.troggs.mmo.Handlers.DataHandlers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import xyz.troggs.mmo.Main;


public class MongoHandler {
    private Main main;

    public MongoHandler(Main main){
        this.main = main;
    }

    public MongoClient mongoClient;

    public void connectClient() throws Exception {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://mongo:9pnN6y0oHTBmc8JIgB2d@containers-us-west-8.railway.app:6392"));
    }
}
