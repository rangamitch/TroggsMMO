package xyz.troggs.mmo.Items;

import org.bukkit.entity.Player;

public interface Item {

    enum ItemRarity{
        COMMON,
        UNCOMMON,
        RARE,
        SUPER_RARE,
        ULTRA_RARE,
        LEGENDARY
    }

    String getId();
    String getName();
    ItemRarity getDefaultRarity();
    int getLevelRequirement();
    String getClassRequirement();

    class Builder {
        private String id;
        private String name;

        private ItemRarity defaultRarity;

        private int levelRequirement;
        private String classRequirement;

        public static Item create(String id, String name, ItemRarity defaultRarity, int levelRequirement, String classRequirement){
            return new ItemImpl()
                    .setId(id)
                    .setName(name)
                    .setDefaultRarity(defaultRarity)
                    .setLevelRequirement(levelRequirement)
                    .setClassRequirement(classRequirement);
        }
    }
}
