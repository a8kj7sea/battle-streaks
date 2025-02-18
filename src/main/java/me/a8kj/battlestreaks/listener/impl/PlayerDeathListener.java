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

        // Handle the victim's death
        if (victimStreaks > 0) {
            // Calculate kill marks to leave behind
            int killMarks = victimStreaks / 4;
            if (victimStreaks < 4) {
                killMarks = 1;
            }

            // Decrease the victim's streak
            new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();
            removeData(victim, PlayerDataType.STREAKS, 1);

            // Add kill marks
            addData(victim, PlayerDataType.KILL_MARKS, killMarks);

        } else {
            // If streaks are 0, handle the life system
            if (victimLives - 1 == 0) {
                victim.sendMessage(
                        "Your ability has gone and you are entered into the lives system, be careful, and try to get streaks.");
                setData(victim, PlayerDataType.LIVES, 5); // Reset lives
            } else {
                new PlayerLivesEvent(victim, victimLives - 1, LivesStatus.LOST).callEvent();
                removeData(victim, PlayerDataType.STREAKS, 1);
            }
        }

        // Handle the killer's reward (gain killstreak)
        final int killerStreaks = getStreaks(killer);
        int newKillerStreaks = killerStreaks + 1; // Increment the killer's streak by 1 for the kill

        // If the killer has gained a killstreak, trigger killstreak event
        new PlayerKillStreakEvent(killer, newKillerStreaks, KillStreakStatus.ACHIEVED).callEvent();
        setData(killer, PlayerDataType.STREAKS, newKillerStreaks); // Update the killer's streak data

        // If the killer reaches a certain number of kills (e.g., 4), give them a kill
        // mark
        if (newKillerStreaks % 4 == 0) {
            addData(killer, PlayerDataType.KILL_MARKS, 1);
        }
    }

    private int getLives(Player player) {
        return getDataConfig().getData(player, PlayerDataType.LIVES, 5);
    }

    private int getStreaks(Player player) {
        return getDataConfig().getData(player, PlayerDataType.STREAKS, 0);
    }

    private void setData(Player player, PlayerDataType type, int amount) {
        this.getDataConfig().setData(player, type, amount);
    }

    private void removeData(Player player, PlayerDataType type, int amount) {
        this.getDataConfig().removeData(player, type, amount);
    }

    private void addData(Player player, PlayerDataType type, int amount) {
        this.getDataConfig().addData(player, type, amount);
    }
}
