package me.a8kj.battlestreaks.effect;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface NegativeEffectManager {
    void applyNegativeEffect(Player player, String effectName);

    boolean contains(Player player);

    void removePlayer(Player player);

    void registerNegativeEffect(String name, NegativeEffect negativeEffect);

    void unRegisterNegativeEffect(String name);

    void unApplyNegativeEffect(Player player, String effectName);

    void addNegativeEffect(String name, NegativeEffect effect);

    Optional<NegativeEffect> getNegativeEffect(String name);

    Optional<NegativeEffect> getNegativeEffectForPlayer(Player player);

    Map<String, NegativeEffect> getEffectMap();

    Map<UUID, NegativeEffect> getPlayers();

    void clear();
}
