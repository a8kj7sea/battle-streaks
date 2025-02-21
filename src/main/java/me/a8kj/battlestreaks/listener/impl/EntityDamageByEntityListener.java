package me.a8kj.battlestreaks.listener.impl;

import java.util.Set;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class EntityDamageByEntityListener extends PluginListener {
    private final Map<UUID, Integer> consecutiveHits = new HashMap<>();

    public EntityDamageByEntityListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))
            return;

        UUID playerId = player.getUniqueId();
        Set<UUID> playersWithChargedStrike = getPluginFacade().getPlayerInChargeAttack();

        if (!playersWithChargedStrike.contains(playerId))
            return;
        
        int hits = consecutiveHits.getOrDefault(playerId, 0);
        if (hits <= 2) {
            consecutiveHits.put(playerId, hits + 1);
        } else {
            double randomMultiplier = 1 + (0.2 + new Random().nextDouble() * 0.3);
            event.setDamage(event.getDamage() * randomMultiplier);
            consecutiveHits.put(playerId, 0); // Reset after Charged Strike
            getPluginFacade().removePlayerFromChargeAttack(player); // Remove player from charge attack after Charged
                                                                    // Strike
            new PlayerActionBar("&6You landed a Charged Strike with increased damage!").execute(player);
        }
    }
}
