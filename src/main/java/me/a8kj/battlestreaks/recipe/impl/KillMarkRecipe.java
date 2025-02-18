package me.a8kj.battlestreaks.recipe.impl;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;
import me.a8kj.battlestreaks.recipe.RecipeBase;
import me.a8kj.battlestreaks.util.ItemStackBuilder;

public class KillMarkRecipe extends RecipeBase {

    public KillMarkRecipe(@NonNull String name, @NonNull Plugin plugin) {
        super("kill_mark",
                new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                        .setAmount(1)
                        .setDisplayName("&4Kill Mark")
                        .setLore("&7A mark of death, infused",
                                "&7with dark energy.",
                                "&cCannot be used while",
                                "&cyou are in the lives system!")
                        .build(),
                plugin);

        setMetaData("name", name, plugin);
        setPattern(
                "NGN", "BSB", "DTD");

        setIngredient('N', Material.NETHERITE_INGOT);
        setIngredient('G', Material.GOLDEN_APPLE);
        setIngredient('B', Material.BLAZE_ROD);
        setIngredient('S', Material.NETHER_STAR);
        setIngredient('D', Material.DIAMOND_BLOCK);
        setIngredient('T', Material.TOTEM_OF_UNDYING);
    }

}
