package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.impl.PlayerAbilityActivateEvent;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerInteractListener extends PluginListener {

    public PlayerInteractListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    @EventHandler
    public void onPlayerActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking()
                && (event.getAction() == Action.RIGHT_CLICK_AIR ||
                        event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            new PlayerAbilityActivateEvent(player).callEvent();
        }
    }

}
