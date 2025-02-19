package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class DashOfFury extends AbilityBase {

    private final PluginFacade plugin;
    private BukkitTask bukkitTask;

    public DashOfFury(String name, CooldownTime cooldownTime, PluginFacade plugin) {
        super(name, "Dash forward every 30 seconds, giving you a burst of speed", cooldownTime);
        this.plugin = plugin;
    }

    @Override
    public void activate(Player player) {
        // Dash forward
        Vector direction = player.getLocation().getDirection().normalize();
        double dashSpeed = 1.5; // Adjust speed as needed
        player.setVelocity(direction.multiply(dashSpeed));
        new PlayerActionBar("&bDASH!!!!!!!!!!").execute(player);
        // Play dash sound
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 1.2f);

        // Apply blindness effect to nearby players
        player.getNearbyEntities(5, 5, 5).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(target -> target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1)));

        // Particle effect
        bukkitTask = new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks > 10) { // 10 ticks = 0.5 seconds
                    cancel();
                    return;
                }
                player.getWorld().spawnParticle(Particle.SONIC_BOOM, player.getLocation(), 10, 0.2, 0.2, 0.2, 0.01);
                ticks++;
            }
        }.runTaskTimer(plugin.getPlugin(), 0, 1);
    }

    @Override
    public void deactivate(Player player) {
        if (bukkitTask != null && !bukkitTask.isCancelled()) {
            bukkitTask.cancel();
            bukkitTask = null;
        }
    }
}
