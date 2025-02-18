package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.api.player.PlayerEvent;

public class AbilityActivateEvent extends PlayerEvent {

    public AbilityActivateEvent(Player player) {
        super(player);
    }

}
