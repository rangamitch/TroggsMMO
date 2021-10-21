package xyz.troggs.mmo.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import xyz.troggs.mmo.Main;

public class PlayerHandler implements Listener {
    private Main main;

    public PlayerHandler(Main main){
        this.main = main;

        Bukkit.getPluginManager().registerEvents(this, main);
    }

}

