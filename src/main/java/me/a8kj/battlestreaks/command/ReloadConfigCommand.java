package me.a8kj.battlestreaks.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.plugin.PluginFacade;

@RequiredArgsConstructor
@Getter
public class ReloadConfigCommand implements CommandExecutor {

    private final PluginFacade pluginFacade;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) && !sender.hasPermission("bsplugin.reload")) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return false;
        }

        try {
            pluginFacade.getDefaultConfiguration().load();
            pluginFacade.getDataConfiguration().load();
            sender.sendMessage("§aConfiguration reloaded successfully.");
        } catch (Exception e) {
            sender.sendMessage("§cAn error occurred while reloading the configuration.");
            pluginFacade.getLogger().severe("Error reloading the configuration: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

}
