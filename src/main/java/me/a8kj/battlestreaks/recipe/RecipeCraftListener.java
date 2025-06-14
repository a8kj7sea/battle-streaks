package me.a8kj.battlestreaks.recipe;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import lombok.Getter;
import lombok.NonNull;
import me.a8kj.battlestreaks.listener.PluginListener;
import me.a8kj.battlestreaks.plugin.PluginFacade;

@Getter
public abstract class RecipeCraftListener extends PluginListener {

    public RecipeCraftListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
    }

    protected abstract boolean canCraft(CraftingInventory inventory);

    protected abstract boolean isMetResultCondition(ItemStack result);

    protected void doSomethingElse(CraftItemEvent event, Player player, ApprovalStatus status) {

    }

    protected String getDeniedMessage() {
        return null;
    }

    protected enum ApprovalStatus {
        ACCEPTED, DENIED
    }

    protected boolean isItemInSlot(Inventory inventory, int slot, Material material) {
        ItemStack item = inventory.getItem(slot);
        return item != null && item.getType() == material;
    }

    protected boolean isItemStackInSlot(Inventory inventory, int slot, ItemStack target) {
        ItemStack item = inventory.getItem(slot);
        return item != null && item.isSimilar(target);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack result = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (player == null || event.getInventory() == null)
            return;

        CraftingInventory craftingInventory = event.getInventory();

        if (!isMetResultCondition(result))
            return;

        if (!canCraft(craftingInventory)) {
            event.setCancelled(true);
            player.closeInventory();
            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.5f, 15.0f);
            doSomethingElse(event, player, ApprovalStatus.DENIED);
            return;
        }
        doSomethingElse(event, player, ApprovalStatus.ACCEPTED);
        player.playSound(player, Sound.ENTITY_VILLAGER_YES, 0.5f, 15.0f);
    }

}
