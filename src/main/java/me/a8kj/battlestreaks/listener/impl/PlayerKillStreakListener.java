package me.a8kj.battlestreaks.listener.impl;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import lombok.NonNull;
import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerKillStreakListener extends PluginListener {

    public PlayerKillStreakListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        
    }

    @EventHandler
    public void onPlayerKillStreak(PlayerKillStreakEvent event) {
        Player player = event.getPlayer();
        KillStreakStatus status = event.getStatus();
        final int streaks = event.getStreaks();

        PlayerAbilityManager playerAbilityManager = this.getPluginFacade().getPlayerAbilityManager();

        if (status == KillStreakStatus.ACHIEVED || status == KillStreakStatus.LOST) {
            getAbilityNameByStreaks(streaks).flatMap(name -> getAbilityManager().getAbility(name))
                    .ifPresent(ability -> playerAbilityManager.replaceAbility(player, ability));
        }
    }

    private Optional<String> getAbilityNameByStreaks(int streaks) {
        return getAbilityManager().getAbilities().values().stream()
                .filter(a -> streaks >= a.getMinStreaks() && streaks <= a.getMaxStreaks())
                .map(AbilityBase::getName)
                .filter(name -> name != null && !name.isEmpty()) // Ensure name is valid
                .findFirst();
    }
}
