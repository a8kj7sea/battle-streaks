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
        Player victim = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if (victim == null || killer == null)
            return;

        final int victimStreaks = getStreaks(victim);
        final int victimLives = getLives(victim);

        final int killerStreaks = getStreaks(killer);
        final int killerLives = getLives(killer);

        // if players not in lives mode then players are default so remove streaks or
        // add it without problems
        if (!PluginFacade.getPlayersInLivesMode().contains(killer.getUniqueId())
                || !PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
            removeData(victim, PlayerDataType.STREAKS, 1);
            addData(killer, PlayerDataType.STREAKS, 1);

            new PlayerKillStreakEvent(killer, killerStreaks + 1, KillStreakStatus.ACHIEVED).callEvent();
            new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();

            if (killerStreaks + 1 <= 4) {
                victim.getWorld().dropItemNaturally(victim.getEyeLocation(),
                        new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                                .setAmount(1)
                                .setDisplayName("&4Kill Mark")
                                .setLore("&7A mark of death, infused",
                                        "&7with dark energy.",
                                        "&cCannot be used while",
                                        "&cyou are in the lives system!")
                                .build());
            }
            if ((killerStreaks + 1) % 4 == 0 && killerStreaks + 1 < 4) {
                int toDrop = (int) Math.floor(killerStreaks / 4);
                victim.getWorld().dropItemNaturally(victim.getEyeLocation(),
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

        // if vicitm streaks - 1 = 0 then enter him to lives mode and call event to
        // disable abilites and remove them and remove poitions
        if (victimStreaks - 1 <= 0 && !PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
            new PlayerActionBar(getMessage("enter-livesmode")).execute(victim);
            this.getPluginFacade().addPlayerToLivesMode(victim);
            setData(victim, PlayerDataType.STREAKS, 0);
            setData(victim, PlayerDataType.LIVES, 5);
            new PlayerLivesEvent(killer, 5, null).callEvent();
        }

        // if victim in lives mode and lost 1 lives after death
        if (PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
            removeData(victim, PlayerDataType.LIVES, 1);
            new PlayerLivesEvent(victim, victimLives - 1, LivesStatus.LOST).callEvent();
        }

        // if killer in lives mode and got one kill add lives
        if (PluginFacade.getPlayersInLivesMode().contains(killer.getUniqueId())) {
            addData(killer, PlayerDataType.LIVES, 1);
            new PlayerLivesEvent(killer, killerLives + 1, LivesStatus.ACHIEVED).callEvent();

            // if killer in lives mode and has 5 or greater remove him from lives mode and
            // say to him
            if (killerLives + 1 > 5) {
                getPluginFacade().removePlayerFromLivesMode(killer);
                removePlayerEffects(killer);
                setData(victim, PlayerDataType.STREAKS, 0);
                new PlayerActionBar(getMessage("leave-livesmode")).execute(victim);
                setData(killer, PlayerDataType.LIVES, 5);
            }
        }

    }

    // if (newKillerStreaks % 4 == 0) {

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
