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
        if(args.length == 3){
            if(args[1].equalsIgnoreCase("give") && args[0].equalsIgnoreCase("admin")){
                ((Player) sender).getInventory().addItem(main.itemHandler.itemMap.get(args[2]).getItemStack());
            }
        }
        return false;
    }
}
