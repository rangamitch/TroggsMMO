package xyz.troggs.mmo.Items.R1.Weapons.Mage;

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

public class WandOfSparking {

    public Map<String, Item> item(Main main){
        Map<String, Item> map = new HashMap<>();
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(utils.chat("Wand of Sparking"));
        meta.setLore(Arrays.asList(utils.chat("&6Item Ability: Sparks &e&lCLICK"), utils.chat("&7Shoots sparks out which damages enemies. Enemies"), utils.chat("&7hit by the sparks will take &a3 &7base damage"), utils.chat("&7and will be set on fire.")));
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "position"), PersistentDataType.STRING, "mainHand");
        meta = ItemEnchant.mageWeaponEnchants(main, meta);
        item.setItemMeta(meta);
        map.put("wandOfSparking", new Item(main, "wandOfSparking", ItemRarity.COMMON, 1, "MAGE", item));
        return map;
    }

}
