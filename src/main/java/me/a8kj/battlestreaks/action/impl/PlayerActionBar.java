package me.a8kj.battlestreaks.action.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.action.PlayerAction;
import me.a8kj.battlestreaks.util.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@RequiredArgsConstructor
@Getter
public class PlayerActionBar extends PlayerAction {

    private final String message;

    @Override
    public void execute(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtils.format(message)));
        this.result = ActionResult.EXECUTED;
    }

}
