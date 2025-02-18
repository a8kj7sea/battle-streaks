package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.PlayerEvent;

@Getter
public class PlayerLivesLostEvent extends PlayerEvent {
    private final int remainingLives;

    public PlayerLivesLostEvent(@NonNull Player player, int remainingLives) {
        super(player);
        this.remainingLives = remainingLives;
    }

}
