package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.a8kj.battlestreaks.ability.AbilityBase;

public class BlindingBurst extends AbilityBase {
    public BlindingBurst() {
        super("blinding_burst");
    }

    private int cooldown = 60;

    @Override
    public void activate(Player player) {
        if (isReady(player)) {
            // Get all entities around the player in a specific radius
            for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                }
            }
            cooldown = 60;
        }
    }

    @Override
    public void deactivate(Player player) {
        System.out.println("Blinding Burst deactivated.");
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
        return "Unleash an AoE explosion that blinds all nearby enemies every 60 seconds.";
    }
}
