package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.PlayerEvent;

@Getter
public class PlayerKillStreakEvent extends PlayerEvent {
    private final int streaks;
    private final KillStreakStatus status;

    public enum KillStreakStatus {
        ACHIEVED, LOST
    }

    public PlayerKillStreakEvent(@NonNull Player player, int streaks, KillStreakStatus status) {
        super(player);
        this.streaks = streaks;
        this.status = status;
    }
}
