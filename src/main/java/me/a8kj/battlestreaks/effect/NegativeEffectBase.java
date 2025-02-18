package me.a8kj.battlestreaks.effect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class NegativeEffectBase implements NegativeEffect {

    private final int requiredLives;
    private final String name;
}
