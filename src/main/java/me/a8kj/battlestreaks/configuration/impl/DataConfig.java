package me.a8kj.battlestreaks.configuration.impl;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.battlestreaks.configuration.Configuration;
import me.a8kj.battlestreaks.player.PlayerData;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;

public class DataConfig extends Configuration implements PlayerData {

    public DataConfig(JavaPlugin plugin) {
        super(plugin, "players-data", true);
    }

    @Override
    public void setData(Player player, PlayerDataType playerDataType, int amount) {
        this.configurationFile.set("players-data." + player.getName() + "." + playerDataType.name().toLowerCase(),
                amount);
        this.save();
    }

    @Override
    public int getData(Player player, PlayerDataType playerDataType, int callBack) {
        if (!contains(player)) {
            return callBack;
        }

        return this.configurationFile
                .getInt("players-data." + player.getName() + "." + playerDataType.name().toLowerCase(), callBack);
    }

    @Override
    public String getData(Player player, PlayerDataType playerDataType, String callBack) {
        if (!contains(player)) {
            return callBack;
        }

        return this.configurationFile
                .getString("players-data." + player.getName() + "." + playerDataType.name().toLowerCase(), callBack);
    }

    @Override
    public boolean contains(Player player) {
        return this.configurationFile.contains("players-data." + player.getName());
    }

    @Override
    public void setData(Player player, PlayerDataType playerDataType, String value) {
        this.configurationFile.set("players-data." + player.getName() + "." + playerDataType.name().toLowerCase(),
                value.toLowerCase());
        this.save();
    }

    @Override
    public boolean hasAbility(Player player) {
        return getData(player, PlayerDataType.ABILITY, "NONE") != "NONE";
    }

}
