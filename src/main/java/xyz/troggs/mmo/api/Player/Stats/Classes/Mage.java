package xyz.troggs.mmo.api.Player.Stats.Classes;

import org.bson.Document;
import org.bukkit.Bukkit;

public class Mage {

    enum MageSubClass {
        LOCKED,
        NECROMANCER,
        ILLUSIONIST
    }

    private int level;
    private int levelExp;

    private MageSubClass subClass;

    private Document document;

    public Mage(Document doc){
        document = (Document) doc.get("mage");
        level = document.getInteger("level");
        levelExp = document.getInteger("levelExp");

        subClass = MageSubClass.valueOf(document.getString("subclass"));
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

    public void setSubClass(MageSubClass subClass) {
        this.subClass = subClass;
    }
}
