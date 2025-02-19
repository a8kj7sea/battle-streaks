package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.Particle;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

import java.util.List;

public class EarthquakeSlam extends AbilityBase {
    private static final double RADIUS = 5.0; // Radius of the slam effect
    private static final int DAMAGE = 4; // Damage dealt to enemies
    private static final int STUN_DURATION = 60; // Stun duration in ticks (3 seconds)

    public EarthquakeSlam(String name, CooldownTime cooldownTime) {
        super(name, "You can slam the ground every 70 seconds, stunning and damaging enemies.", cooldownTime);
    }

    @Override
    public void activate(Player player) {
        // Get the player's location
        Location location = player.getLocation();

        // Create a shockwave effect using particles
        for (int i = 0; i < 10; i++) {
            double angle = Math.random() * Math.PI * 2;
            double x = Math.cos(angle) * RADIUS;
            double z = Math.sin(angle) * RADIUS;
            Location particleLocation = location.clone().add(x, 0, z);
            player.getWorld().spawnParticle(Particle.EXPLOSION, particleLocation, 1);
        }

        // Find all entities in the radius
        List<Entity> nearbyEntities = player.getNearbyEntities(RADIUS, RADIUS, RADIUS);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && entity != player) {
                LivingEntity livingEntity = (LivingEntity) entity;

                // Deal damage to the entity
                livingEntity.damage(DAMAGE, player);

                // Apply stun effect (slowness)
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, STUN_DURATION, 10)); // 3
                                                                                                              // seconds
                                                                                                              // of
                                                                                                              // slowness
                                                                                                              // II

                // Optional: create a knockback effect
                Vector direction = livingEntity.getLocation().subtract(location).toVector().normalize();
                livingEntity.setVelocity(direction.multiply(-1).setY(0.5)); // Knockback upwards
            }
        }

        // Optional notification to the player
        player.sendMessage("You slammed the ground, causing an earthquake!");
    }

    @Override
    public void deactivate(Player player) {
        // No specific deactivation logic needed for this ability
        // However, you can add any cleanup or state management here if needed
    }
}
