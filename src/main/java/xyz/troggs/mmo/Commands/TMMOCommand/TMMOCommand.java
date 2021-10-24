package xyz.troggs.mmo.Commands.TMMOCommand;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.Utils;

import java.util.Arrays;

public class TMMOCommand implements CommandExecutor {

    private Main main;

    public TMMOCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("tmmo.admin")){
            sender.sendMessage(new Utils().chat(main.messagesHandler.config.getString("no-perms")));
            return true;
        }
        Utils utils = new Utils();
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(utils.chat("INFO"), utils.chat(""), utils.chat("&9&lRARE")));
        meta.setDisplayName(utils.chat("&"));
        NamespacedKey levelReq = new NamespacedKey(main, "levelRequirement");
        NamespacedKey classReq = new NamespacedKey(main, "classRequirement");
        meta.getPersistentDataContainer().set(levelReq, PersistentDataType.INTEGER, 10);
        meta.getPersistentDataContainer().set(classReq, PersistentDataType.STRING, "MAGE");
        item.setItemMeta(meta);
        ((Player)sender).getInventory().addItem(item);
        return false;
    }
}
