package me.a8kj.battlestreaks.manager;

import me.a8kj.battlestreaks.ability.AbilityBase;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import org.bukkit.entity.Player;
import java.util.*;

public class PlayerAbilityManagerImpl implements PlayerAbilityManager {
    private final AbilityManager abilityManager;
    private final Map<UUID, Set<String>> playerAbilities = new HashMap<>(); // Player UUID to ability names
    @Override
    public void activatePlayerAbility(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activatePlayerAbility'");
    }
    @Override
    public void deactivatePlayerAbility(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactivatePlayerAbility'");
    }
    @Override
    public void replaceAbility(Player player, AbilityBase newAbility) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replaceAbility'");
    }
    @Override
    public void replaceAbility(Player player, String newAbility) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replaceAbility'");
    }
    @Override
    public void removeAbility(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAbility'");
    }
    @Override
    public boolean hasAbility(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasAbility'");
    }
    @Override
    public boolean hasAbility(Player player, String abilityName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasAbility'");
    }
    @Override
    public boolean hasAbility(Player player, AbilityBase ability) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasAbility'");
    }
    @Override
    public void registerAbility(Player player, AbilityBase ability) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerAbility'");
    }
    @Override
    public void registerAbility(Player player, String ability) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerAbility'");
    }
    @Override
    public void unRegisterAbility(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unRegisterAbility'");
    }
    @Override
    public boolean isAbilityOnCooldown(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAbilityOnCooldown'");
    }
    @Override
    public void saveAbilities(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAbilities'");
    }
    @Override
    public void loadAbilities(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAbilities'");
    }
    @Override
    public AbilityBase getAbility(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAbility'");
    }

}
