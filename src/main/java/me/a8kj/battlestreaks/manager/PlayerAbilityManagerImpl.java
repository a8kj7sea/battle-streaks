package me.a8kj.battlestreaks.manager;

import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PlayerAbilityManagerImpl implements PlayerAbilityManager {
    private final AbilityManager abilityManager;
    private final Map<UUID, String> playerAbilities = new HashMap<>();

    @Override
    public void activatePlayerAbility(Player player) {
        UUID playerId = player.getUniqueId();
        if (!playerAbilities.containsKey(playerId)) {
            player.sendMessage("You have no ability assigned!");
            return;
        }

        String abilityName = playerAbilities.get(playerId);
        Optional<AbilityBase> optionalAbility = abilityManager.getAbility(abilityName);

        if (optionalAbility.isPresent()) {
            AbilityBase ability = optionalAbility.get();
            ability.activate(player);
        } else {
            player.sendMessage("Ability not found!");
        }
    }

    @Override
    public void deactivatePlayerAbility(Player player) {
        UUID playerId = player.getUniqueId();
        if (!playerAbilities.containsKey(playerId))
            return;

        abilityManager.getAbility(playerAbilities.get(playerId))
                .ifPresent(ability -> ability.deactivate(player));
    }

    @Override
    public void replaceAbility(Player player, AbilityBase newAbility) {
        replaceAbility(player, newAbility.getName());
    }

    @Override
    public void replaceAbility(Player player, String newAbility) {
        playerAbilities.put(player.getUniqueId(), newAbility);
    }

    @Override
    public void removeAbility(Player player) {
        playerAbilities.remove(player.getUniqueId());
    }

    @Override
    public boolean hasAbility(Player player) {
        return playerAbilities.containsKey(player.getUniqueId());
    }

    @Override
    public boolean hasAbility(Player player, String abilityName) {
        return playerAbilities.getOrDefault(player.getUniqueId(), "").equalsIgnoreCase(abilityName);
    }

    @Override
    public boolean hasAbility(Player player, AbilityBase ability) {
        return hasAbility(player, ability.getName());
    }

    @Override
    public void registerAbility(Player player, AbilityBase ability) {
        registerAbility(player, ability.getName());
    }

    @Override
    public void registerAbility(Player player, String ability) {
        playerAbilities.put(player.getUniqueId(), ability);
    }

    @Override
    public void unRegisterAbility(Player player) {
        removeAbility(player);
    }

    @Override
    public void saveAbilities(Player player) {
        // Implement persistence logic here (e.g., save to database or config)
    }

    @Override
    public void loadAbilities(Player player) {
        // Implement loading logic here (e.g., retrieve from database or config)
    }

    @Override
    public Optional<AbilityBase> getAbility(Player player) {
        return Optional.ofNullable(playerAbilities.get(player.getUniqueId()))
                .flatMap(abilityManager::getAbility);
    }

    @Override
    public Set<UUID> getPlayersWithAbilityByName(String name) {
        String lowerName = name.toLowerCase();

        return playerAbilities.entrySet().stream()
                .filter(entry -> entry.getValue().toLowerCase().equals(lowerName))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
