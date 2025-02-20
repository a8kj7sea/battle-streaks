package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import lombok.Getter;

import org.bukkit.Particle;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.plugin.PluginFacade;

import java.util.List;

@Getter
public class EarthquakeSlam extends AbilityBase {
    private static final double RADIUS = 5.0;
    private static final int DAMAGE = 4;
    private static final int STUN_DURATION = 60;

    private final PluginFacade pluginFacade;

    public EarthquakeSlam(String name, CooldownTime cooldownTime, int maxStreaks, int minStreaks,
            PluginFacade pluginFacade) {
        super(name, "You can slam the ground every 70 seconds, stunning and damaging enemies.", cooldownTime,
                maxStreaks, minStreaks);
        this.pluginFacade = pluginFacade;
    }

    @Override
    public void activate(Player player) {
        Location location = player.getLocation();

        player.setVelocity(new Vector(0, 0.7, 0));

        player.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, location, 2);
        player.getWorld().spawnParticle(Particle.CLOUD, location, 20, 1, 0.5, 1);

        player.getServer().getScheduler().runTaskLater(pluginFacade.getPlugin(), () -> {
            for (int i = 0; i < 10; i++) {
                double angle = Math.random() * Math.PI * 2;
                double x = Math.cos(angle) * RADIUS;
                double z = Math.sin(angle) * RADIUS;
                Location particleLocation = location.clone().add(x, 0, z);
                player.getWorld().spawnParticle(Particle.EXPLOSION, particleLocation, 1);
            }

            List<Entity> nearbyEntities = player.getNearbyEntities(RADIUS, RADIUS, RADIUS);
            for (Entity entity : nearbyEntities) {
                if (entity instanceof LivingEntity && entity != player) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    livingEntity.damage(DAMAGE, player);
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, STUN_DURATION, 10));

                    Vector direction = livingEntity.getLocation().subtract(location).toVector().normalize();
                    livingEntity.setVelocity(direction.multiply(0.5).setY(0.3));
                }
            }

            new PlayerActionBar("§cYou slammed the ground, causing an earthquake!").execute(player);

        }, 10L);
    }

    @Override
    public void deactivate(Player player) {
    }
}
