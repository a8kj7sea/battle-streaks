package me.a8kj.battlestreaks.configuration.impl;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.battlestreaks.configuration.Configuration;

public class DefaultConfig extends Configuration {

    public DefaultConfig(JavaPlugin plugin, String child, boolean saveDefaultData) {
        super(plugin, "config", true);
    }

}
