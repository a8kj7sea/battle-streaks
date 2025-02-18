package me.a8kj.battlestreaks.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import lombok.NonNull;

public interface Recipe {

    @NonNull
    String getName();

    @NonNull
    ItemStack getResult();

    @NonNull
    String[] getPattern();

    @NonNull
    ShapedRecipe getRecipe();

    @NonNull
    Plugin getPlugin();

    void setPattern(String... pattern);

    void setIngredient(char letter, Material material);

    void register();

    void setMetaData(String key, String value, Plugin plugin);

}
