package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.effect.NegativeEffectManager;
import me.a8kj.battlestreaks.effect.impl.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class NegativeEffectManagerImpl implements NegativeEffectManager {
    private final Map<String, NegativeEffect> effectMap = new HashMap<>();
    private final Map<UUID, NegativeEffect> players = new HashMap<>();

    public NegativeEffectManagerImpl() {
        effectMap.put("slowness", new SlownessEffect(4, "slowness"));
        effectMap.put("weakness", new WeaknessEffect(3, "weakness"));
        effectMap.put("mining_fatigue", new MiningFatigueEffect(2, "mining_fatigue"));
        effectMap.put("jump_slow_fall", new JumpAndSlowFallEffect(1, "jump_slow_fall"));
        effectMap.put("glowing_all", new GlowingAndAllEffects(0, "glowing_all"));
    }

    @Override
    public void applyNegativeEffect(Player player, String effectName) {
        NegativeEffect effect = effectMap.get(effectName.toLowerCase());
        if (effect != null) {
            effect.apply(player);
            players.put(player.getUniqueId(), effect);
        } else {
            player.sendMessage("Debug: Effect not found!");
        }
    }

    @Override
    public boolean contains(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    @Override
    public void unApplyNegativeEffect(Player player, String effectName) {
        NegativeEffect effect = effectMap.get(effectName.toLowerCase());
        if (effect != null) {
            effect.unApply(player);
            players.remove(player.getUniqueId());
        }
    }

    @Override
    public void addNegativeEffect(String name, NegativeEffect effect) {
        effectMap.put(name.toLowerCase(), effect);
    }

    @Override
    public Optional<NegativeEffect> getNegativeEffect(String name) {
        return Optional.ofNullable(effectMap.get(name.toLowerCase()));
    }

    @Override
    public Optional<NegativeEffect> getNegativeEffectForPlayer(Player player) {
        return Optional.ofNullable(players.get(player.getUniqueId()));
    }

    @Override
    public Map<String, NegativeEffect> getEffectMap() {
        return new HashMap<>(effectMap);
    }

    @Override
    public Map<UUID, NegativeEffect> getPlayers() {
        return new HashMap<>(players);
    }

    @Override
    public void clear() {
        effectMap.clear();
        players.clear();
    }
}
