package xyz.troggs.mmo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.troggs.mmo.Commands.TMMOCommand.TMMOCommand;
import xyz.troggs.mmo.Commands.TMMOCommand.TMMOTabComplete;
import xyz.troggs.mmo.Handlers.DataHandlers.MongoHandler;
import xyz.troggs.mmo.Handlers.DataHandlers.PlayerDataHandler;
import xyz.troggs.mmo.Handlers.DataHandlers.RedisHandler;
import xyz.troggs.mmo.Handlers.FileHandlers.ConfigHandler;
import xyz.troggs.mmo.Handlers.FileHandlers.MessagesHandler;
import xyz.troggs.mmo.Handlers.ItemHandler;
import xyz.troggs.mmo.Items.ItemUpdater;

import java.io.IOException;

public final class Main extends JavaPlugin {

    public MongoHandler mongoHandler;
    private boolean mongoEnabled;

    public RedisHandler redisHandler;

    public ConfigHandler configHandler;
    public MessagesHandler messagesHandler;

    public PlayerDataHandler playerDataHandler;

    public ItemHandler itemHandler;

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

        itemHandler = new ItemHandler(this);
        itemHandler.registerItem();

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
        getCommand("tmmo").setTabCompleter(new TMMOTabComplete(this));
    }

    public void registerEvents(){
        new ItemUpdater(this);
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
