package me.a8kj.battlestreaks.effect;

import org.bukkit.entity.Player;

public interface NegativeEffect {
    void apply(Player player);

    void unApply(Player player);

    String getName();

    int getRequiredLives();
}
