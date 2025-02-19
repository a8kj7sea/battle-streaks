package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class ChargedStrike extends AbilityBase {

    private final PluginFacade pluginFacade;

    public ChargedStrike(String name, CooldownTime cooldownTime, PluginFacade pluginFacade) {
        super(name, "Perform a charged attack every 30 seconds, increasing your damage output", cooldownTime);
        this.pluginFacade = pluginFacade;
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
