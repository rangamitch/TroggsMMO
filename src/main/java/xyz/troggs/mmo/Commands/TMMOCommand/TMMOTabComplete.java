package xyz.troggs.mmo.Commands.TMMOCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class TMMOTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            if(sender.hasPermission("tmmo.admin")) {
                return Arrays.asList("admin");
            }
        }
        if(args.length == 2){
            if(sender.hasPermission("tmmo.admin") && args[0].equalsIgnoreCase("admin")) {
                return Arrays.asList("stats");
            }
        }
        if(args.length == 4){
            if(sender.hasPermission("tmmo.admin") && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("stats")) {
                return Arrays.asList("view", "edit", "reset");
            }
        }
        return null;
    }
}
