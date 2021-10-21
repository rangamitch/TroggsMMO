package xyz.troggs.mmo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.troggs.mmo.Handlers.ConfigHandler;
import xyz.troggs.mmo.Handlers.MongoHandler;

import java.io.IOException;

public final class Main extends JavaPlugin {

    public MongoHandler mongoHandler;

    public ConfigHandler configHandler;

    @Override
    public void onEnable() {
        mongoHandler = new MongoHandler(this);
        try{
            mongoHandler.connectClient();
        } catch (Exception e) {
            getLogger().severe("Error connecting to MongoDB. Shutting down.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        configHandler = new ConfigHandler();
        configHandler.base(this);

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){

    }

    public void registerEvents(){

    }

    private void checkToken() throws IOException {
        String token = configHandler.config.getString("token");
        System.out.println(new Utils().readJsonFromUrl("https://raw.githubusercontent.com/mitchtheranga/Soup/main/file").getString("token"));
    }
}
