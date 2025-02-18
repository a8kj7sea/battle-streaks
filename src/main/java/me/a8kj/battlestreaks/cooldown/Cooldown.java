package me.a8kj.battlestreaks.cooldown;

import java.util.UUID;

public interface Cooldown {

    /**
     * Start the cooldown for a specific player.
     *
     * @param playerId     the player's UUID
     * @param cooldownName the name of the cooldown
     * @param minutes      duration of the cooldown in minutes
     * @param seconds      additional seconds to be added to the cooldown duration
     */
    void start(UUID playerId, String cooldownName, int minutes, int seconds);

    /**
     * Check if the player is still in cooldown.
     *
     * @param playerId     the player's UUID
     * @param cooldownName the name of the cooldown
     * @return true if the player is still on cooldown
     */
    boolean isOnCooldown(UUID playerId, String cooldownName);

    /**
     * Get the remaining time for the cooldown of a specific player.
     *
     * @param playerId     the player's UUID
     * @param cooldownName the name of the cooldown
     * @return the remaining time in seconds, or 0 if the cooldown has expired
     */
    int getRemainingTime(UUID playerId, String cooldownName);

    String getRemainingTimeAsString(UUID playerId, String cooldownName);

    /**
     * Manually reset the cooldown for a player.
     *
     * @param playerId     the player's UUID
     * @param cooldownName the name of the cooldown
     */
    void reset(UUID playerId, String cooldownName);
}
