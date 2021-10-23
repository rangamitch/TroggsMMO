package xyz.troggs.mmo.api.Player.Stats.Classes;

import org.bson.Document;
import org.bukkit.Bukkit;

public class Ranger {

    enum RangerSubClass {
        LOCKED,
        ARCHER,
        MARKSMAN
    }

    private int level;
    private int levelExp;

    private RangerSubClass subClass;

    private Document document;

    public Ranger(Document doc){
        document = (Document) doc.get("ranger");
        level = document.getInteger("level");
        levelExp = document.getInteger("levelExp");

        subClass = RangerSubClass.valueOf(document.getString("subclass"));
    }

    public int getLevel() {
        return level;
    }

    public int getLevelExp() {
        return levelExp;
    }

    public Document getDocument() {
        document.put("level", level);
        document.put("levelExp", levelExp);
        document.put("subclass", subClass.toString());
        return document;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addToLevel(int level) {
        this.level += level;
    }

    public void setLevelExp(int levelExp) {
        this.levelExp = levelExp;
    }

    public void addToLevelExp(int levelExp) {
        this.levelExp += levelExp;
    }

    public void setSubClass(RangerSubClass subClass) {
        this.subClass = subClass;
    }
}
