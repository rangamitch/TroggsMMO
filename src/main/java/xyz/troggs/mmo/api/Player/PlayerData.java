package xyz.troggs.mmo.api.Player;

import org.bson.Document;
import org.bukkit.Bukkit;
import xyz.troggs.mmo.api.Player.Stats.Classes.Cleric;
import xyz.troggs.mmo.api.Player.Stats.Classes.Mage;
import xyz.troggs.mmo.api.Player.Stats.Classes.Ranger;
import xyz.troggs.mmo.api.Player.Stats.Classes.Warrior;
import xyz.troggs.mmo.api.Player.Stats.PlayerStats;

public class PlayerData {

    private String id;
    private String uuid;
    private String displayName;

    private Long firstJoined;
    private Long lastJoined;

    private Document document;

    private PlayerStats stats;

    public PlayerData(Document doc){
        id = doc.getString("_id");
        uuid = doc.getString("uuid");
        displayName = doc.getString("displayName");

        firstJoined = doc.getLong("firstJoined");
        lastJoined = doc.getLong("lastJoined");

        document = doc;

        stats = new PlayerStats(document);
    }

    public String getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Long getFirstJoined() {
        return firstJoined;
    }

    public Long getLastJoined() {
        return lastJoined;
    }

    public Document getDocument() {
        document.put("stats", stats.getDocument());
        return document;
    }

    public PlayerStats getStats() {
        return stats;
    }
}
