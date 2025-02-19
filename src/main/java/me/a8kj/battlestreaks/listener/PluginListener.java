package me.a8kj.battlestreaks.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.configuration.impl.DefaultConfig;
import me.a8kj.battlestreaks.effect.NegativeEffectManager;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import me.a8kj.battlestreaks.player.PlayerData;
import me.a8kj.battlestreaks.plugin.PluginFacade;

@RequiredArgsConstructor
@Getter
public class PluginListener implements Listener {

    private final @NonNull PluginFacade pluginFacade;

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, pluginFacade.getPlugin());
    }

    public void removePlayerEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public NegativeEffectManager getEffectManager() {
        return this.pluginFacade.getEffectManager();
    }

    public AbilityManager getAbilityManager() {
        return this.getPluginFacade().getAbilityManager();
    }

    public String getMessage(String key) {
        return pluginFacade.getDefaultConfiguration().getYamConfiguration().getString("messages." + key, "no message");
    }

    public PlayerData getDataConfig() {
        return (PlayerData) this.pluginFacade.getDataConfiguration();
    }

    public DefaultConfig getDefaultConfig() {
        return (DefaultConfig) this.pluginFacade.getDefaultConfiguration();
    }

    public PlayerAbilityManager getPlayerAbilityManager() {
        return this.pluginFacade.getPlayerAbilityManager();
    }
}
