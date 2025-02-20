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
        
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (PluginFacade.getPlayersInLivesMode().contains(event.getPlayer().getUniqueId())) {
            new PlayerActionBar("   &fLives: " + drawHearts(event.getPlayer())).execute(event.getPlayer());
        }

    }

    private String drawHearts(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        int lives = getLives(player);
        if (lives <= 0)
            return "&4No More lives";
        for (int x = 0; x < lives; ++x) {
            stringBuilder.append("&c\u2764");
        }
        return stringBuilder.toString();
    }

    private int getLives(Player player) {
        return getDataConfig().getData(player, PlayerDataType.LIVES, 5);
    }

}
