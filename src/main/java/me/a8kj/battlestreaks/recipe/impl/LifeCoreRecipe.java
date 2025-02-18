package me.a8kj.battlestreaks.recipe.impl;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;
import me.a8kj.battlestreaks.recipe.RecipeBase;
import me.a8kj.battlestreaks.util.ItemStackBuilder;

public class LifeCoreRecipe extends RecipeBase {

        public LifeCoreRecipe(@NonNull Plugin plugin) {
                super("life_core",
                                new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                                                .setAmount(1)
                                                .setDisplayName("&6Life Core")
                                                .setLore("&7A powerful artifact infused",
                                                                "&7with the essence of life.",
                                                                "&7Use it to restore your lost lives.")
                                                .build(),
                                plugin);

                setMetaData("name", name, plugin);

                setPattern("WGW",
                                "BTB",
                                "ISI");

                setIngredient('W', Material.WITHER_SKELETON_SKULL);
                setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);
                setIngredient('B', Material.BLAZE_ROD);
                setIngredient('I', Material.IRON_NUGGET);
                setIngredient('T', Material.TOTEM_OF_UNDYING);
        }

}
