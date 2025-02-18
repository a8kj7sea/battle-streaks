package me.a8kj.battlestreaks.cooldown;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CooldownTime {

    private final int minutes;
    private final int seconds;

    public long getTotal() {
        return ((minutes * 60) + seconds) * 1000;
    }

}
