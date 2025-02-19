package me.a8kj.battlestreaks.player;

import me.a8kj.battlestreaks.ability.AbilityBase;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public interface PlayerAbilityManager {

    void activatePlayerAbility(Player player);

    void deactivatePlayerAbility(Player player);

    void replaceAbility(Player player, AbilityBase newAbility);

    void replaceAbility(Player player, String newAbility);

    void removeAbility(Player player);

    boolean hasAbility(Player player);

    boolean hasAbility(Player player, String abilityName);

    boolean hasAbility(Player player, AbilityBase ability);

    void registerAbility(Player player, AbilityBase ability);

    void registerAbility(Player player, String ability);

    void unRegisterAbility(Player player);

    void saveAbilities(Player player);

    void loadAbilities(Player player);

    Optional<AbilityBase> getAbility(Player player);

    Set<UUID> getPlayersWithAbilityByName(String name);

}
