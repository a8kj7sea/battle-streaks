package me.a8kj.battlestreaks.recipe.crafts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import lombok.NonNull;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.recipe.RecipeCraftListener;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class CraftTotemFragmentListener extends RecipeCraftListener {

    public CraftTotemFragmentListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        // register();
    }

    @Override
    protected boolean canCraft(CraftingInventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isMetResultCondition(ItemStack result) {
        return ItemMetadataUtils.hasMetaDisplayName(result, "&eTotem Fragment")
                && result.getType() == Material.TOTEM_OF_UNDYING;
    }

    @Override
    protected void doSomethingElse(CraftItemEvent event, Player player, ApprovalStatus status) {
        if (status == ApprovalStatus.DENIED) {
            event.setResult(Result.DENY);
        }
    }

}
