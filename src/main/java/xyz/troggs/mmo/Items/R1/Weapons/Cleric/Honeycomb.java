package xyz.troggs.mmo.Items.R1.Weapons.Cleric;

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

public class Honeycomb {
    public Map<String, Item> item(Main main){
        Map<String, Item> map = new HashMap<>();
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(utils.chat("Honey Pot"));
        meta.setLore(Arrays.asList(utils.chat("&6Item Ability: Feed Others &e&lSNEAK"), utils.chat("&7Sneak to release a pool of honey which"), utils.chat("&7will heal players &a1 &7base health per second for 3 seconds."), utils.chat("&8Cooldown: &a20s")));
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "position"), PersistentDataType.STRING, "offHand");
        meta = ItemEnchant.clericWeaponEnchants(main, meta);
        item.setItemMeta(meta);
        map.put("honeyPot", new Item(main, "honeyPot", ItemRarity.RARE, 3, "CLERIC", item));
        return map;
    }
}
