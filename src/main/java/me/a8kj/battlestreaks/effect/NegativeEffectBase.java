package me.a8kj.battlestreaks.effect;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class NegativeEffectBase implements NegativeEffect {

    private final int requiredLives;
    private final String name;

    @Override
    public void unApply(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }
}
