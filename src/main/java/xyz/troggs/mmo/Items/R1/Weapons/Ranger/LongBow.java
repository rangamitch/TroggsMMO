package xyz.troggs.mmo.Items.R1.Weapons.Ranger;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.troggs.mmo.Items.Item;
import xyz.troggs.mmo.Items.ItemEnchant;
import xyz.troggs.mmo.Items.ItemRarity;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongBow {
    public Map<String, Item> item(Main main){
        Map<String, Item> map = new HashMap<>();
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(utils.chat("Long Bow"));
        meta.setLore(Arrays.asList(utils.chat("&7&a30% &7chance to consume ammo")));
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "position"), PersistentDataType.STRING, "mainHand");
        meta = ItemEnchant.rangerWeaponEnchants(main, meta);
        item.setItemMeta(meta);
        map.put("longBow", new Item(main, "longBow", ItemRarity.COMMON, 1, "RANGER", item));
        return map;
    }
}
