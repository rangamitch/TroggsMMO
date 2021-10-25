package xyz.troggs.mmo.Commands.TMMOCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import xyz.troggs.mmo.Main;

import java.util.Arrays;
import java.util.List;

public class TMMOTabComplete implements TabCompleter {

    private Main main;

    public TMMOTabComplete(Main main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            if(sender.hasPermission("tmmo.admin")) {
                return Arrays.asList("admin");
            }
        }
        if(args.length == 2){
            if(sender.hasPermission("tmmo.admin") && args[0].equalsIgnoreCase("admin")) {
                return Arrays.asList("stats", "give");
            }
        }
        if(args.length == 4){
            if(sender.hasPermission("tmmo.admin") && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("stats")) {
                return Arrays.asList("view", "edit", "reset");
            }
        }
        if(args.length == 3){
            if(sender.hasPermission("tmmo.admin") && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("give")) {
                return main.itemHandler.itemMap.keySet().stream().toList();
            }
        }
        return null;
    }
}
