package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.a8kj.battlestreaks.ability.AbilityBase;

public class Thunderstrike extends AbilityBase {
    public Thunderstrike() {
        super("thunderstrike");
    }

    private int cooldown = 200;

    @Override
    public void activate(Player player) {
        if (isReady(player)) {
            for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    target.damage(6);
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 100, 1));
                }
            }
            // You can also make a lightning effect here if needed
            cooldown = 200;
        }
    }

    @Override
    public void deactivate(Player player) {
        System.out.println("Thunderstrike deactivated.");
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
        return "Summon a powerful lightning strike to deal damage and stun enemies in a 20-block radius.";
    }
}
