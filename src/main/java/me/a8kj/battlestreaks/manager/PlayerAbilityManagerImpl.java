package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class PlayerAbilityManagerImpl implements PlayerAbilityManager {
    private Map<UUID, Map<String, AbilityBase>> playerAbilities = new HashMap<>(); // Player ID to abilities map

    @Override
    public void registerAbility(Player player, AbilityBase ability) {
        UUID playerId = player.getUniqueId();
        playerAbilities.putIfAbsent(playerId, new HashMap<>());
        playerAbilities.get(playerId).put(ability.getName(), ability);
    }

    @Override
    public void activateAbility(Player player, String abilityName) {
        UUID playerId = player.getUniqueId();
        Map<String, AbilityBase> abilities = playerAbilities.get(playerId);

        if (abilities != null && abilities.containsKey(abilityName)) {
            AbilityBase ability = abilities.get(abilityName);
            ability.activate(player);
        } else {
            player.sendMessage("You don't have this ability.");
        }
    }

    @Override
    public void deactivateAbility(Player player, String abilityName) {
        UUID playerId = player.getUniqueId();
        Map<String, AbilityBase> abilities = playerAbilities.get(playerId);

        if (abilities != null && abilities.containsKey(abilityName)) {
            AbilityBase ability = abilities.get(abilityName);
            ability.deactivate(player);
        } else {
            player.sendMessage("You don't have this ability.");
        }
    }

    @Override
    public void updateAbilities(Player player) {
        UUID playerId = player.getUniqueId();
        Map<String, AbilityBase> abilities = playerAbilities.get(playerId);

        if (abilities != null) {
            for (AbilityBase ability : abilities.values()) {
                ability.update(player);
            }
        }
    }

    @Override
    public List<String> getAllAbilities(Player player) {
        UUID playerId = player.getUniqueId();
        Map<String, AbilityBase> abilities = playerAbilities.get(playerId);

        if (abilities != null) {
            return new ArrayList<>(abilities.keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean hasAbility(Player player, String abilityName) {
        UUID playerId = player.getUniqueId();
        Map<String, AbilityBase> abilities = playerAbilities.get(playerId);
        return abilities != null && abilities.containsKey(abilityName);
    }

    @Override
    public void replaceAbility(Player player, AbilityBase newAbility) {
        UUID playerId = player.getUniqueId();
        Map<String, AbilityBase> abilities = playerAbilities.get(playerId);

        if (abilities != null) {
            // Check if player already has the ability
            if (abilities.containsKey(newAbility.getName())) {
                // Deactivate the old ability
                abilities.get(newAbility.getName()).deactivate(player);

                // Remove the old ability
                abilities.remove(newAbility.getName());
            }

            // Register the new ability (it may replace the old one)
            abilities.put(newAbility.getName(), newAbility);

        } else {
            player.sendMessage("You don't have any abilities to replace.");
        }
    }
}
