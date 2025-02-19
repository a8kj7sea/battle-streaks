package me.a8kj.battlestreaks.recipe.crafts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import lombok.NonNull;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.recipe.RecipeCraftListener;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class CraftLifeCoreListener extends RecipeCraftListener {

    public CraftLifeCoreListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        register();
    }

    @Override
    protected boolean canCraft(CraftingInventory inventory) {
        ItemStack totemFragment = inventory.getItem(4);
        return ItemMetadataUtils.hasMetadata(totemFragment, "totem_fragment", getPluginFacade().getPlugin());
    }

    @Override
    protected boolean isMetResultCondition(ItemStack result) {
        return ItemMetadataUtils.hasMetaDisplayName(result, "&6Life Core")
                && result.getType() == Material.TOTEM_OF_UNDYING;
    }

    @Override
    protected void doSomethingElse(CraftItemEvent event, Player player, ApprovalStatus status) {
        if (ApprovalStatus.DENIED == status) {
            event.setCancelled(true);
            player.sendMessage("Sorry you cannot");
        }
    }

}
