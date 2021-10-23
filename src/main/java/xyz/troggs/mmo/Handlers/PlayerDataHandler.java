package xyz.troggs.mmo.Handlers;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.api.Player.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataHandler implements Listener {
    private Main main;

    public Map<String, PlayerData> playerMap = new HashMap<>();

    public PlayerDataHandler(Main main){
        this.main = main;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    public void loadPlayers(){
        for(Player player : Bukkit.getOnlinePlayers()){
            loadPlayer(player);
        }
    }

    public void savePlayers(){
        MongoClient client = main.mongoHandler.mongoClient;
        MongoDatabase database = client.getDatabase("api");
        MongoCollection<Document> collection = database.getCollection("playerData");

        for(String uuid : playerMap.keySet()){
            collection.replaceOne(Filters.eq("uuid", uuid), playerMap.get(uuid).getDocument());
        }
    }

    public void loadPlayer(Player player){
        MongoClient mongoClient = main.mongoHandler.mongoClient;
        String uuid = (player.getUniqueId().toString());
        String _id = (uuid).replace("-", "");
        String displayName = player.getName();
        MongoDatabase database = mongoClient.getDatabase("api");
        MongoCollection<Document> collection = database.getCollection("playerData");
        Document playerDoc;
        if(collection.countDocuments(Filters.eq("uuid", uuid)) <= 0){
            playerDoc = new Document("_id", _id);
            playerDoc.append("uuid", uuid)
                    .append("displayName", displayName)
                    .append("firstJoined", System.currentTimeMillis())
                    .append("lastJoined", System.currentTimeMillis())
                    .append("stats", new Document("level", 1)
                            .append("levelExp", 0)
                            .append("active-class", "NULL")
                            .append("classes", new Document("cleric", new Document("level", 1)
                                    .append("levelExp", 0)
                                    .append("subclass", "LOCKED"))
                                    .append("mage", new Document("level", 1)
                                            .append("levelExp", 0)
                                            .append("subclass", "LOCKED"))
                                    .append("ranger", new Document("level", 1)
                                            .append("levelExp", 0)
                                            .append("subclass", "LOCKED"))
                                    .append("warrior", new Document("level", 1)
                                            .append("levelExp", 0)
                                            .append("subclass", "LOCKED"))));
            collection.insertOne(playerDoc);
            return;
        }else {
            collection.updateOne(Filters.eq("uuid", uuid), Updates.set("lastJoined", System.currentTimeMillis()));
            playerDoc = collection.find(Filters.eq("uuid", uuid)).first();
        }
        main.playerDataHandler.playerMap.put(uuid, new PlayerData(playerDoc));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        loadPlayer(e.getPlayer());
    }
}

