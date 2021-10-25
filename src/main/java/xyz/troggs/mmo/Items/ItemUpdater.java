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
import xyz.troggs.mmo.api.Player.Stats.PlayerClass;
import xyz.troggs.mmo.api.Player.Stats.PlayerStats;

import java.util.Arrays;

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
                    player.getInventory().setItem(i, updateItem(item));
                }
            } catch (Exception e) {
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e){
        updatePlayer(e.getPlayer());
    }
}
