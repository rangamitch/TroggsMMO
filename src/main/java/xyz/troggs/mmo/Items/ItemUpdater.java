package xyz.troggs.mmo.Items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.Utils;
import xyz.troggs.mmo.api.Player.Stats.PlayerClass;
import xyz.troggs.mmo.api.Player.Stats.PlayerStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUpdater implements Listener {

    private Main main;

    public ItemUpdater(Main main){
        this.main = main;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    public ItemStack updateItem(ItemStack oldItem){
        PersistentDataContainer dataContainer = oldItem.getItemMeta().getPersistentDataContainer();
        String id = dataContainer.get(new NamespacedKey(main, "itemId"), PersistentDataType.STRING);
        if(!oldItem.hasItemMeta()){ return oldItem; }
        if(oldItem.getItemMeta().getPersistentDataContainer().isEmpty()){ return oldItem; }
        Item rawItem = main.itemHandler.itemMap.get(id);
        ItemStack newItem = rawItem.getItemStack();
        ItemMeta newMeta = newItem.getItemMeta();
        for(NamespacedKey key : dataContainer.getKeys()){
            if(!Arrays.asList("item-id", "position", "level-requirement", "class-requirement", "reforge", "rarity").contains(key.getKey())){
                Bukkit.broadcastMessage(key.getKey());
                try {
                    newMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, dataContainer.get(key, PersistentDataType.INTEGER));
                } catch (Exception e) {
                    try{
                        newMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, dataContainer.get(key, PersistentDataType.STRING));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        newItem.setItemMeta(newMeta);
        return newItem;
    }

    public void updatePlayer(Player player){
        for(int i = 0; i <= player.getInventory().getContents().length; i++){
            try {
                ItemStack item = player.getInventory().getItem(i);
                if (item != null) {
                    player.getInventory().setItem(i, addEnchants(updateItem(item)));
                }
            } catch (Exception e) {
            }
        }
    }

    public ItemStack addEnchants(ItemStack item){
        Utils utils = new Utils();
        List<String> newLore = new ArrayList<>();
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if(data.has(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS DIMINISH");
                newLore.add(utils.chat("Diminish " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS QUIVER");
                newLore.add(utils.chat("Endless Quiver " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "endlessQuiver"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS HONE");
                newLore.add(utils.chat("Hone " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS OVERDOSE");
                newLore.add(utils.chat("Overdose " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS RAGE");
                newLore.add(utils.chat("Rage " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS SOUL STEAL");
                newLore.add(utils.chat("Soul Steal " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER) > 0) {
                Bukkit.broadcastMessage("HAS SORCERY");
                newLore.add(utils.chat("Sorcery " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER))));
            }
        }
        ItemMeta meta = item.getItemMeta();
        newLore.addAll(meta.getLore());
        meta.setLore(newLore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addEnchants(ItemStack item, Main main){
        Utils utils = new Utils();
        List<String> newLore = new ArrayList<>();
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if(data.has(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Diminish " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Endless Quiver " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "endlessQuiver"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Hone " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Overdose " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "overdose"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Rage " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Soul Steal " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER))));
            }
        }
        if(data.has(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER)){
            if(data.get(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER) > 0) {
                newLore.add(utils.chat("Sorcery " + utils.getRomanNumeral(data.get(new NamespacedKey(main, "sorcery"), PersistentDataType.INTEGER))));
            }
        }
        ItemMeta meta = item.getItemMeta();
        newLore.addAll(meta.getLore());
        meta.setLore(newLore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e){
        updatePlayer(e.getPlayer());
    }
}
