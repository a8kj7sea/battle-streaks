package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.Getter;
import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.plugin.PluginFacade;

@Getter
public class RampageSurge extends AbilityBase {

    private final PluginFacade pluginFacade;

    public RampageSurge(String name, CooldownTime cooldownTime, PluginFacade pluginFacade, int maxStreaks,
            int minStreaks) {
        super(name, "Boost your strength and speed while healing 3 hearts instantly.", cooldownTime, maxStreaks,
                minStreaks);
        this.pluginFacade = pluginFacade;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void activate(Player player) {
        // Heal the player for 3 hearts (6 health points)
        player.setHealth(Math.min(player.getHealth() + 6, player.getMaxHealth()));

        // Apply Strength effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 200, 1)); // 10 seconds (200 ticks) of
                                                                                     // Strength II

        // Apply Speed effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1)); // 10 seconds (200 ticks) of Speed II

        new PlayerActionBar("&2You are in Rampage Surge!").execute(player);
    }

    @Override
    public void deactivate(Player player) {
        // Remove potion effects
        player.removePotionEffect(PotionEffectType.STRENGTH);
        player.removePotionEffect(PotionEffectType.SPEED);
        new PlayerActionBar("&cRampage Surge has been deactivated").execute(player);
    }
}
