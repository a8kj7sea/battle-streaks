package me.a8kj.battlestreaks.recipe.interacts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerKillStreakEvent.KillStreakStatus;
import me.a8kj.battlestreaks.player.properties.PlayerDataType;
import me.a8kj.battlestreaks.plugin.PluginFacade;
import me.a8kj.battlestreaks.recipe.CustomInteractListener;
import me.a8kj.battlestreaks.util.ItemMetadataUtils;

public class KillMarksInteractListener extends CustomInteractListener {

    public KillMarksInteractListener(@NonNull PluginFacade pluginFacade) {
        super(pluginFacade);
        register();
    }

    @Override
    public boolean isConditionMet(ItemStack itemStack) {
        return ItemMetadataUtils.hasMetaDisplayName(itemStack, "&4Kill Mark")
                && itemStack.getType() == Material.TOTEM_OF_UNDYING;
    }

    @Override
    public boolean canExecute(PlayerInteractEvent event, Player player) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (PluginFacade.getPlayersInLivesMode().contains(player.getUniqueId())) {
                new PlayerActionBar("&cYou cannot get a kill streak while you are in lives mode!").execute(player);
                return false;
            }

            return true;
        }

        return false;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(PlayerInteractEvent event, Player player) {

        getDataConfig().addData(player, PlayerDataType.STREAKS, 1);

        ItemStack killMarkItem = event.getItem();
        if (killMarkItem != null && killMarkItem.getAmount() > 0) {

            int newAmount = killMarkItem.getAmount() - 1;
            killMarkItem.setAmount(newAmount > 0 ? newAmount : 0);
            if (newAmount == 0) {
                player.setItemInHand(new ItemStack(Material.AIR));
            }

            player.updateInventory();
            new PlayerKillStreakEvent(player, getDataConfig().getData(player, PlayerDataType.STREAKS, 1),
                    KillStreakStatus.ACHIEVED).callEvent();

            getDataConfig().removeData(player, PlayerDataType.KILL_MARKS, 1);

            new PlayerActionBar("&a+1 Kill streak!").execute(player);
        }
    }
}
