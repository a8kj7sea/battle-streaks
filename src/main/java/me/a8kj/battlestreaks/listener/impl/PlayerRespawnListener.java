package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.manager.NegativeEffectManager;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerRespawnListener extends PluginListener {

    public PlayerRespawnListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (NegativeEffectManager.contains(event.getPlayer())) {
            NegativeEffectManager.applyNegativeEffect(event.getPlayer(),
                    NegativeEffectManager.getNegativeEffectForPlayer(event.getPlayer()).getName().toLowerCase());
        }
    }

}
