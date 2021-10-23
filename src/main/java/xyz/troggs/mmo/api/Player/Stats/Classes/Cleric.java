package xyz.troggs.mmo.api.Player.Stats.Classes;

import org.bson.Document;
import org.bukkit.Bukkit;

public class Cleric {

    public enum ClericSubClass {
        LOCKED,
        MENDER,
        SHAMAN
    }

    private int level;
    private int levelExp;

    private ClericSubClass subClass;

    private Document document;

    public Cleric(Document doc){
        document = (Document) doc.get("cleric");
        level = document.getInteger("level");
        levelExp = document.getInteger("levelExp");

        subClass = ClericSubClass.valueOf(document.getString("subclass"));
    }

    public int getLevel() {
        return level;
    }

    public int getLevelExp() {
        return levelExp;
    }

    public ClericSubClass getSubClass() {
        return subClass;
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

    public void setSubClass(ClericSubClass subClass) {
        this.subClass = subClass;
    }
}
