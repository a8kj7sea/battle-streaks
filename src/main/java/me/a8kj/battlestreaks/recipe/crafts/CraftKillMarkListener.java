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
import me.a8kj.battlestreaks.util.ChatUtils;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class CraftKillMarkListener extends RecipeCraftListener {

    public CraftKillMarkListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);

    }

    @Override
    public boolean canCraft(CraftingInventory inventory) {
        ItemStack[] contents = inventory.getContents();

        for (ItemStack item : contents) {
            if (item != null && !item.getType().isAir()) {
                if (item.getType() == Material.TOTEM_OF_UNDYING &&
                        item.hasItemMeta() &&
                        item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtils.format("&eTotem Fragment"))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isMetResultCondition(ItemStack result) {
        return ItemMetadataUtils.hasMetaDisplayName(result, "&4Kill Mark")
                && result.getType() == Material.TOTEM_OF_UNDYING;
    }

    @Override
    public void doSomethingElse(CraftItemEvent event, Player player, ApprovalStatus status) {
        if (status == ApprovalStatus.DENIED) {
            event.setResult(Result.DENY);
        }
    }

}
