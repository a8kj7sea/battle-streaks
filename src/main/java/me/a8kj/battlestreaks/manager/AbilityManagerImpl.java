package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.ability.AbilityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AbilityManagerImpl implements AbilityManager {
    private final Map<String, AbilityBase> abilities = new HashMap<>();

    @Override
    public void registerAbility(String name, AbilityBase ability) {
        abilities.put(name.toLowerCase(), ability);
    }

    @Override
    public void unRegisterAbility(String name) {
        abilities.remove(name.toLowerCase());
    }

    @Override
    public Optional<AbilityBase> getAbility(String name) {
        return Optional.ofNullable(abilities.get(name.toLowerCase()));
    }

    @Override
    public Map<String, AbilityBase> getAbilities() {
        return new HashMap<>(abilities); // Return a copy to prevent external modification
    }

    @Override
    public void updateAbilities(Map<String, AbilityBase> newAbilities) {
        abilities.clear();
        abilities.putAll(newAbilities);
    }

    @Override
    public boolean isAbilityRegistered(String name) {
        return abilities.containsKey(name.toLowerCase());
    }
}
