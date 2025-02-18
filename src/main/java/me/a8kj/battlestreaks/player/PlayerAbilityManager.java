package me.a8kj.battlestreaks.player;

import me.a8kj.battlestreaks.ability.AbilityBase;
import org.bukkit.entity.Player;
import java.util.List;

public interface PlayerAbilityManager {
    void registerAbility(Player player, AbilityBase ability);

    void activateAbility(Player player, String abilityName);

    void deactivateAbility(Player player, String abilityName);

    void updateAbilities(Player player);

    List<String> getAllAbilities(Player player);

    boolean hasAbility(Player player, String abilityName);

    void replaceAbility(Player player, AbilityBase newAbility);
}
