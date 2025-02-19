package me.a8kj.battlestreaks.listener.impl;

import java.util.Set;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class EntityDamageByEntityListener extends PluginListener {

    private final Map<UUID, Long> lastChargedAttack = new HashMap<>(); // Track last attack time
    private static final long COOLDOWN_TIME = 30 * 1000; // 30 seconds in milliseconds
    private static final double DAMAGE_MULTIPLIER = 2.0; // Double the damage

    public EntityDamageByEntityListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        UUID playerId = player.getUniqueId();

        // Check if the player has the "charged_strike" ability
        final Set<UUID> playersWithChargedStrike = this.getPluginFacade()
                .getPlayerAbilityManager()
                .getPlayersWithAbilityByName("charged_strike");

        if (!playersWithChargedStrike.contains(playerId)) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long lastTime = lastChargedAttack.getOrDefault(playerId, 0L);

        if (currentTime - lastTime >= COOLDOWN_TIME) {
            // Perform the charged attack
            event.setDamage(event.getDamage() * DAMAGE_MULTIPLIER);
            player.sendMessage(ChatColor.GOLD + "Charged Attack! You dealt extra damage!");

            // Reset cooldown
            lastChargedAttack.put(playerId, currentTime);
        } else {
            long remainingTime = (COOLDOWN_TIME - (currentTime - lastTime)) / 1000;
            player.sendMessage(ChatColor.RED + "Charged Attack on cooldown! " + remainingTime + "s remaining.");
        }
    }
}
