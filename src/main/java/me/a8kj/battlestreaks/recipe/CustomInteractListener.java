package me.a8kj.battlestreaks.recipe;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

public abstract class CustomInteractListener extends PluginListener {

    public CustomInteractListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        register();
    }

    public abstract boolean isConditionMet(ItemStack itemStack);

    public abstract boolean canExecute(PlayerInteractEvent event, Player player);

    public abstract void execute(PlayerInteractEvent event, Player player);

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getItemInHand() != null || !player.getItemInHand().getType().isAir()) {
            ItemStack item = player.getItemInHand();
            if (!isConditionMet(item))
                return;

            if (!canExecute(event, player))
                return;

            execute(event, player);

        }

    }
}
