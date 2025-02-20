package me.a8kj.battlestreaks.configuration.impl;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.battlestreaks.configuration.Configuration;

public class DefaultConfig extends Configuration {

    public DefaultConfig(JavaPlugin plugin) {
        super(plugin, "config", true);
    }

    public String getAbilityNameByStreaks(int minStreaks, int maxStreak) {
        for (String key : getYamConfiguration().getConfigurationSection("abilities").getKeys(false)) {
            int min = getYamConfiguration().getInt("abilities." + key + ".minStreak");
            int max = getYamConfiguration().getInt("abilities." + key + ".maxStreak");

            if (minStreaks >= min && minStreaks <= max) {
                return getYamConfiguration().getString("abilities." + key + ".name");
            }
        }
        return null;
    }

    public String getAbilityMessage(int minStreaks) {
        for (String key : getYamConfiguration().getConfigurationSection("abilities").getKeys(false)) {
            int min = getYamConfiguration().getInt("abilities." + key + ".minStreak");
            int max = getYamConfiguration().getInt("abilities." + key + ".maxStreak");

            if (minStreaks >= min && minStreaks <= max) {
                return getYamConfiguration().getString("abilities." + key + ".message");
            }
        }
        return null;
    }

    public String getEffectByLives(int lives) {
        String key = String.valueOf(lives); // Convert lives to String
        if (getYamConfiguration().contains("lives-system." + key)) {
            return getYamConfiguration().getString("lives-system." + key + ".effect");
        }
        return null; // Return null if not found
    }

    public String getMessageByLives(int lives) {
        String key = String.valueOf(lives); // Convert lives to String
        if (getYamConfiguration().contains("lives-system." + key)) {
            return getYamConfiguration().getString("lives-system." + key + ".message");
        }
        return null; // Return null if not found
    }

}
