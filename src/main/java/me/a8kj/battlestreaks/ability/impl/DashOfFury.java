package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

public class DashOfFury extends AbilityBase {
    public DashOfFury() {
        super("dash");
    }

    private int cooldown = 30;

    @Override
    public void activate(Player player) {
        if (isReady(player)) {
            // player.moveForward(); // Add your movement logic here
            cooldown = 30;
        }
    }

    @Override
    public void deactivate(Player player) {
        System.out.println("Dash of Fury deactivated.");
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
        return "Dash forward every 30 seconds, giving you a burst of speed.";
    }

    @Override
    public CooldownTime getCooldownTime() {
        return new CooldownTime(0,30);
    }
}
