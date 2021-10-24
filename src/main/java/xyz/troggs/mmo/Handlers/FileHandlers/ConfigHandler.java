package xyz.troggs.mmo.Handlers.FileHandlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.troggs.mmo.Main;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    public File configFile;
    public FileConfiguration config;

    public void base(Main m){
        if(!m.getDataFolder().exists()){
            m.getDataFolder().mkdirs();
        }
        configFile = new File(m.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            m.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfig() {config = YamlConfiguration.loadConfiguration(configFile);}
    public void resetConfig(Main m) {
        File configFile = new File(m.getDataFolder(), "config.yml");
        configFile.delete();
        File newConfig = new File(m.getDataFolder(), "config.yml");
        if (!newConfig.exists()) {
            m.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(newConfig);
    }
    public void saveConfig() throws IOException {config.save(configFile);}
}