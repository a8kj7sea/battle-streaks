package me.a8kj.battlestreaks.listener.impl;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.impl.PlayerEffectAppliedEvent;
import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerConnectionListeners extends PluginListener {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public PlayerConnectionListeners(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);

    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        pool.execute(() -> {
            // This can handle any initial data checks before the player joins
            if (!getDataConfig().contains(event.getPlayer())) {
                setDefaultData(event.getPlayer());
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        pool.execute(() -> {
            if (!getDataConfig().contains(event.getPlayer())) {
                setDefaultData(event.getPlayer());
            }

            int lives = getDataConfig().getData(event.getPlayer(), PlayerDataType.LIVES, 6);
            if (lives > 4)
                return;

            PluginFacade.getPlayersInLivesMode().add(event.getPlayer().getUniqueId());

            Bukkit.getScheduler().runTask(getPluginFacade().getPlugin(), () -> {
                Optional<NegativeEffect> effect = getEffectManager().getEffectMap().values().stream()
                        .filter(e -> e.getRequiredLives() == lives)
                        .findFirst();
                effect.ifPresent(e -> new PlayerEffectAppliedEvent(event.getPlayer(), e).callEvent());
            });
        });
    }

    private void setDefaultData(org.bukkit.entity.Player player) {
        getDataConfig().setData(player, PlayerDataType.LIVES, 6);
        getDataConfig().setData(player, PlayerDataType.STREAKS, 0);
        getDataConfig().setData(player, PlayerDataType.KILL_MARKS, 0);
        getDataConfig().setData(player, PlayerDataType.ABILITY, "NONE");
    }
}
