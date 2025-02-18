package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import lombok.NonNull;
import me.a8kj.battlestreaks.ability.impl.DashOfFury;
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
            applyKillstreakAbility(player, playerAbilityManager, streaks);
        }
    }

    private void applyKillstreakAbility(Player player, PlayerAbilityManager manager, int streaks) {
        if (streaks >= 1 && streaks < 3) {
            manager.registerAbility(player, new DashOfFury());
            player.sendMessage("You have taken dash ability!");
        } else if (streaks >= 3 && streaks < 5) {
            // msh7oon
        } else if (streaks >= 5 && streaks < 7) {
            // aoe
        } else if (streaks >= 7 && streaks < 9) {
            // + hearts and heal
        } else if (streaks >= 9 && streaks < 12) {
            // earthquake
        } else if (streaks >= 12) {
            // strike
        }

        if (streaks % 4 == 0) {
            // add kill mark
        }
    }

}
