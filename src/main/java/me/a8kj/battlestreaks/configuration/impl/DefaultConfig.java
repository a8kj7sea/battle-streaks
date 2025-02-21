package me.a8kj.battlestreaks.configuration.impl;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.battlestreaks.configuration.Configuration;

public class DefaultConfig extends Configuration {

    public DefaultConfig(JavaPlugin plugin) {
        super(plugin, "config", true);
    }

    public boolean isEffectEnabled(String name) {
        if (getYamConfiguration().contains("effects." + name.toLowerCase()))
            return false;
        return getYamConfiguration().getBoolean("effects." + name.toLowerCase() + ".enabled");
    }

    public int getRequiredLivesForEffect(String name) {
        if (getYamConfiguration().contains("effects." + name.toLowerCase()))
            return -1;
        return getYamConfiguration().getInt("effects." + name.toLowerCase() + ".lives");
    }
}
