package me.a8kj.battlestreaks.listener.impl;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent.LivesStatus;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.manager.NegativeEffectManager;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerLivesListener extends PluginListener {

    public PlayerLivesListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    @EventHandler
    public void onPlayerLives(PlayerLivesEvent event) {
        Player player = event.getPlayer();
        LivesStatus status = event.getStatus();
        int lives = event.getRemainingLives();

        if (status == null) {

            if (lives == 5) {
                if (getPluginFacade().getPlayerAbilityManager().hasAbility(player)) {
                    getPluginFacade().getPlayerAbilityManager().removeAbility(player);
                }

                removePlayerEffects(player);

            }

        }

        if (status == LivesStatus.ACHIEVED || status == LivesStatus.LOST) {
            @SuppressWarnings("unchecked")
            List<Map<?, ?>> livesConfigList = (List<Map<?, ?>>) getPluginFacade().getDefaultConfiguration()
                    .getYamConfiguration()
                    .getList("lives-system");

            if (livesConfigList == null) {
                return;
            }

            // Iterate through the lives-system configuration to apply effects and messages
            // based on the player's lives
            for (Map<?, ?> lifeConfig : livesConfigList) {
                // Ensure valid data in the config
                if (!lifeConfig.containsKey("at") || !lifeConfig.containsKey("effect")
                        || !lifeConfig.containsKey("message")) {
                    continue; // Skip this entry if essential data is missing
                }

                // Get the life value, effect name, and message
                int lifeAt = (int) lifeConfig.get("at");
                String effectName = (String) lifeConfig.get("effect");
                String message = (String) lifeConfig.get("message");

                // Apply effects based on the player's remaining lives
                if (lives == lifeAt) {
                    // Remove any previous effects to avoid stacking them
                    removePlayerEffects(player);

                    // Remove any negative effects using the NegativeEffectManager (custom manager)
                    NegativeEffectManager.removePlayer(player);

                    // Apply the effect by the effect name (make sure to handle case sensitivity)
                    NegativeEffectManager.applyNegativeEffect(player, effectName.toLowerCase());

                    // Display the action bar message
                    new PlayerActionBar(message).execute(player);
                }
            }
        }

    }

}
