package me.a8kj.battlestreaks.recipe.crafts;

import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;
import me.a8kj.battlestreaks.recipe.RecipeCraftListener;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class CraftLifeCoreListener extends RecipeCraftListener {

    public CraftLifeCoreListener(@NonNull Plugin plugin) {
        super(plugin);
        register();
    }

    @Override
    protected boolean canCraft(CraftingInventory inventory) {
        ItemStack totemFragment = inventory.getItem(4);
        return ItemMetadataUtils.hasMetadata(totemFragment, "totem_fragment", plugin);
    }

    @Override
    protected boolean isMetResultCondition(ItemStack result) {
        return ItemMetadataUtils.hasMetaDisplayName(result, "&6Life Core")
                && result.getType() == Material.TOTEM_OF_UNDYING;
    }

}
