package me.a8kj.battlestreaks.configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Configuration {

    @Getter
    private final File file;
    @Getter
    protected FileConfiguration configurationFile;
    private final Logger logger;

    public Configuration(JavaPlugin plugin, String child, boolean saveDefaultData) {
        this.logger = plugin.getLogger();
        if (!child.endsWith(".yml") && !child.endsWith(".yaml")) {
            child += ".yml";
        }

        this.file = new File(plugin.getDataFolder(), child);
        createFile(plugin, saveDefaultData);
        load();
    }

    private void createFile(JavaPlugin plugin, boolean saveDefaultData) {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            if (saveDefaultData) {
                try {
                    plugin.saveResource(file.getName(), saveDefaultData);
                    // logger.info("Default config file created: " + file.getName());
                } catch (Exception e) {
                    // logger.log(Level.SEVERE, "Failed to save default config file: " +
                    // file.getName(), e);
                }
            } else {
                try {
                    file.createNewFile();
                    // logger.info("Config file created: " + file.getName());
                } catch (IOException e) {
                    // logger.log(Level.SEVERE, "Failed to create config file: " + file.getName(),
                    // e);
                }
            }
        }
    }

    public void save() {
        try {
            configurationFile.save(file);
            // logger.info("Config file saved: " + file.getName());
        } catch (IOException e) {
            // logger.log(Level.SEVERE, "Failed to save config file: " + file.getName(), e);
        }
    }

    public void load() {
        configurationFile = YamlConfiguration.loadConfiguration(file);
        // logger.info("Config file loaded: " + file.getName());
    }

    public YamlConfiguration getYamConfiguration() {
        return (YamlConfiguration) getConfigurationFile();
    }
}