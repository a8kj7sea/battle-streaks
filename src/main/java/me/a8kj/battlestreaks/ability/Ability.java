package me.a8kj.battlestreaks.ability;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.cooldown.CooldownTime;

public interface Ability {

    void activate(Player player);

    void deactivate(Player player);

    boolean isReady(Player player);

    void update(Player player);

    CooldownTime getCooldownTime();

    String getName();

    String getDescription();
}
