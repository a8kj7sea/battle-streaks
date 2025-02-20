package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.impl.PlayerEffectAppliedEvent;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerEffectAppliedListener extends PluginListener {

    public PlayerEffectAppliedListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        
    }

    @EventHandler
    public void onPlayerEffectApplied(PlayerEffectAppliedEvent event) {
        Player player = event.getPlayer();
        event.getEffect().apply(player);
    }

}
