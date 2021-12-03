package xyz.troggs.mmo.Items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.troggs.mmo.Main;

public interface ItemEnchant {
    static ItemMeta clericWeaponEnchants(Main main, ItemMeta meta){
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER, 0);
        return meta;
    }

    static ItemMeta mageWeaponEnchants(Main main, ItemMeta meta){
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER, 0);
        return meta;
    }

    static ItemMeta rangerWeaponEnchants(Main main, ItemMeta meta){
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER, 0);
        return meta;
    }

    static ItemMeta warriorWeaponEnchants(Main main, ItemMeta meta){
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER, 0);
        return meta;
    }
}