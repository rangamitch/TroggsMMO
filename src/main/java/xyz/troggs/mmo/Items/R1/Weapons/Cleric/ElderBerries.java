package xyz.troggs.mmo.Items.R1.Weapons.Cleric;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
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

    public void ability(PlayerInteractEvent event, Main main, ItemStack item){
        String cooldownKey = "elderberries_" + event.getPlayer().getUniqueId().toString();
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
        }.runTaskLaterAsynchronously(main, (long) ((300 / (1+cooldownReduction))));
        Location loc;
        loc = player.getLocation();
        new BukkitRunnable() {
            public void run() {
                if(!main.itemHandler.itemCooldowns.contains(cooldownKey)){
                    this.cancel();
                    return;
                }
                new BukkitRunnable() {
                    double phi = 0;
                    @Override
                    public void run() {
                        phi += Math.PI / 20;
                        for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI/30) {
                            double r = 6;
                            double x = r * cos(theta) * sin(phi);
                            double y = 0.05;
                            double z = r * sin(theta) * sin(phi);
                            loc.add(x, y, z);
                            loc.getWorld().spawnParticle(Particle.WAX_ON, loc, 0, 0, 0, 0, 1);
                            for (Entity en : loc.getWorld().getNearbyEntities(loc, 0.3, 5, 0.3)) {
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
