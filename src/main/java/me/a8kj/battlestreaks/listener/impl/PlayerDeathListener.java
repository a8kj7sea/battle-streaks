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

        // إذا لم يكن اللاعبان في وضع الحياة
        if (!PluginFacade.getPlayersInLivesMode().contains(killer.getUniqueId())
                && !PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
            removeData(victim, PlayerDataType.STREAKS, 1);
            addData(killer, PlayerDataType.STREAKS, 1);

            new PlayerKillStreakEvent(killer, killerStreaks + 1, KillStreakStatus.ACHIEVED).callEvent();
            new PlayerKillStreakEvent(victim, victimStreaks - 1, KillStreakStatus.LOST).callEvent();

            if (victimStreaks - 1 <= 0) {
                new PlayerActionBar("enter-livesmode").execute(victim);
                this.getPluginFacade().addPlayerToLivesMode(victim);
                setData(victim, PlayerDataType.STREAKS, 0);
                setData(victim, PlayerDataType.LIVES, 5);
                new PlayerLivesEvent(victim, 5, null).callEvent();
                return;
            }

            // إذا كان القاتل في وضع الحياة وحصل على قتل
            if (PluginFacade.getPlayersInLivesMode().contains(killer.getUniqueId())) {
                addData(killer, PlayerDataType.LIVES, 1);
                new PlayerLivesEvent(killer, killerLives + 1, LivesStatus.ACHIEVED).callEvent();

                // إذا كان القاتل لديه 5 أو أكثر من الحياة
                if (killerLives + 1 > 5) {
                    getPluginFacade().removePlayerFromLivesMode(killer);
                    removePlayerEffects(killer);
                    setData(killer, PlayerDataType.LIVES, 5);
                    new PlayerActionBar("leave-livesmode").execute(killer);
                }
            }

            // إذا كان الضحية في وضع الحياة وخسر حياة
            if (PluginFacade.getPlayersInLivesMode().contains(victim.getUniqueId())) {
                removeData(victim, PlayerDataType.LIVES, 1);
                new PlayerLivesEvent(victim, victimLives - 1, LivesStatus.LOST).callEvent();
                return;
            }

            // إضافة علامات القتل
            int toDrop = (int) Math.floor(killerStreaks / 4);
            if (toDrop > 0) {
                victim.getWorld().dropItemNaturally(victim.getEyeLocation(),
                        new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                                .setAmount(toDrop)
                                .setDisplayName("&4Kill Mark")
                                .setLore("&7A mark of death, infused",
                                        "&7with dark energy.",
                                        "&cCannot be used while",
                                        "&cyou are in the lives system!")
                                .build());
            } else {
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
