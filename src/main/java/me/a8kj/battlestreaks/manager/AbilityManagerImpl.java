package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.ability.AbilityManager;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class AbilityManagerImpl implements AbilityManager {
    private Map<String, AbilityBase> abilities = new HashMap<>();

    @Override
    public void registerAbility(AbilityBase ability) {
        abilities.put(ability.getName(), ability);
    }

    @Override
    public void activateAbility(Player player, String abilityName) {
        AbilityBase ability = abilities.get(abilityName);
        if (ability != null) {
            ability.activate(player);
        }
    }

    @Override
    public void deactivateAbility(Player player, String abilityName) {
        AbilityBase ability = abilities.get(abilityName);
        if (ability != null) {
            ability.deactivate(player);
        }
    }

    @Override
    public void updateAbilities(Player player) {
        for (AbilityBase ability : abilities.values()) {
            ability.update(player);
        }
    }

    @Override
    public List<String> getAllAbilities() {
        return new ArrayList<>(abilities.keySet());
    }
}
