package me.a8kj.battlestreaks.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class BattleStreaksPlugin extends JavaPlugin {
    //

    @Getter
    private static PluginFacade pluginFacade;

    @Override
    public void onEnable() {
        pluginFacade = new PluginFacade(getLogger(), this);
        pluginFacade.onLunch();
        Bukkit.getConsoleSender()
                .sendMessage(ChatColor.GOLD + "⚔  " + ChatColor.RED + "Battle" + ChatColor.YELLOW + "Streaks "
                        + ChatColor.GREEN + "has been ENABLED! " + ChatColor.GRAY + "Made by " + ChatColor.AQUA
                        + "a8kj7eas");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender()
                .sendMessage(ChatColor.DARK_RED + "⚠  " + ChatColor.RED + "Battle" + ChatColor.YELLOW + "Streaks "
                        + ChatColor.GRAY + "has been DISABLED. " + ChatColor.RED + "See you next time!");
        pluginFacade.onStop();
    }

}
