package me.a8kj.battlestreaks.ability;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbilityBase implements Ability {

    private final String name;

}
