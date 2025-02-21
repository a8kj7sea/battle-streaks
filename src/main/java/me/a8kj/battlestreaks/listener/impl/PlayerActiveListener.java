package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.NonNull;
import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerAbilityActivateEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.manager.CooldownManager;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import java.util.Optional;

public class PlayerActiveListener extends PluginListener {

    private static final CooldownManager abilitiesCooldown = new CooldownManager();

    public PlayerActiveListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);

    }

    @EventHandler
    public void onPlayerActiveHisAbility(PlayerAbilityActivateEvent event) {
        Player player = event.getPlayer();

        if (PluginFacade.getPlayersInLivesMode().contains(player.getUniqueId()))
            return;

        if (getDataConfig().getData(player, PlayerDataType.STREAKS, 0) >= 1
                && getPlayerAbilityManager().getAbility(player).isEmpty()) {
            new PlayerKillStreakEvent(player, getDataConfig().getData(player, PlayerDataType.STREAKS, 0),
                    KillStreakStatus.ACHIEVED).callEvent();
        }

        if (!getPlayerAbilityManager().hasAbility(player)) {
            return;
        }

        Optional<AbilityBase> optionalAbility = getPlayerAbilityManager().getAbility(player);

        if (optionalAbility.isEmpty()) {
            player.sendMessage("[debug] Your ability is invalid!");
            return;
        }

        AbilityBase ability = optionalAbility.get();
        String abilityName = ability.getName();

        if (abilitiesCooldown.isOnCooldown(player.getUniqueId(), abilityName)) {
            new PlayerActionBar("&bYou are on cooldown").execute(player);
            return;
        }

        getPlayerAbilityManager().activatePlayerAbility(player);
        CooldownTime cooldownTime = ability.getCooldownTime();
        abilitiesCooldown.start(player.getUniqueId(), abilityName, cooldownTime.getMinutes(),
                cooldownTime.getSeconds());

        new BukkitRunnable() {
            @Override
            public void run() {
                // Only send the message if the player still has the ability they activated
                Optional<AbilityBase> currentAbility = getPlayerAbilityManager().getAbility(player);
                if (currentAbility.isPresent() && currentAbility.get().getName().equals(abilityName)) {
                    new PlayerActionBar("&aYou can use your ability now!").execute(player);
                }
            }
        }.runTaskLater(getPluginFacade().getPlugin(),
                cooldownTime.getMinutes() * 20 * 60 + cooldownTime.getSeconds() * 20);
    }
}
