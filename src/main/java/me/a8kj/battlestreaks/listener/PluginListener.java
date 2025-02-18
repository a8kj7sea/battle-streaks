package me.a8kj.battlestreaks.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.configuration.impl.DefaultConfig;
import me.a8kj.battlestreaks.player.PlayerData;
import me.a8kj.battlestreaks.plugin.PluginFacade;

@RequiredArgsConstructor
@Getter
public class PluginListener implements Listener {

    private final @NonNull PluginFacade pluginFacade;

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, pluginFacade.getPlugin());
    }

    public PlayerData getDataConfig() {
        return (PlayerData) this.pluginFacade.getDataConfiguration();
    }

    public DefaultConfig getDefaultConfig() {
        return (DefaultConfig) this.pluginFacade.getDefaultConfiguration();
    }

    public AbilityManager getAbilityManager() {
        return this.pluginFacade.getAbilityManager();
    }
}
