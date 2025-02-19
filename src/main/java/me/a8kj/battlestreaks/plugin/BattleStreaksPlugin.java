package me.a8kj.battlestreaks.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class BattleStreaksPlugin extends JavaPlugin {
    //

    @Getter
    private static PluginFacade pluginFacade;

    @Override
    public void onEnable() {
        pluginFacade = new PluginFacade(getLogger(), this);
        pluginFacade.onLunch();
    }
}
