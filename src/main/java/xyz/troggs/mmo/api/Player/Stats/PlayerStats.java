package xyz.troggs.mmo.api.Player.Stats;

import org.bson.Document;
import org.bukkit.Bukkit;
import xyz.troggs.mmo.api.Player.PlayerData;
import xyz.troggs.mmo.api.Player.Stats.Classes.Cleric;
import xyz.troggs.mmo.api.Player.Stats.Classes.Mage;
import xyz.troggs.mmo.api.Player.Stats.Classes.Ranger;
import xyz.troggs.mmo.api.Player.Stats.Classes.Warrior;

public class PlayerStats {

    public enum PlayerClass{
        NULL,
        MELEE,
        MAGE,
        HEALER,
        ARCHER
    }

    private Document document;

    private int level;
    private int levelExp;
    private PlayerClass playerClass;

    private Cleric cleric;
    private Mage mage;
    private Ranger ranger;
    private Warrior warrior;

    public PlayerStats(Document doc){
        document = (Document) doc.get("stats");

        level = document.getInteger("level");
        levelExp = document.getInteger("levelExp");
        playerClass = PlayerClass.valueOf(document.getString("active-class"));

        cleric = new Cleric((Document) document.get("classes"));
        mage = new Mage((Document) document.get("classes"));
        ranger = new Ranger((Document) document.get("classes"));
        warrior = new Warrior((Document) document.get("classes"));
    }

    public Document getDocument() {
        document.put("level", level);
        document.put("levelExp", levelExp);
        document.put("active-class", playerClass.toString());

        document.put("classes.cleric", cleric.getDocument());
        document.put("classes.mage", mage.getDocument());
        document.put("classes.ranger", ranger.getDocument());
        document.put("classes.warrior", warrior.getDocument());
        return document;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelExp() {
        return levelExp;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public Cleric getCleric() {
        return cleric;
    }

    public Mage getMage() {
        return mage;
    }

    public Ranger getRanger() {
        return ranger;
    }

    public Warrior getWarrior() {
        return warrior;
    }
}
