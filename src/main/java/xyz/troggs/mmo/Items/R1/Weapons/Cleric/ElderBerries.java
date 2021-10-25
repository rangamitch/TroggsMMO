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

public class ElderBerries {
    public Map<String, Item> item(Main main){
        Map<String, Item> map = new HashMap<>();
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.SWEET_BERRIES);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(utils.chat("Elderberries"));
        meta.setLore(Arrays.asList(utils.chat("&6Item Ability: Feed Others &e&lLEFT CLICK"), utils.chat("&7Left click another player to heal"), utils.chat("&7them for &a3 &7base health"), utils.chat("&f"), utils.chat("&6Item Ability: Feed Self &e&lRIGHT CLICK"), utils.chat("&7Right click to heal yourself"), utils.chat("&7for &a3 &7base health"), utils.chat("&8Cooldown: &a15s")));
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "position"), PersistentDataType.STRING, "mainHand");
        meta = ItemEnchant.clericWeaponEnchants(main, meta);
        item.setItemMeta(meta);
        map.put("elderberries", new Item(main, "elderberries", ItemRarity.COMMON, 1, "CLERIC", item));
        return map;
    }
}
