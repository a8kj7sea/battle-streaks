package me.a8kj.battlestreaks.listener.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import lombok.NonNull;
import me.a8kj.battlestreaks.ability.Ability;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerAbilityActivateEvent;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.manager.CooldownManager;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerActiveListener extends PluginListener {

    public PlayerActiveListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    private static CooldownManager abilitiesCooldown = new CooldownManager();

    @EventHandler
    public void onPlayerActiveHisAbility(PlayerAbilityActivateEvent event) {
        Player player = event.getPlayer();
        if (!getPlayerAbilityManager().hasAbility(player))
            return;

        getPlayerAbilityManager().getAllAbilities(player).forEach(name -> {
            if (abilitiesCooldown.isOnCooldown(player.getUniqueId(), name)) {
                new PlayerActionBar("&bYou are on cooldown").execute(player);
                return;
            }

            getPlayerAbilityManager().activateAbility(player, name);
            abilitiesCooldown.start(player.getUniqueId(), name, 0, 0);
            new PlayerActionBar("&6Your ability has been enabled!").execute(player);
        });

    }

}
