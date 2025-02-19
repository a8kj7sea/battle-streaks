package me.a8kj.battlestreaks.player;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.player.properties.PlayerDataType;

import static java.lang.Math.abs;

public interface PlayerData {

    public enum DataType {
        TEXT, NUMBER;
    }

    void setData(Player player, PlayerDataType playerDataType, int amount);

    void setData(Player player, PlayerDataType playerDataType, String value);

    boolean contains(Player player);

    int getData(Player player, PlayerDataType playerDataType, int callBack);

    String getData(Player player, PlayerDataType playerDataType, String callBack);

    default void addData(Player player, PlayerDataType playerDataType, int toAdd) {
        if (playerDataType == null) {
            throw new IllegalArgumentException("Couldn't find player data type!");
        }

        this.setData(player, playerDataType, getData(player, playerDataType, 0) + abs(toAdd));
    }

    default boolean hasData(Player player, PlayerDataType playerDataType, DataType dataType) {

        if (!contains(player))
            return false;

        if (DataType.NUMBER == dataType) {
            return getData(player, playerDataType, 0) != 0;
        } else {
            return getData(player, playerDataType, null) != null;
        }
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
