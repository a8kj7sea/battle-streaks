package me.a8kj.battlestreaks.action;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class PlayerAction implements Action<Player> {

    protected ActionResult result = ActionResult.NOT_EXECUTED;

}
