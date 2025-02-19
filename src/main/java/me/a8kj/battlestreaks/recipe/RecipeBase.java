package me.a8kj.battlestreaks.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class RecipeBase implements Recipe {

    protected final @NonNull String name;
    protected final @NonNull ItemStack result;
    protected ShapedRecipe recipe;
    protected final @NonNull Plugin plugin;
    protected String[] pattern;

    @Override
    public void setPattern(String... pattern) {
        this.pattern = pattern;
    }

    @Override
    public void setIngredient(char letter, Material material) {
        this.recipe.setIngredient(letter, material);
    }

    @Override
    public void register() {
        preInit();
        this.recipe = new ShapedRecipe(new NamespacedKey(plugin, name), result);
        this.recipe.shape(pattern);
        init();
        this.plugin.getServer().addRecipe(this.recipe);

    }

    protected abstract void preInit();

    protected abstract void init();

    @Override
    public void setMetaData(String key, String value, Plugin plugin) {
        if (result == null || !result.hasItemMeta()) {
            return;
        }

        ItemMeta meta = result.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
            result.setItemMeta(meta);
        }
    }
}
