package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.PlayerEvent;

@Getter
public class KillstreakAchieveEvent extends PlayerEvent {
    private final int streaks;

    public KillstreakAchieveEvent(@NonNull Player player, int streaks) {
        super(player);
        this.streaks = streaks;
    }

}
