package me.a8kj.battlestreaks.configuration.impl;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.battlestreaks.configuration.Configuration;

public class DataConfig extends Configuration {

    public DataConfig(JavaPlugin plugin) {
        super(plugin, "players-data", true);
    }

}
