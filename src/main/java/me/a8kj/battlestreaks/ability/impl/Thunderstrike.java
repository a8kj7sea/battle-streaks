package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

import java.util.List;

public class Thunderstrike extends AbilityBase {

    private static final double RADIUS = 20.0; // Radius of the AoE effect
    private static final int DAMAGE = 6; // Damage dealt to players (3 hearts = 6 points)
    private static final int STUN_DURATION = 60; // Duration of the slowness effect in ticks (3 seconds)
    private static final int NAUSEA_DURATION = 60; // Duration of the nausea effect in ticks (3 seconds)

    public Thunderstrike(String name, CooldownTime cooldownTime, int maxStreaks, int minStreaks) {
        super(name,
                "Summon a lightning bolt every 200 seconds, dealing 3 hearts of true damage and stunning all players within a 20-block radius.",
                cooldownTime, maxStreaks, minStreaks);
    }

    @Override
    public void activate(Player player) {
        // Get the player's location
        Location location = player.getLocation();

        // Summon a lightning bolt at the player's location
        player.getWorld().strikeLightning(location);

        // Find all players in the radius
        List<Entity> nearbyEntities = player.getNearbyEntities(RADIUS, RADIUS, RADIUS);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player && entity != player) {
                Player targetPlayer = (Player) entity;

                // Deal true damage to the player
                targetPlayer.damage(DAMAGE, player);

                // Apply slowness effect
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, STUN_DURATION, 1)); // Slowness
                                                                                                             // II

                // Apply nausea effect
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, NAUSEA_DURATION, 0)); // Nausea
            }
        }

        // Optional: create a knockback effect
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player && entity != player) {
                Player targetPlayer = (Player) entity;
                Vector direction = targetPlayer.getLocation().subtract(location).toVector().normalize();
                targetPlayer.setVelocity(direction.multiply(-2.4).setY(0.5)); // Knockback upwards
            }
        }

        new PlayerActionBar("&1You summoned a Thunderstrike!").execute(player);
    }

    @Override
    public void deactivate(Player player) {
        // No specific deactivation logic needed for this ability
        // However, you can add any cleanup or state management here if needed
    }
}
