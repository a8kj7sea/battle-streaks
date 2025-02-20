package me.a8kj.battlestreaks.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.NamespacedKey;

public class ItemMetadataUtils {

    public static boolean hasMetadataString(ItemStack item, String key, Plugin plugin) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            return meta.getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING);
        }

        return false;
    }

    public static boolean hasMetadata(ItemStack item, String key, Plugin plugin) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            return meta.getPersistentDataContainer().has(namespacedKey);
        }

        return false;
    }

    public static boolean hasMetaDisplayName(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                String displayName = meta.getDisplayName();
                return displayName != null && !displayName.isEmpty();
            }
        }
        return false;
    }

    public static boolean hasMetaDisplayName(ItemStack item, String displayNameToCheck) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                String displayName = meta.getDisplayName();
                return displayName != null && displayName.equals(ChatUtils.format(displayNameToCheck));
            }
        }
        return false;
    }

    public static String getMetadata(ItemStack item, String key, Plugin plugin) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            return meta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
        }

        return null;
    }

    public static void setMetadata(ItemStack item, String key, String value, Plugin plugin) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            meta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
            item.setItemMeta(meta);
        }
    }
}
