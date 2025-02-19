package me.a8kj.battlestreaks.recipe.impl;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;
import me.a8kj.battlestreaks.recipe.RecipeBase;
import me.a8kj.battlestreaks.util.ItemStackBuilder;

public class TotemFragmentRecipe extends RecipeBase {

        public TotemFragmentRecipe(@NonNull Plugin plugin) {
                super("totem_fragment",
                                new ItemStackBuilder(Material.TOTEM_OF_UNDYING)
                                                .setAmount(1)
                                                .setDisplayName("&eTotem Fragment")
                                                .setLore("&7A fragment of a once-great totem.",
                                                                "&7It holds lingering protective power.",
                                                                "&7Used in powerful recipes.")
                                                .build(),
                                plugin);

        }

        @Override
        protected void init() {

                setIngredient('G', Material.GOLD_BLOCK);
                setIngredient('T', Material.TOTEM_OF_UNDYING);

        }

        @Override
        protected void preInit() {
                setMetaData("name", name, plugin);

                setPattern("GTG",
                                "G G",
                                "   ");
        }

}
