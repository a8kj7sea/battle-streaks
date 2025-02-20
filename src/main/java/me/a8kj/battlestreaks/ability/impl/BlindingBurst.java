package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

import java.util.List;

public class BlindingBurst extends AbilityBase {

    public BlindingBurst(String name, CooldownTime cooldownTime, int maxStreaks, int minStreaks) {
        super(name, "Unleash an AoE explosion that blinds all nearby enemies every 60 seconds.", cooldownTime,
                maxStreaks, minStreaks);
    }

    private static final double RADIUS = 10.0; // Radius of the AoE effect
    private static final int STUN_DURATION = 60; // Duration of the blindness effect in ticks (3 seconds)

    @Override
    public void activate(Player player) {
        // Get the player's location
        Location location = player.getLocation();

        // Play explosion sound at the player's location
        player.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

        // Find all entities in the radius
        List<Entity> nearbyEntities = player.getNearbyEntities(RADIUS, RADIUS, RADIUS);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && entity != player) {
                LivingEntity livingEntity = (LivingEntity) entity;

                // Apply blindness effect
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, STUN_DURATION, 0)); // 3
                                                                                                              // seconds
                                                                                                              // of
                                                                                                              // blindness
            }
        }

        // Optional: create a knockback effect
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && entity != player) {
                LivingEntity livingEntity = (LivingEntity) entity;
                Vector direction = livingEntity.getLocation().subtract(location).toVector().normalize();
                livingEntity.setVelocity(direction.multiply(-1).setY(0.5)); // Knockback upwards
            }
        }

        // Optional notification to the player
        new PlayerActionBar("&eYou unleashed a Blinding Burst!").execute(player);
    }

    @Override
    public void deactivate(Player player) {
        // No specific deactivation logic needed for this ability
        // However, you can add any cleanup or state management here if needed
    }
}
