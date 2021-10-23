package xyz.troggs.mmo.Commands.TMMOCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.troggs.mmo.Main;
import xyz.troggs.mmo.Utils;

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
        return false;
    }
}
