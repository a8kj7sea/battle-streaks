package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.api.player.PlayerEvent;

public class PlayerAbilityActivateEvent extends PlayerEvent {

    public PlayerAbilityActivateEvent(Player player) {
        super(player);
    }

}
