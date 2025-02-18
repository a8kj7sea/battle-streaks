package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

public class ChargedStrike extends AbilityBase {
    public ChargedStrike() {
        super("charged_strike");
    }

    private int cooldown = 30;

    @Override
    public void activate(Player player) {
        if (isReady(player)) {
            // player.performChargedAttack(); // Add your attack logic here
            cooldown = 30;
        }
    }

    @Override
    public void deactivate(Player player) {
        System.out.println("Charged Strike deactivated.");
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
        return "Perform a charged attack every 30 seconds, increasing your damage output.";
    }

    @Override
    public CooldownTime getCooldownTime() {
        return new CooldownTime(0, 30);
    }
}
