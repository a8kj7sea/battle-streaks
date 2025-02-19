package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerKillStreakListener extends PluginListener {

    public PlayerKillStreakListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        this.register();
    }

    @EventHandler
    public void onPlayerKillStreak(PlayerKillStreakEvent event) {
        Player player = event.getPlayer();
        KillStreakStatus status = event.getStatus();
        final int streaks = event.getStreaks();

        PlayerAbilityManager playerAbilityManager = this.getPluginFacade().getPlayerAbilityManager();

        if (status == KillStreakStatus.ACHIEVED || status == KillStreakStatus.LOST) {
            // applyKillstreakAbility(player, playerAbilityManager, streaks);
            playerAbilityManager.registerAbility(player, getAbilityManager().getAbility("dash"));
        }
    }

    public void applyKillstreakAbility(Player player, PlayerAbilityManager manager, int streaks) {
        ConfigurationSection abilitiesSection = getDefaultConfig().getYamConfiguration()
                .getConfigurationSection("abilities");

        if (abilitiesSection == null) {
            player.sendMessage("Error: No abilities found in config!");
            return;
        }

        for (String key : abilitiesSection.getKeys(false)) {
            int minStreak = abilitiesSection.getInt(key + ".minStreak");
            int maxStreak = abilitiesSection.getInt(key + ".maxStreak");
            String abilityName = abilitiesSection.getString(key + ".ability");
            String message = abilitiesSection.getString(key + ".message", "You have unlocked a new ability!");

            if (streaks >= minStreak && streaks < maxStreak) {

                manager.replaceAbility(player, abilityName.toLowerCase());

                new PlayerActionBar(message).execute(player);
                return;
            }
        }
    }

}
