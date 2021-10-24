package xyz.troggs.mmo.Handlers.FileHandlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.troggs.mmo.Main;

import java.io.File;
import java.io.IOException;

public class MessagesHandler {
    public File configFile;
    public FileConfiguration config;

    public void base(Main m){
        if(!m.getDataFolder().exists()){
            m.getDataFolder().mkdirs();
        }
        configFile = new File(m.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            m.saveResource("messages.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfig() {config = YamlConfiguration.loadConfiguration(configFile);}
    public void resetConfig(Main m) {
        File configFile = new File(m.getDataFolder(), "messages.yml");
        configFile.delete();
        File newConfig = new File(m.getDataFolder(), "messages.yml");
        if (!newConfig.exists()) {
            m.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(newConfig);
    }
    public void saveConfig() throws IOException {config.save(configFile);}
}