package xyz.troggs.mmo.Items;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.Utils;

import java.util.List;

public class Item {

    private String id;

    private ItemRarity defaultRarity;

    private int levelRequirement;
    private String classRequirement;
    private ItemStack itemStack;

    public Item(Main main, String id, ItemRarity defaultRarity, int levelRequirement, String classRequirement, ItemStack itemStack){
        this.id = id;
        this.defaultRarity = defaultRarity;
        this.levelRequirement = levelRequirement;
        this.classRequirement = classRequirement;

        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "level-requirement"), PersistentDataType.INTEGER, levelRequirement);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "class-requirement"), PersistentDataType.STRING, classRequirement);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "item-id"), PersistentDataType.STRING, id);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "reforge"), PersistentDataType.STRING, "NULL");
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "rarity"), PersistentDataType.STRING, defaultRarity.toString());
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "augmented"), PersistentDataType.INTEGER, 0);
        List<String> lore = meta.getLore();
        lore.add(new Utils().chat("&f"));
        lore.add(new Utils().chat(getColor(defaultRarity) + "&l" + defaultRarity.toString()));
        meta.setLore(lore);
        meta.setDisplayName(new Utils().chat(getColor(defaultRarity) + meta.getDisplayName()));
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
    }

    public String getId() {
        return id;
    }

    public ItemRarity getDefaultRarity() {
        return defaultRarity;
    }

    public String getClassRequirement() {
        return classRequirement;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public String getColor(ItemRarity rarity){
        if(rarity == ItemRarity.COMMON){ return "&f"; }
        if(rarity == ItemRarity.UNCOMMON){ return "&a"; }
        if(rarity == ItemRarity.RARE){ return "&9"; }
        if(rarity == ItemRarity.SUPER_RARE){ return "&5"; }
        if(rarity == ItemRarity.LEGENDARY){ return "&6"; }
        if(rarity == ItemRarity.MYTHICAL){ return "&d"; }
        return "&f&lERROR";
    }
}
