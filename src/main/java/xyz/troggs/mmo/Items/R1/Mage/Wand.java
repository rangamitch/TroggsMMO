package xyz.troggs.mmo.Items.R1.Mage;

import xyz.troggs.mmo.Items.Item;

public class Wand implements Item {

    private final Item item;

    private Wand(Item item){
        this.item = item;
    }

    public static Wand fromItem(Item item){
        return new Wand(item);
    }

    @Override
    public String getId() {
        return item.getId();
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public ItemRarity getDefaultRarity() {
        return item.getDefaultRarity();
    }

    @Override
    public int getLevelRequirement() {
        return item.getLevelRequirement();
    }

    @Override
    public String getClassRequirement() {
        return item.getClassRequirement();
    }
}
