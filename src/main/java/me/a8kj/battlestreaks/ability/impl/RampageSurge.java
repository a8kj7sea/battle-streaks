package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.a8kj.battlestreaks.ability.AbilityBase;

public class RampageSurge extends AbilityBase {
    public RampageSurge() {
        super("rampage_surge");
    }

    private int cooldown = 10;

    @Override
    public void activate(Player player) {
        if (isReady(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1)); // Increase Strength
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1)); // Increase Speed
            player.setHealth(player.getHealth() + 6); // Heal 3 hearts
            cooldown = 10;
        }
    }

    @Override
    public void deactivate(Player player) {
        System.out.println("Rampage Surge deactivated.");
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
        return "Boost your strength and speed while healing 3 hearts instantly, every 10 seconds.";
    }
}
