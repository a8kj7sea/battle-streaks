package me.a8kj.battlestreaks.configuration.impl;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.battlestreaks.configuration.Configuration;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

public class DefaultConfig extends Configuration {

    public DefaultConfig(JavaPlugin plugin) {
        super(plugin, "config", true);
    }

    public boolean isEffectEnabled(String name) {
        if (!getYamConfiguration().contains("effects." + name.toLowerCase()))
            return false;
        return getYamConfiguration().getBoolean("effects." + name.toLowerCase() + ".enabled");
    }

    public int getRequiredLivesForEffect(String name) {
        if (!getYamConfiguration().contains("effects." + name.toLowerCase()))
            return -1;
        return getYamConfiguration().getInt("effects." + name.toLowerCase() + ".lives");
    }

    public boolean isAbilityEnabled(String name) {
        if (!getYamConfiguration().contains("abilities." + name.toLowerCase()))
            return false;
        return getYamConfiguration().getBoolean("abilities." + name.toLowerCase() + ".enabled");
    }

    public CooldownTime getAbilityCooldownTime(String name) {
        if (!getYamConfiguration().contains("abilities." + name.toLowerCase()))
            return null;
        int minutes = getYamConfiguration().getInt("abilities." + name.toLowerCase() + ".cooldown.minutes");
        int seconds = getYamConfiguration().getInt("abilities." + name.toLowerCase() + ".cooldown.seconds");

        return new CooldownTime(minutes, seconds);
    }

    public int getAbilityMinStreaks(String name) {
        if (!getYamConfiguration().contains("abilities." + name.toLowerCase()))
            return -1;
        return getYamConfiguration().getInt("abilities." + name.toLowerCase() + ".min-streaks");
    }

    public int getAbilityMaxStreaks(String name) {
        if (!getYamConfiguration().contains("abilities." + name.toLowerCase()))
            return -1;
        return getYamConfiguration().getInt("abilities." + name.toLowerCase() + ".max-streaks");
    }
}
