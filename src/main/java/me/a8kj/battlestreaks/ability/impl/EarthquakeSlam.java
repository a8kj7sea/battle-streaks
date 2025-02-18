package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.a8kj.battlestreaks.ability.AbilityBase;

public class EarthquakeSlam extends AbilityBase {
    public EarthquakeSlam() {
        super("earthquake_slam");
    }

    private int cooldown = 70;

    @Override
    public void activate(Player player) {
        if (isReady(player)) {
            // Players in a certain radius will be stunned and damaged
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 100, 1));
                    target.damage(5);
                }
            }
            cooldown = 70;
        }
    }

    @Override
    public void deactivate(Player player) {
        System.out.println("Earthquake Slam deactivated.");
    }

    @Override
    public boolean isReady(Player player) {
        return cooldown == 0;
    }

    @Override
    public void update(Player player) {
        if (cooldown > 0) {
            cooldown--;
        }
    }

    @Override
    public String getDescription() {
        return "Strike the ground to cause a shockwave that stuns and damages all nearby enemies.";
    }
}
