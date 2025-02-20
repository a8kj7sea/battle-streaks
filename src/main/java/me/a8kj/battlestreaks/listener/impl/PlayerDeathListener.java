package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent.LivesStatus;
import me.a8kj.battlestreaks.player.PlayerData;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.util.ItemStackBuilder;

public class PlayerDeathListener implements Listener {

    private final PluginFacade pluginFacade;
    @Getter
    private final PlayerData dataConfig;

    public PlayerDeathListener(@NonNull PluginFacade pluginFacade) {
        this.pluginFacade = pluginFacade;
        dataConfig = (PlayerData) pluginFacade.getDataConfiguration();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null || victim == null)
            return; // منع التنفيذ إذا لم يكن هناك قاتل

        handleVictimDeath(victim);
        handleKillerStreak(killer);
    }

    private void handleVictimDeath(Player victim) {
        final int victimStreaks = getDataConfig().getData(victim, PlayerDataType.STREAKS, 0);

        final int victimLives = getDataConfig().getData(victim, PlayerDataType.LIVES, 0);

        if (!PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
            if (victimStreaks <= 1) {
                pluginFacade.addPlayerToLivesMode(victim);
                getDataConfig().setData(victim, PlayerDataType.STREAKS, 0);
                getDataConfig().setData(victim, PlayerDataType.LIVES, 5);
                new PlayerLivesEvent(victim, 5, null).callEvent();
                return;
            }
            dropKillMark(victim, victimStreaks);
            new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();
            getDataConfig().removeData(victim, PlayerDataType.STREAKS, 1);
            new PlayerActionBar("&4&l- 1 Streak!").execute(victim);
        } else {
            if (victimLives <= 1) {
                new PlayerLivesEvent(victim, 0, null).callEvent();
                getDataConfig().setData(victim, PlayerDataType.LIVES, 0);
                return;
            }
            new PlayerLivesEvent(victim, victimLives - 1, LivesStatus.LOST).callEvent();
            getDataConfig().removeData(victim, PlayerDataType.LIVES, 1);
            new PlayerActionBar("&f&l- 1 Live!").execute(victim);
        }
    }

    private void handleKillerStreak(Player killer) {
        final int killerStreaks = getDataConfig().getData(killer, PlayerDataType.STREAKS, 0);
        final int killerLives = getDataConfig().getData(killer, PlayerDataType.LIVES, 0);

        if (!PluginFacade.getPlayersInLivesMode().contains(killer.getUniqueId())) {
            new PlayerKillStreakEvent(killer, killerStreaks + 1, KillStreakStatus.ACHIEVED).callEvent();
            getDataConfig().addData(killer, PlayerDataType.STREAKS, 1);
            new PlayerActionBar("&2&l+ 1 Streak!").execute(killer);
        } else {
            if (killerLives >= 5) {
                pluginFacade.removePlayerFromLivesMode(killer);
                getDataConfig().setData(killer, PlayerDataType.LIVES, 5);
                new PlayerActionBar("&1Wooosh you had left lives mode!").execute(killer);
                return;
            }
            new PlayerLivesEvent(killer, killerLives + 1, LivesStatus.ACHIEVED).callEvent();
            getDataConfig().addData(killer, PlayerDataType.LIVES, 1);
            new PlayerActionBar("&d&l+ 1 Live!").execute(killer);
        }
    }

    private void dropKillMark(Player player, int streaks) {
        int toDrop = Math.max(1, streaks / 4);
        player.getWorld().dropItemNaturally(player.getEyeLocation(),
                new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                        .setAmount(toDrop)
                        .setDisplayName("&4Kill Mark")
                        .setLore("&7A mark of death, infused",
                                "&7with dark energy.",
                                "&cCannot be used while",
                                "&cyou are in the lives system!")
                        .build());
    }

}