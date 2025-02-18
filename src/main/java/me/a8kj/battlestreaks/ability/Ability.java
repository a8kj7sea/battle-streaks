package me.a8kj.battlestreaks.ability;

import org.bukkit.entity.Player;

public interface Ability {

    void activate(Player player);

    void deactivate(Player player);

    boolean isReady(Player player);

    void update(Player player);

    String getName();

    String getDescription();
}
