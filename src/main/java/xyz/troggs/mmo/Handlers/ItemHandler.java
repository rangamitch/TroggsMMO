package xyz.troggs.mmo.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.troggs.mmo.Items.Item;
import xyz.troggs.mmo.Items.R1.Weapons.Cleric.ElderBerries;
import xyz.troggs.mmo.Items.R1.Weapons.Cleric.HoneyPot;
import xyz.troggs.mmo.Items.R1.Weapons.R1WeaponHandler;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.api.Player.PlayerData;

import java.util.*;

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

        PlayerData playerData = main.playerDataHandler.playerMap.get(e.getPlayer().getUniqueId().toString());
        boolean makesLevelReqs = playerData.getStats().getLevel() >= levelReq;
        boolean makesClassReqs = playerData.getStats().getPlayerClass().toString().equalsIgnoreCase(classReq);
        itemAbility(e, itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "item-id"), PersistentDataType.STRING), e.getItem(), makesClassReqs && makesLevelReqs);
        return;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager().getType().equals(EntityType.PLAYER)){
            Player player = (Player) e.getDamager();
            ItemStack item = ((Player) e.getDamager()).getItemInUse();
            if(item == null){
                return;
            }else if(!item.hasItemMeta()){
                return;
            }else if(!item.getItemMeta().hasLore()){
                return;
            }else if(!item.getItemMeta().hasDisplayName()){
                return;
            }

            ItemMeta itemMeta = item.getItemMeta();
            int levelReq = itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "level-requirement"), PersistentDataType.INTEGER);
            String classReq = itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "class-requirement"), PersistentDataType.STRING);
            //JSONObject json = new JSONObject(jsonLine);

            //JSONObject requirements = json.getJSONObject("requirements");
            //int levelReq = requirements.getInt("level");
            //String classReq = requirements.getString("class");

            PlayerData playerData = main.playerDataHandler.playerMap.get(player.getUniqueId().toString());
            boolean makesLevelReqs = playerData.getStats().getLevel() >= levelReq;
            boolean makesClassReqs = playerData.getStats().getPlayerClass().toString().equalsIgnoreCase(classReq);
            itemMelee(e, player, item, makesClassReqs && makesLevelReqs, itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "item-id"), PersistentDataType.STRING));
            Bukkit.broadcastMessage("OOF");
        }
    }

    @EventHandler
    public void playerShoot(EntityShootBowEvent e){
        if(e.getEntity().getType().equals(EntityType.PLAYER)){
            Player player = (Player) e.getEntity();
            ItemStack item = e.getBow();
            if(item == null){
                return;
            }else if(!item.hasItemMeta()){
                return;
            }else if(!item.getItemMeta().hasLore()){
                return;
            }else if(!item.getItemMeta().hasDisplayName()){
                return;
            }

            ItemMeta itemMeta = item.getItemMeta();
            int levelReq = itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "level-requirement"), PersistentDataType.INTEGER);
            String classReq = itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "class-requirement"), PersistentDataType.STRING);
            //JSONObject json = new JSONObject(jsonLine);

            //JSONObject requirements = json.getJSONObject("requirements");
            //int levelReq = requirements.getInt("level");
            //String classReq = requirements.getString("class");

            PlayerData playerData = main.playerDataHandler.playerMap.get(player.getUniqueId().toString());
            boolean makesLevelReqs = playerData.getStats().getLevel() >= levelReq;
            boolean makesClassReqs = playerData.getStats().getPlayerClass().toString().equalsIgnoreCase(classReq);
            bowShoot(e, player, makesClassReqs && makesLevelReqs, itemMeta.getPersistentDataContainer().get(new NamespacedKey(main, "item-id"), PersistentDataType.STRING));
        }
    }

    public void itemAbility(PlayerInteractEvent event, String itemId, ItemStack item, boolean meetsReqs){
        if(!meetsReqs){
            event.setCancelled(true);
            return;
        }
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

    public void itemMelee(EntityDamageByEntityEvent event, Player player, ItemStack item, boolean meetsReqs, String itemId){
        Bukkit.broadcastMessage("itemMelee");
        if(!meetsReqs){
            event.setCancelled(true);
            return;
        }
        Bukkit.broadcastMessage("meetsReqs");
        if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER)){
            if(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER) > 0){
                int rage = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "rage"), PersistentDataType.INTEGER);
                if(new Random().nextInt(10) < rage){
                    Bukkit.broadcastMessage("rage");
                    if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 3, 0, true, true));
                    }else{
                        if(player.getPotionEffect(PotionEffectType.FAST_DIGGING).getAmplifier() == 0){
                            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, player.getPotionEffect(PotionEffectType.FAST_DIGGING).getDuration() + 3, 0, true, true));
                        }
                    }
                }
            }
        }
        Bukkit.broadcastMessage("rageChecked");
        if(itemId.equalsIgnoreCase("broadsword")){
            Bukkit.broadcastMessage("broadsword");
            if(player.isSneaking()){
                Bukkit.broadcastMessage("sneaking");
                event.setDamage(event.getDamage() + 1);
            }
        }
        if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER)){
            if(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER) > 0){
                int hone = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "hone"), PersistentDataType.INTEGER);
                if(new Random().nextInt(100) < hone){
                    Bukkit.broadcastMessage("hone");
                    event.setDamage(event.getDamage() * 2);
                }
            }
        }
        Bukkit.broadcastMessage("honeChecked");
        if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER)){
            if(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER) > 0){
                int soulSteal = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "soul-steal"), PersistentDataType.INTEGER);
                if(new Random().nextInt(100) < soulSteal){
                    Bukkit.broadcastMessage("soulSteal");
                    player.setHealth(player.getHealth() + 1);
                }
            }
        }
        Bukkit.broadcastMessage("soulStealChecked");
    }

    public void bowShoot(EntityShootBowEvent event, Player player, boolean meetsReqs, String itemId){
        if(!meetsReqs){
            event.setCancelled(true);
            return;
        }
        ItemStack item = event.getBow();
        if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER)){
            if(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER) > 0){
                int soulSteal = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "endless-quiver"), PersistentDataType.INTEGER);
                if(new Random().nextInt(10) < soulSteal){
                    Bukkit.broadcastMessage("endless-quiver");
                    player.getInventory().addItem(event.getConsumable());
                }
            }
        }
    }
}
