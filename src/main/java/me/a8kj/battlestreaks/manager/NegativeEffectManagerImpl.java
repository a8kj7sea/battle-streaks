package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.effect.NegativeEffectManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class NegativeEffectManagerImpl implements NegativeEffectManager {
    private final Map<String, NegativeEffect> effectMap = new HashMap<>();
    private final Map<UUID, NegativeEffect> players = new HashMap<>();

    @Override
    public void applyNegativeEffect(Player player, String effectName) {
        if (players.containsKey(player.getUniqueId())) {
            players.remove(player.getUniqueId());
        }
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

    @Override
    public void registerNegativeEffect(String name, NegativeEffect negativeEffect) {
        if (name == null || negativeEffect == null) {
            throw new IllegalArgumentException("Name and NegativeEffect cannot be null");
        }
        effectMap.put(name.toLowerCase(), negativeEffect);
    }

    @Override
    public void unRegisterNegativeEffect(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        effectMap.remove(name.toLowerCase());

        players.entrySet().removeIf(entry -> {
            NegativeEffect appliedEffect = entry.getValue();
            return appliedEffect.getName().equalsIgnoreCase(name);
        });
    }

}
