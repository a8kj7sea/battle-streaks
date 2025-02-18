package me.a8kj.battlestreaks.effect.impl;

import me.a8kj.battlestreaks.effect.NegativeEffect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlownessEffect implements NegativeEffect {
    @Override
    public void apply(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 0, false, false));
    }
}
