package me.a8kj.battlestreaks.plugin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class BattleStreaksPlugin extends JavaPlugin implements Listener {
    //

    @Getter
    private static PluginFacade pluginFacade;

    @Override
    public void onEnable() {
        pluginFacade = new PluginFacade(getLogger(), this);
        pluginFacade.onLunch();
        Bukkit.getConsoleSender()
                .sendMessage(ChatColor.GOLD + "⚔ " + ChatColor.RED + "Battle" + ChatColor.YELLOW + "Streaks "
                        + ChatColor.GREEN + "has been ENABLED! " + ChatColor.GRAY + "Made by " + ChatColor.AQUA
                        + "a8kj7eas");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender()
                .sendMessage(ChatColor.DARK_RED + "⚠ " + ChatColor.RED + "Battle" + ChatColor.YELLOW + "Streaks "
                        + ChatColor.GRAY + "has been DISABLED. " + ChatColor.RED + "See you next time!");
        pluginFacade.onStop();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Bukkit.getScheduler().runTaskLater(this, () -> player.spigot().respawn(), 1L); // Respawn next tick
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            player.setGameMode(GameMode.SURVIVAL); // Ensure correct game mode
            player.teleport(player.getWorld().getSpawnLocation()); // Adjust as needed
        }, 1L);
    }
}
