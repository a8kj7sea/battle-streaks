package me.a8kj.battlestreaks.listener.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public class PlayerMoveListener extends PluginListener {

    public PlayerMoveListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        this.register();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (PluginFacade.getPlayersInLivesMode().contains(event.getPlayer().getUniqueId())) {
            new PlayerActionBar("                        " + getLives(event.getPlayer()) + "&c❤️")
                    .execute(event.getPlayer());
        }

    }

    private int getLives(Player player) {
        return getDataConfig().getData(player, PlayerDataType.LIVES, 5);
    }

}
