package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.effect.impl.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class NegativeEffectManager {
    private static final Map<Integer, NegativeEffect> effectMap = new HashMap<>();

    static {
        effectMap.put(4, new SlownessEffect());
        effectMap.put(3, new WeaknessEffect());
        effectMap.put(2, new MiningFatigueEffect());
        effectMap.put(1, new JumpAndSlowFallEffect());
        effectMap.put(0, new GlowingAndAllEffects());
    }

    public static void applyNegativeEffect(Player player, int remainingLives) {
        NegativeEffect effect = effectMap.get(remainingLives);
        if (effect != null) {
            effect.apply(player);
        }
    }
}
