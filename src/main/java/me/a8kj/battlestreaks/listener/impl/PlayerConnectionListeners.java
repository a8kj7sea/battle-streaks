package me.a8kj.battlestreaks.listener.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerConnectionListeners extends PluginListener {

    public PlayerConnectionListeners(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    private final ExecutorService pool = Executors.newCachedThreadPool();

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        pool.execute(() -> {
            if (!getDataConfig().contains(event.getPlayer())) {
                getDataConfig().setData(event.getPlayer(), PlayerDataType.LIVES, 5);
                getDataConfig().setData(event.getPlayer(), PlayerDataType.STREAKS, 0);
            }
        });
    }

}
