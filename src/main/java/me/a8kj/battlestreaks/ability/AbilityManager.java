package me.a8kj.battlestreaks.ability;

import org.bukkit.entity.Player;
import java.util.List;

public interface AbilityManager {
    void registerAbility(AbilityBase ability);

    void activateAbility(Player player, String abilityName);

    void deactivateAbility(Player player, String abilityName);

    void updateAbilities(Player player);

    AbilityBase getAbility(String name);

    List<String> getAllAbilities();
}
