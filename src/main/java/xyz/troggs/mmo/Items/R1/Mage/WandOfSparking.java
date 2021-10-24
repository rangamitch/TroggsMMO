package xyz.troggs.mmo.Items.R1.Mage;

import xyz.troggs.mmo.Items.Item;

public class WandOfSparking implements Item {

    public WandOfSparking(){

    }

    private String id;
    private String name;

    private ItemRarity defaultRarity;

    private int levelRequirement;
    private String classRequirement;

    @Override
    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public ItemRarity getDefaultRarity() {
        return defaultRarity;
    }


    public int getLevelRequirement() {
        return levelRequirement;
    }

    public String getClassRequirement() {
        return classRequirement;
    }

}
