package me.a8kj.battlestreaks.ability;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.cooldown.CooldownTime;

@RequiredArgsConstructor
@Getter
public abstract class AbilityBase implements Ability {

    private final String name;
    private final String description;
    private final CooldownTime cooldownTime;
    private final int maxStreaks, minStreaks;

}
