package me.a8kj.battlestreaks.ability;

import java.util.Map;
import java.util.Optional;

public interface AbilityManager {
    void registerAbility(String name, AbilityBase ability);

    void unRegisterAbility(String name);

    Optional<AbilityBase> getAbility(String name);

    Map<String, AbilityBase> getAbilities();

    void updateAbilities(Map<String, AbilityBase> abilities);

    boolean isAbilityRegistered(String name);
}
