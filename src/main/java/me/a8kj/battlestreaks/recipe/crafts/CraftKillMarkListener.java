package me.a8kj.battlestreaks.recipe.crafts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;
import me.a8kj.battlestreaks.recipe.RecipeCraftListener;
import me.a8kj.battlestreaks.util.AndBooleanBuilder;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class CraftKillMarkListener extends RecipeCraftListener {

    public CraftKillMarkListener(@NonNull Plugin plugin) {
        super(plugin);
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
            if (ItemMetadataUtils.hasMetadata(totemItem, "totem_fragment", this.plugin))
                builder.append(true);
        } else {
            builder.append(false);
        }

        if (isItemInSlot(inventory, 8, Material.DIAMOND_BLOCK)) {
            builder.append(true);
        } else {
            builder.append(false);
        }

        // add condition to check if player not in live system

        return builder.toBoolean();
    }

    @Override
    protected boolean isMetResultCondition(ItemStack result) {
        return ItemMetadataUtils.hasMetaDisplayName(result, "&4Kill Mark")
                && result.getType() == Material.TOTEM_OF_UNDYING;
    }

    @Override
    protected void doSomethingElse(CraftItemEvent event, Player player, ApprovalStatus status) {
        if (status == ApprovalStatus.ACCEPTED) {
            player.sendMessage("You have kill mark yay !");
            return;
        } else {
            player.sendMessage(getDeniedMessage());
        }
    }

    @Override
    protected String getDeniedMessage() {
        return "You cannot create a kill mark item while you are in lives system!";
    }

}
