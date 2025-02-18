package me.a8kj.battlestreaks.recipe.crafts;

import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import lombok.NonNull;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.recipe.RecipeCraftListener;
import me.a8kj.battlestreaks.util.AndBooleanBuilder;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class CraftKillMarkListener extends RecipeCraftListener {

    public CraftKillMarkListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        register();
    }

    @Override
    protected boolean canCraft(CraftingInventory inventory) {
        if (inventory.getSize() != 9) {
            return false;
        }

        AndBooleanBuilder builder = new AndBooleanBuilder();

        if (isItemInSlot(inventory, 0, Material.NETHERITE_INGOT)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 1, Material.GOLDEN_APPLE)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 2, Material.NETHERITE_INGOT)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 3, Material.BLAZE_ROD)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 4, Material.NETHER_STAR)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 5, Material.BLAZE_ROD)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 6, Material.DIAMOND_BLOCK)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 7, Material.TOTEM_OF_UNDYING)) {
            ItemStack totemItem = inventory.getItem(7);
            if (ItemMetadataUtils.hasMetadata(totemItem, "totem_fragment", getPluginFacade().getPlugin()))
                builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 8, Material.DIAMOND_BLOCK)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        return builder.toBoolean();
    }

    @Override
    protected boolean isMetResultCondition(ItemStack result) {
        return ItemMetadataUtils.hasMetaDisplayName(result, "&4Kill Mark")
                && result.getType() == Material.TOTEM_OF_UNDYING;
    }

}
