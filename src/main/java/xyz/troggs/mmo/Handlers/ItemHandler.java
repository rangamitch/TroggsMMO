package xyz.troggs.mmo.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.troggs.mmo.Items.Item;
import xyz.troggs.mmo.Items.R1.Weapons.Cleric.ElderBerries;
import xyz.troggs.mmo.Items.R1.Weapons.Cleric.HoneyPot;
import xyz.troggs.mmo.Items.R1.Weapons.R1WeaponHandler;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.api.Player.PlayerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHandler implements Listener {
    private Main main;

    public Map<String, Item> itemMap = new HashMap<>();
    public List<String> itemCooldowns = new ArrayList<>();

    public ItemHandler(Main main){
        this.main = main;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

    public void registerItem(){
        itemMap.putAll(new R1WeaponHandler(main).getItems());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() == null){
            return;
        }else if(!e.getItem().hasItemMeta()){
            return;
        }else if(!e.getItem().getItemMeta().hasLore()){
            return;
        }else if(!e.getItem().getItemMeta().hasDisplayName()){
            return;
        }

        ItemMeta itemMeta = e.getItem().getItemMeta();
        int levelReq = itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "level-requirement"), PersistentDataType.INTEGER);
        String classReq = itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "class-requirement"), PersistentDataType.STRING);
        //JSONObject json = new JSONObject(jsonLine);

        //JSONObject requirements = json.getJSONObject("requirements");
        //int levelReq = requirements.getInt("level");
        //String classReq = requirements.getString("class");

        e.getPlayer().sendMessage("Level: " + levelReq + " | Class: " + classReq);
        PlayerData playerData = main.playerDataHandler.playerMap.get(e.getPlayer().getUniqueId().toString());
        boolean makesLevelReqs = playerData.getStats().getLevel() >= levelReq;
        boolean makesClassReqs = playerData.getStats().getPlayerClass().toString().equalsIgnoreCase(classReq);
        Bukkit.broadcastMessage((makesClassReqs && makesLevelReqs) ? "MAKES REQS" : "DOESN'T MAKE REQS");
        itemAbility(e, itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "item-id"), PersistentDataType.STRING), e.getItem());
        return;
    }

    public void itemAbility(PlayerInteractEvent event, String itemId, ItemStack item){
        Bukkit.broadcastMessage(itemId);
        if(itemId.equalsIgnoreCase("elderberries")){
            new ElderBerries().ability(event, main, item);
            return;
        }
        if(itemId.equalsIgnoreCase("honeyPot")){
            new HoneyPot().ability(event, main, item);
            return;
        }
    }
}
