package xyz.troggs.mmo.Items.R1.Weapons.Cleric;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
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
        event.setCancelled(true);
        String cooldownKey = "elderberries_" + event.getPlayer().getUniqueId().toString();
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            ((LivingEntity) player).setHealth(((LivingEntity) player).getHealth() + 3);
            if(main.itemHandler.itemCooldowns.contains(cooldownKey)){
                return;
            }
            main.itemHandler.itemCooldowns.add(cooldownKey);
            double cooldownReduction = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER) * 0.05;
            new BukkitRunnable() {
                @Override
                public void run() {
                    main.itemHandler.itemCooldowns.remove(cooldownKey);
                }
            }.runTaskLaterAsynchronously(main, (long) ((300 / (1+cooldownReduction))));
        }else{
            Player target = null;
            try{
                target = getTargetPlayer(player);
            } catch (Exception e) {
            }
            if(target == null){
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new Utils().chat("&cNo player found.")));
                return;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(new Utils().chat("&aHealing &2" + target.getName() + "&a.")));
            ((LivingEntity) target).setHealth(((LivingEntity) target).getHealth() + 3);
            if(main.itemHandler.itemCooldowns.contains(cooldownKey)){
                return;
            }
            main.itemHandler.itemCooldowns.add(cooldownKey);
            double cooldownReduction = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(main, "diminish"), PersistentDataType.INTEGER) * 0.05;
            new BukkitRunnable() {
                @Override
                public void run() {
                    main.itemHandler.itemCooldowns.remove(cooldownKey);
                }
            }.runTaskLaterAsynchronously(main, (long) ((300 / (1+cooldownReduction))));
        }
    }

    public static Player getTargetPlayer(final Player player) {
        return getTarget(player, player.getWorld().getPlayers());
    }

    public static Entity getTargetEntity(final Entity entity) {
        return getTarget(entity, entity.getWorld().getEntities());
    }

    public static <T extends Entity> T getTarget(final Entity entity,
                                                 final Iterable<T> entities) {
        if (entity == null)
            return null;
        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Vector n = other.getLocation().toVector()
                    .subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n)
                    .lengthSquared() < threshold
                    && n.normalize().dot(
                    entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null
                        || target.getLocation().distanceSquared(
                        entity.getLocation()) > other.getLocation()
                        .distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }
}
