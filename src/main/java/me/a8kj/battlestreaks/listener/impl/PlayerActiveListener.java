package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import lombok.NonNull;
import me.a8kj.battlestreaks.ability.Ability;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerAbilityActivateEvent;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.manager.CooldownManager;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerActiveListener extends PluginListener {

    public PlayerActiveListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        this.register();
    }

    private static CooldownManager abilitiesCooldown = new CooldownManager();

    @EventHandler
    public void onPlayerActiveHisAbility(PlayerAbilityActivateEvent event) {
        Player player = event.getPlayer();
        if (!getPlayerAbilityManager().hasAbility(player)) {
            player.sendMessage("[debug] you don't have ability");
            return;
        }

        getPlayerAbilityManager().getAllAbilities(player).forEach(name -> {
            if (abilitiesCooldown.isOnCooldown(player.getUniqueId(), name)) {
                new PlayerActionBar("&bYou are on cooldown").execute(player);
                return;
            }

            getPlayerAbilityManager().activateAbility(player, name);
            Ability ability = getAbilityManager().getAbility(name);
            CooldownTime cooldownTime = ability.getCooldownTime();
            abilitiesCooldown.start(player.getUniqueId(), name, cooldownTime.getMinutes(), cooldownTime.getSeconds());
            new PlayerActionBar("&6Your ability has been enabled!").execute(player);
        });

    }

}
