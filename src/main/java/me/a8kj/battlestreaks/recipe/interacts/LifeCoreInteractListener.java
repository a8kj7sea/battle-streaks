package me.a8kj.battlestreaks.recipe.interacts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;
import me.a8kj.battlestreaks.action.impl.PlayerActionBar;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent;
import me.a8kj.battlestreaks.api.player.impl.PlayerLivesEvent.LivesStatus;
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
            if (!PluginFacade.getPlayersInLivesMode().contains(player.getUniqueId())) {
                player.sendMessage("\u00a7cYou cannot use it in streaks mode !");
                return false;
            }
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(PlayerInteractEvent event, Player player) {
        getDataConfig().addData(player, PlayerDataType.LIVES, 1);

        ItemStack killMarkItem = event.getItem();
        if (killMarkItem != null && killMarkItem.getAmount() > 0) {
            int newAmount = killMarkItem.getAmount() - 1;
            killMarkItem.setAmount(newAmount > 0 ? newAmount : 0);
            if (newAmount == 0) {
                player.setItemInHand(new ItemStack(Material.AIR));
            }
            player.updateInventory();

            getDataConfig().removeData(player, PlayerDataType.KILL_MARKS, 1);
            new PlayerActionBar("&e+1 Lives!").execute(player);
            if (getDataConfig().getData(player, PlayerDataType.LIVES, 1) >= 5) {
                getPluginFacade().removePlayerFromLivesMode(player);
                getDataConfig().setData(player, PlayerDataType.LIVES, 5);
                new PlayerActionBar("&1Wooosh you had left lives mode!").execute(player);
            }
            new PlayerLivesEvent(player, getDataConfig().getData(player, PlayerDataType.LIVES, 1), LivesStatus.ACHIEVED)
                    .callEvent();

        }
    }
}