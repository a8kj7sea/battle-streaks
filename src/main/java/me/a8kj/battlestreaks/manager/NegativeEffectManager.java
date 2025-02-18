package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.effect.impl.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NegativeEffectManager {
    private static final Map<String, NegativeEffect> effectMap = new HashMap<>();
    private static final Map<UUID, NegativeEffect> players = new HashMap<>();
    static {
        effectMap.put("slowness", new SlownessEffect(4, "slowness"));
        effectMap.put("weakness", new WeaknessEffect(3, "weakness"));
        effectMap.put("mining_fatigue", new MiningFatigueEffect(2, "mining_fatigue"));
        effectMap.put("jump_slow_fall", new JumpAndSlowFallEffect(1, "jump_slow_fall"));
        effectMap.put("glowing_all", new GlowingAndAllEffects(0, "glowing_all"));
    }

    public static void applyNegativeEffect(Player player, String effectName) {
        NegativeEffect effect = effectMap.get(effectName.toLowerCase());
        if (effect != null) {
            effect.apply(player);
            players.put(player.getUniqueId(), effect);
        }
    }

    public static boolean contains(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public static void unApplyNegativeEffect(Player player, String effectName) {
        NegativeEffect effect = effectMap.get(effectName.toLowerCase());
        if (effect != null) {
            effect.unApply(player);
            players.remove(player.getUniqueId());
        }
    }

    public static void addNegativeEffect(String name, NegativeEffect effect) {
        effectMap.put(name.toLowerCase(), effect);
    }

    public static NegativeEffect getNegativeEffect(String name) {
        return effectMap.get(name.toLowerCase());
    }

    public static NegativeEffect getNegativeEffectForPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static void clear() {
        effectMap.clear();
    }
}
