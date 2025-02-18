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
        // Use an async task to handle player data on login
        pool.execute(() -> {
            // Check if the player's data is already saved
            if (!getDataConfig().contains(event.getPlayer())) {
                // Fetch default values from the configuration file
                int defaultLives = getDefaultConfig().getConfigurationFile().getInt("default-data.lives", 5);
                int defaultStreaks = getDefaultConfig().getConfigurationFile().getInt("default-data.streaks", 0);
                int defaultKillMarks = getDefaultConfig().getConfigurationFile().getInt("default-data.kill_marks", 0);
                String defaultAbility = getDefaultConfig().getConfigurationFile().getString("default-data.ability",
                        "NONE");

                // Set the default data for the player
                getDataConfig().setData(event.getPlayer(), PlayerDataType.LIVES, defaultLives);
                getDataConfig().setData(event.getPlayer(), PlayerDataType.STREAKS, defaultStreaks);
                getDataConfig().setData(event.getPlayer(), PlayerDataType.KILL_MARKS, defaultKillMarks);
                getDataConfig().setData(event.getPlayer(), PlayerDataType.ABILITY, defaultAbility);
            } else {
                // Check if the player has 4 or fewer lives and add them to Lives Mode
                if (getDataConfig().getData(event.getPlayer(), PlayerDataType.LIVES, 4) <= 4) {
                    // Ensure this logic is correct to manage players in lives mode
                    PluginFacade.getPlayersInLivesMode().add(event.getPlayer().getUniqueId());
                }
            }
        });
    }

}
