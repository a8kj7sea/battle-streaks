package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent.LivesStatus;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerDeathListener extends PluginListener {

    public PlayerDeathListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if (victim == null || killer == null)
            return;

        final int victimStreaks = getStreaks(victim);
        final int victimLives = getLives(victim);

        if (victimStreaks > 0) {
            int killMarks = victimStreaks / 4;

            if (victimStreaks < 4) {
                killMarks = 1;
            }

            new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();
            removeData(victim, PlayerDataType.STREAKS, 1);

        } else {

            if (victimLives - 1 == 0) {
                victim.sendMessage(
                        "Your ability has gone and you are enttred to lives system so becareful and try to get streakks");
                setData(victim, PlayerDataType.LIVES, 5);
            } else {
                new PlayerLivesEvent(victim, victimLives - 1, LivesStatus.LOST).callEvent();
                removeData(victim, PlayerDataType.STREAKS, 1);
            }
        }
    }

    private int getLives(Player player) {
        return getDataConfig().getData(player, PlayerDataType.LIVES, 5);
    }

    private void setData(Player player, PlayerDataType type, int amount) {
        this.getDataConfig().setData(player, type, 0);
    }

    private int getStreaks(Player player) {
        return getDataConfig().getData(player, PlayerDataType.STREAKS, 0);
    }

    private void removeData(Player player, PlayerDataType type, int amount) {
        this.getDataConfig().removeData(player, type, amount);
    }

    private void addData(Player player, PlayerDataType type, int amount) {
        this.getDataConfig().addData(player, type, amount);
    }
}
