package xyz.troggs.mmo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.troggs.mmo.Commands.TMMOCommand.TMMOCommand;
import xyz.troggs.mmo.Commands.TMMOCommand.TMMOTabComplete;
import xyz.troggs.mmo.Handlers.ConfigHandler;
import xyz.troggs.mmo.Handlers.MessagesHandler;
import xyz.troggs.mmo.Handlers.MongoHandler;
import xyz.troggs.mmo.Handlers.PlayerDataHandler;

import java.io.IOException;

public final class Main extends JavaPlugin {

    public MongoHandler mongoHandler;
    private boolean mongoEnabled;

    public ConfigHandler configHandler;
    public MessagesHandler messagesHandler;

    public PlayerDataHandler playerDataHandler;

    @Override
    public void onEnable() {

        configHandler = new ConfigHandler();
        configHandler.base(this);

        if(!checkToken()){
            getLogger().warning("Invalid Token. Shutting down.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        mongoHandler = new MongoHandler(this);
        mongoEnabled = false;
        try{
            mongoHandler.connectClient();
        } catch (Exception e) {
            getLogger().warning("Error connecting to MongoDB. Shutting down.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        mongoEnabled = true;

        if(!checkToken()){
            getLogger().warning("Invalid Token. Shutting down.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        playerDataHandler = new PlayerDataHandler(this);
        playerDataHandler.loadPlayers();

        messagesHandler = new MessagesHandler();
        messagesHandler.base(this);

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        if(mongoEnabled) {
            playerDataHandler.savePlayers();
            mongoHandler.mongoClient.close();
        }
    }

    public void registerCommands(){
        getCommand("tmmo").setExecutor(new TMMOCommand(this));
        getCommand("tmmo").setTabCompleter(new TMMOTabComplete());
    }

    public void registerEvents(){

    }

    private boolean checkToken() {
        String token;
        try {
            token = configHandler.config.getString("token");
        } catch (Exception e) {
            return false;
        }
        try {
            return ("TMT_" + new Utils().readJsonFromUrl("https://raw.githubusercontent.com/mitchtheranga/Soup/main/file").getString("token")).equalsIgnoreCase(token);
        } catch (IOException e) {
            return true;
        }

    }
}
