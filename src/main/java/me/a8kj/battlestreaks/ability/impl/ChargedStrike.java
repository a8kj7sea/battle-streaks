package me.a8kj.battlestreaks.ability.impl;

import org.bukkit.entity.Player;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class ChargedStrike extends AbilityBase {

    private final PluginFacade pluginFacade;

    public ChargedStrike(String name, CooldownTime cooldownTime, int maxStreaks, int minStreaks,
            PluginFacade pluginFacade) {
        super(name, "Perform a charged attack every 30 seconds, increasing your damage output", cooldownTime,
                maxStreaks, minStreaks);
        this.pluginFacade = pluginFacade;
    }

    @Override
    public void activate(Player player) {
        this.pluginFacade.getPlayerAbilityManager().registerAbility(player, "charged_strike");
        new PlayerActionBar("&6You have activated Charged Strike!").execute(player);
    }

    @Override
    public void deactivate(Player player) {
        this.pluginFacade.getPlayerAbilityManager().removeAbility(player);
        new PlayerActionBar("&4You have deactivated Charged Strike!").execute(player);
    }
}
