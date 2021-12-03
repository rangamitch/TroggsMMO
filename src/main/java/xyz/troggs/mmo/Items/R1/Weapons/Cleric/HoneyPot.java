package xyz.troggs.mmo.Items.R1.Weapons.Cleric;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.troggs.mmo.Items.Item;
import xyz.troggs.mmo.Items.ItemEnchant;
import xyz.troggs.mmo.Items.ItemRarity;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class HoneyPot {
    public Map<String, Item> item(Main main){
        Map<String, Item> map = new HashMap<>();
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.HONEY_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(utils.chat("Honey Pot"));
        meta.setLore(Arrays.asList(utils.chat("&6Item Ability: Feed Others &e&lSNEAK RIGHT CLICK"), utils.chat("&7Sneak to release a pool of honey which"), utils.chat("&7will heal players &a1 &7base health "), utils.chat("&7per second for 3 seconds."), utils.chat("&8Cooldown: &a30s")));
        meta.getPersistentDataContainer().set(new NamespacedKey(main, "position"), PersistentDataType.STRING, "offHand");
        meta = ItemEnchant.clericWeaponEnchants(main, meta);
        item.setItemMeta(meta);
        map.put("honeyPot", new Item(main, "honeyPot", ItemRarity.RARE, 3, "CLERIC", item));
        return map;
    }

    public void ability(PlayerInteractEvent event, Main main, ItemStack item){
        if(!event.getPlayer().isSneaking()){
            return;
        }
        String cooldownKey = "honeyPot_" + event.getPlayer().getUniqueId().toString();
        if(main.itemHandler.itemCooldowns.contains(cooldownKey)){
            return;
        }
        Player player = event.getPlayer();
        main.itemHandler.itemCooldowns.add(cooldownKey);
        double cooldownReduction = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER) * 0.05;
        new BukkitRunnable() {
            @Override
            public void run() {
                main.itemHandler.itemCooldowns.remove(cooldownKey);
            }
        }.runTaskLaterAsynchronously(main, (long) ((600 / (1+cooldownReduction))));
        Location loc;
        loc = player.getLocation();
        new BukkitRunnable() {
            Long stopTime = System.currentTimeMillis() + 3500;
            Long healTime = System.currentTimeMillis();
            public void run() {
                if(!main.itemHandler.itemCooldowns.contains(cooldownKey)){
                    this.cancel();
                    return;
                }
                if(System.currentTimeMillis() >= stopTime){
                    this.cancel();
                    return;
                }
                Location location = player.getLocation();
                if(System.currentTimeMillis() >= healTime){
                    healTime += 1000;
                    for (Entity en : location.getWorld().getNearbyEntities(location, 1.5, 5, 1.5)) {
                        if (en.getType().equals(EntityType.PLAYER)) {
                            if (!en.isDead()) {
                                double amount = 1;
                                LivingEntity e = ((LivingEntity) en);
                                double ch = e.getHealth();
                                if (ch + amount > e.getMaxHealth()) {
                                    e.setHealth(e.getMaxHealth());
                                } else {
                                    e.setHealth(ch + amount);
                                }
                            }
                        }
                    }
                }
                new BukkitRunnable() {
                    double phi = 0;
                    @Override
                    public void run() {
                        phi += Math.PI / 20;
                        for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI/30) {
                            double r = 1.5;
                            double x = r * cos(theta) * sin(phi);
                            double y = 0.05;
                            double z = r * sin(theta) * sin(phi);
                            loc.add(x, y, z);
                            loc.getWorld().spawnParticle(Particle.WAX_ON, loc, 0, 0, 0, 0, 1);
                            loc.subtract(x, y, z);
                        }
                        if(phi > Math.PI){
                            this.cancel();
                        }
                    }
                }.runTaskTimer(main, 0, 0);
            }
        }.runTaskTimer(main, 0, 10);
    }
}
