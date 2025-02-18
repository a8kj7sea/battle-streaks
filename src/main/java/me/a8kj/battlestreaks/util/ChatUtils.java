package me.a8kj.battlestreaks.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ChatUtils {

    @NonNull
    public static String[] format(String... strings) {
        return Arrays.stream(strings).map(ChatUtils::format).toArray(String[]::new);
    }

    @NonNull
    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @NonNull
    public static List<String> format(List<String> strings) {
        return strings.stream().map(ChatUtils::format).collect(Collectors.toList());
    }

}