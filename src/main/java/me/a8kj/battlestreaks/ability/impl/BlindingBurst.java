package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

public class BlindingBurst extends AbilityBase {

    public BlindingBurst(String name, String description, CooldownTime cooldownTime) {
        super(name, description, cooldownTime);
    }

    @Override
    public void activate(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activate'");
    }

    @Override
    public void deactivate(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactivate'");
    }
   
}
