package me.a8kj.battlestreaks.listener.impl;

import java.util.Optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.api.player.impl.PlayerEffectAppliedEvent;
import me.a8kj.battlestreaks.effect.NegativeEffect;
import me.a8kj.battlestreaks.effect.NegativeEffectManager;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerRespawnListener extends PluginListener {

    public PlayerRespawnListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        this.register();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        NegativeEffectManager effectManager = this.getEffectManager();
        if (PluginFacade.getPlayersInLivesMode().contains(event.getPlayer().getUniqueId())) {
            if (effectManager.contains(event.getPlayer())) {
                Optional<NegativeEffect> effect = effectManager.getNegativeEffectForPlayer(event.getPlayer());
                if (effect.isPresent()) {
                    new PlayerEffectAppliedEvent(event.getPlayer(), effect.get()).callEvent();
                }
            } else {
                int lives = getDataConfig().getData(event.getPlayer(), PlayerDataType.LIVES, 0);
                Optional<NegativeEffect> effect = getEffectManager().getEffectMap().values().stream()
                        .filter(e -> e.getRequiredLives() == lives)
                        .findFirst();

                effect.ifPresent(e -> new PlayerEffectAppliedEvent(event.getPlayer(), e));
            }
        }
    }

}
