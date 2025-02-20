package me.a8kj.battlestreaks.listener.impl;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.impl.PlayerEffectAppliedEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent.LivesStatus;
import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerLivesListener extends PluginListener {

    public PlayerLivesListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        this.register();
    }

    @EventHandler
    public void onPlayerLives(PlayerLivesEvent event) {
        Player player = event.getPlayer();
        LivesStatus status = event.getStatus();
        int lives = event.getRemainingLives();

        if (status == LivesStatus.ACHIEVED || status == LivesStatus.LOST || status == null) {
            if (getPluginFacade().getPlayerAbilityManager().hasAbility(player)) {
                getPluginFacade().getPlayerAbilityManager().removeAbility(player);
                getDataConfig().setData(player, PlayerDataType.ABILITY, "NONE");
            }
            removePlayerEffects(player);
            Optional<NegativeEffect> effect = getEffectManager().getEffectMap().values().stream()
                    .filter(e -> e.getRequiredLives() == lives)
                    .findFirst();

            effect.ifPresent(e -> new PlayerEffectAppliedEvent(event.getPlayer(), e).callEvent());
        }

    }

}
