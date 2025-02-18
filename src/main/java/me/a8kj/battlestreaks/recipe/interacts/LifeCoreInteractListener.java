package me.a8kj.battlestreaks.recipe.interacts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.recipe.CustomInteractListener;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class LifeCoreInteractListener extends CustomInteractListener {

    public LifeCoreInteractListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        register();
    }

    @Override
    public boolean isConditionMet(ItemStack itemStack) {
        return ItemMetadataUtils.hasMetaDisplayName(itemStack, "&6Life Core")
                && itemStack.getType() == Material.TOTEM_OF_UNDYING;
    }

    @Override
    public boolean canExecute(PlayerInteractEvent event, Player player) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            return true;
        }

        return false;

    }

    @Override
    public void execute(PlayerInteractEvent event, Player player) {
        // Add kill streak
        getDataConfig().addData(player, PlayerDataType.LIVES, 1);

        // Check if the player has enough Kill Marks to remove
        ItemStack killMarkItem = event.getItem();
        if (killMarkItem != null && killMarkItem.getAmount() > 0) {
            // Reduce the amount of Kill Marks by 1
            int newAmount = killMarkItem.getAmount() - 1;
            killMarkItem.setAmount(newAmount > 0 ? newAmount : 0); // Make sure the amount doesn't go negative
            if (newAmount == 0) {
                player.setItemInHand(new ItemStack(Material.AIR));
            }
            // Update the player's inventory
            player.updateInventory();

            // Remove 1 Kill Mark from the player's data
            getDataConfig().removeData(player, PlayerDataType.KILL_MARKS, 1);

            new PlayerActionBar("&e+1 Lives!").execute(player);
        }
    }
}
