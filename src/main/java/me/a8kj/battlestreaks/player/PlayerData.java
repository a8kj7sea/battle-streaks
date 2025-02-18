package me.a8kj.battlestreaks.player;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.player.properties.PlayerDataType;

import static java.lang.Math.abs;

public interface PlayerData {

    void setData(Player player, PlayerDataType playerDataType, int amount);

    boolean contains(Player player);

    int getData(Player player, PlayerDataType playerDataType, int callBack);

    default void addData(Player player, PlayerDataType playerDataType, int toAdd) {
        if (playerDataType == null) {
            throw new IllegalArgumentException("Couldn't find player data type!");
        }

        this.setData(player, playerDataType, getData(player, playerDataType, 0) + abs(toAdd));
    }

    default void removeData(Player player, PlayerDataType playerDataType, int toRemove) {
        if (playerDataType == null) {
            throw new IllegalArgumentException("Couldn't find player data type!");
        }

        int currentData = abs(getData(player, playerDataType, 0));

        if (currentData <= abs(toRemove)) {
            setData(player, playerDataType, 0);
            return;
        }
        setData(player, playerDataType, currentData - abs(toRemove));
    }
}
