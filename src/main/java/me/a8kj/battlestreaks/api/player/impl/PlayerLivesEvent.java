package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.PlayerEvent;

@Getter
public class PlayerLivesEvent extends PlayerEvent {
    private final int remainingLives;
    private final LivesStatus status;

    public enum LivesStatus {
        ACHIEVED, LOST
    }

    public PlayerLivesEvent(@NonNull Player player, int remainingLives, LivesStatus livesStatus) {
        super(player);
        this.remainingLives = remainingLives;
        this.status = livesStatus;
    }

}
