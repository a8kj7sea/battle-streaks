package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import org.bukkit.entity.Player;
import java.util.*;

public class PlayerAbilityManagerImpl implements PlayerAbilityManager {
    private final AbilityManager abilityManager;
    private final Map<UUID, Set<String>> playerAbilities = new HashMap<>(); // Player UUID to ability names

    public PlayerAbilityManagerImpl(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    @Override
    public void registerAbility(Player player, AbilityBase ability) {
        abilityManager.registerAbility(ability);
        playerAbilities.putIfAbsent(player.getUniqueId(), new HashSet<>());
        playerAbilities.get(player.getUniqueId()).add(ability.getName());
    }

    @Override
    public void activateAbility(Player player, String abilityName) {
        AbilityBase ability = abilityManager.getAbility(abilityName);
        if (ability != null && playerAbilities.getOrDefault(player.getUniqueId(), Collections.emptySet()).contains(abilityName)) {
            ability.activate(player);
        } else {
            player.sendMessage("You don't have this ability.");
        }
    }

    @Override
    public void deactivateAbility(Player player, String abilityName) {
        AbilityBase ability = abilityManager.getAbility(abilityName);
        if (ability != null && playerAbilities.getOrDefault(player.getUniqueId(), Collections.emptySet()).contains(abilityName)) {
            ability.deactivate(player);
        } else {
            player.sendMessage("You don't have this ability.");
        }
    }

    @Override
    public void updateAbilities(Player player) {
        Set<String> abilities = playerAbilities.getOrDefault(player.getUniqueId(), Collections.emptySet());
        for (String abilityName : abilities) {
            AbilityBase ability = abilityManager.getAbility(abilityName);
            if (ability != null) {
                ability.update(player);
            }
        }
    }

    @Override
    public List<String> getAllAbilities(Player player) {
        return new ArrayList<>(playerAbilities.getOrDefault(player.getUniqueId(), Collections.emptySet()));
    }

    @Override
    public boolean hasAbility(Player player, String abilityName) {
        return playerAbilities.getOrDefault(player.getUniqueId(), Collections.emptySet()).contains(abilityName);
    }

    @Override
    public void replaceAbility(Player player, AbilityBase newAbility) {
        Set<String> abilities = playerAbilities.get(player.getUniqueId());
        if (abilities != null) {
            if (abilities.contains(newAbility.getName())) {
                AbilityBase oldAbility = abilityManager.getAbility(newAbility.getName());
                if (oldAbility != null) {
                    oldAbility.deactivate(player);
                }
                abilities.remove(newAbility.getName());
            }
            abilities.add(newAbility.getName());
            abilityManager.registerAbility(newAbility);
        } else {
            player.sendMessage("You don't have any abilities to replace.");
        }
    }
}
