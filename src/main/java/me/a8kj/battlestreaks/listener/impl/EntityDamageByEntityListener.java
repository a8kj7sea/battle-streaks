package me.a8kj.battlestreaks.listener.impl;

import java.util.Set;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class EntityDamageByEntityListener extends PluginListener {
    private final Map<UUID, Long> lastChargedAttack = new HashMap<>();
    private static final long COOLDOWN_TIME = 30 * 1000;
    private static final double DAMAGE_MULTIPLIER = 1.3;

    public EntityDamageByEntityListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))
            return;

        UUID playerId = player.getUniqueId();
        Set<UUID> playersWithChargedStrike = getPluginFacade()
                .getPlayerAbilityManager()
                .getPlayersWithAbilityByName("charged_strike");

        if (!playersWithChargedStrike.contains(playerId))
            return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastChargedAttack.getOrDefault(playerId, 0L) >= COOLDOWN_TIME) {
            event.setDamage(event.getDamage() * DAMAGE_MULTIPLIER);
            lastChargedAttack.put(playerId, currentTime);
        }
    }
}
