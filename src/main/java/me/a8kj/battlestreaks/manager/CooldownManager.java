package me.a8kj.battlestreaks.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.a8kj.battlestreaks.cooldown.Cooldown;

public class CooldownManager implements Cooldown {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    @Override
    public void start(UUID playerId, String cooldownName, int minutes, int seconds) {
        int totalSeconds = (minutes * 60) + seconds;
        long expirationTime = System.currentTimeMillis() + (totalSeconds * 1000L);
        cooldowns.computeIfAbsent(playerId, k -> new HashMap<>()).put(cooldownName, expirationTime);
    }

    @Override
    public boolean isOnCooldown(UUID playerId, String cooldownName) {
        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns == null || !playerCooldowns.containsKey(cooldownName)) {
            return false;
        }
        long expirationTime = playerCooldowns.get(cooldownName);
        return System.currentTimeMillis() < expirationTime;
    }

    @Override
    public int getRemainingTime(UUID playerId, String cooldownName) {
        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns == null || !playerCooldowns.containsKey(cooldownName)) {
            return 0;
        }
        long expirationTime = playerCooldowns.get(cooldownName);
        long remainingTime = expirationTime - System.currentTimeMillis();
        return remainingTime > 0 ? (int) (remainingTime / 1000) : 0;
    }

    @Override
    public void reset(UUID playerId, String cooldownName) {
        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns != null) {
            playerCooldowns.remove(cooldownName);
        }
    }

    /**
     * Get the remaining time of a cooldown for the player in a formatted string (Xm
     * Ys).
     * 
     * @param playerId     The UUID of the player.
     * @param cooldownName The name of the cooldown.
     * @return The remaining time in a readable format.
     */
    @Override
    public String getRemainingTimeAsString(UUID playerId, String cooldownName) {
        if (!isOnCooldown(playerId, cooldownName)) {
            return "0s"; // No cooldown active
        }

        long remainingMillis = cooldowns.get(playerId).get(cooldownName) - System.currentTimeMillis();
        int seconds = (int) (remainingMillis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;

        return (minutes > 0) ? minutes + "m " + seconds + "s" : seconds + "s";
    }
}
