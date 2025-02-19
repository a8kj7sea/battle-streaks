package me.a8kj.battlestreaks.api.player.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import me.a8kj.battlestreaks.api.player.PlayerEvent;
import me.a8kj.battlestreaks.effect.NegativeEffect;

@Getter
public class PlayerEffectAppliedEvent extends PlayerEvent {

    public PlayerEffectAppliedEvent(Player player, NegativeEffect effect) {
        super(player);
        this.effect = effect;
    }

    private final NegativeEffect effect;
}
