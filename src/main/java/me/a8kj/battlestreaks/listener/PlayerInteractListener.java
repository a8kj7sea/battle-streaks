package me.a8kj.battlestreaks.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.a8kj.battlestreaks.api.player.impl.AbilityActivateEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking()
                && (event.getAction() == Action.RIGHT_CLICK_AIR ||
                        event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            new AbilityActivateEvent(player).callEvent();
        }
    }

}
