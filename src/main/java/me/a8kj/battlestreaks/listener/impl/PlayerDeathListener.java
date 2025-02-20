package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent.LivesStatus;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.util.ItemStackBuilder;

public class PlayerDeathListener extends PluginListener {

    public PlayerDeathListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        this.register();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        

        final int victimStreaks = getStreaks(victim);
        final int victimLives = getLives(victim);
        ///////////////////////////
        final int killerStreaks = getStreaks(killer);
        final int killerLives = getLives(killer);

        if (!PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
            if (victimStreaks - 1 == 0 || victimStreaks == 0) {
                getPluginFacade().addPlayerToLivesMode(victim);
                setData(victim, PlayerDataType.STREAKS, 0);
                setData(victim, PlayerDataType.LIVES, 5);
                new PlayerLivesEvent(victim, 5, null).callEvent();
                
            } else {
                int toDrop = Math.max(1, (int) Math.floor(victimStreaks / 4));
                if (victimStreaks >= 4) {
                    victim.getWorld().dropItemNaturally(victim.getEyeLocation(),
                            new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                                    .setAmount(toDrop)
                                    .setDisplayName("&4Kill Mark")
                                    .setLore("&7A mark of death, infused",
                                            "&7with dark energy.",
                                            "&cCannot be used while",
                                            "&cyou are in the lives system!")
                                    .build());
                    new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();
                    removeData(victim, PlayerDataType.STREAKS, 1);
                    new PlayerActionBar("&4&l- 1 Streak!").execute(victim);
                    
                } else {
                    victim.getWorld().dropItemNaturally(victim.getEyeLocation(),
                            new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                                    .setAmount(toDrop)
                                    .setDisplayName("&4Kill Mark")
                                    .setLore("&7A mark of death, infused",
                                            "&7with dark energy.",
                                            "&cCannot be used while",
                                            "&cyou are in the lives system!")
                                    .build());
                    new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();
                    removeData(victim, PlayerDataType.STREAKS, 1);
                    new PlayerActionBar("&4&l- 1 Streak!").execute(victim);
                    
                }
            }
        } else {
            if (victimLives - 1 == 0 || victimLives == 0) {
                new PlayerLivesEvent(victim, 0, null).callEvent();
                setData(victim, PlayerDataType.LIVES, 0);
                
            }
            if (victimLives <= 5) {
                new PlayerLivesEvent(victim, victimLives - 1, LivesStatus.LOST).callEvent();
                removeData(victim, PlayerDataType.LIVES, 1);
                new PlayerActionBar("&f&l- 1 Live!").execute(victim);
                
            }
        }

        if (!PluginFacade.getPlayersInLivesMode().contains(killer.getUniqueId())) {
            if (killerStreaks >= 0) {
                new PlayerKillStreakEvent(killer, killerStreaks + 1, KillStreakStatus.ACHIEVED).callEvent();
                addData(killer, PlayerDataType.STREAKS, 1);
                new PlayerActionBar("&2&l+ 1 Streak!").execute(killer);
                
            }
        } else {
            if (killerLives + 1 > 5) {
                getPluginFacade().removePlayerFromLivesMode(killer);
                setData(killer, PlayerDataType.LIVES, 5);
                new PlayerActionBar("&1Wooosh you had left lives mode !").execute(killer);
                
            } else {
                new PlayerLivesEvent(killer, killerLives + 1, LivesStatus.ACHIEVED).callEvent();
                addData(killer, PlayerDataType.LIVES, 1);
                new PlayerActionBar("&d&l+ 1 Live!").execute(killer);
                
            }
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
