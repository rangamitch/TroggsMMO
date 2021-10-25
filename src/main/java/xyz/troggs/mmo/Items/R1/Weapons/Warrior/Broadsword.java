package xyz.troggs.mmo.Items.R1.Weapons.Warrior;

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

public class Broadsword {
    public Map<String, Item> item(Main main){
        Map<String, Item> map = new HashMap<>();
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(utils.chat("Broadsword"));
        meta.setLore(Arrays.asList(utils.chat("&7Deal extra damage while sneaking")));
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "position"), PersistentDataType.STRING, "mainHand");
        meta = ItemEnchant.warriorWeaponEnchants(main, meta);
        item.setItemMeta(meta);
        map.put("broadsword", new Item(main, "broadsword", ItemRarity.COMMON, 1, "WARRIOR", item));
        return map;
    }
}
