package me.a8kj.battlestreaks.plugin;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.ability.impl.BlindingBurst;
import me.a8kj.battlestreaks.ability.impl.ChargedStrike;
import me.a8kj.battlestreaks.ability.impl.DashOfFury;
import me.a8kj.battlestreaks.ability.impl.EarthquakeSlam;
import me.a8kj.battlestreaks.ability.impl.RampageSurge;
import me.a8kj.battlestreaks.ability.impl.Thunderstrike;
import me.a8kj.battlestreaks.configuration.Configuration;
import me.a8kj.battlestreaks.configuration.impl.DataConfig;
import me.a8kj.battlestreaks.configuration.impl.DefaultConfig;
import me.a8kj.battlestreaks.manager.AbilityManagerImpl;
import me.a8kj.battlestreaks.manager.PlayerAbilityManagerImpl;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import me.a8kj.battlestreaks.recipe.crafts.CraftKillMarkListener;
import me.a8kj.battlestreaks.recipe.crafts.CraftTotemFragmentListener;
import me.a8kj.battlestreaks.recipe.impl.KillMarkRecipe;
import me.a8kj.battlestreaks.recipe.impl.LifeCoreRecipe;
import me.a8kj.battlestreaks.recipe.impl.TotemFragmentRecipe;

@RequiredArgsConstructor
@Getter
public class PluginFacade {

    private final Logger logger;
    private final JavaPlugin plugin;

    private PlayerAbilityManager playerAbilityManager;
    private final AbilityManager abilityManager = new AbilityManagerImpl();
    private Configuration defaultConfiguration;
    private Configuration dataConfiguration;

    public void onLunch() {
        onPreLunch();
    }

    public void onPreLunch() {
        registerConfigurations();
        registerRecipes();
        registerCraftRecipeListeners();
        registerListeners();
        registerCommands();
        registerAbilities();
        playerAbilityManager = new PlayerAbilityManagerImpl(abilityManager);
    }

    private void registerAbilities() {
        this.abilityManager.registerAbility(new BlindingBurst());
        this.abilityManager.registerAbility(new ChargedStrike());
        this.abilityManager.registerAbility(new DashOfFury());
        this.abilityManager.registerAbility(new EarthquakeSlam());
        this.abilityManager.registerAbility(new RampageSurge());
        this.abilityManager.registerAbility(new Thunderstrike());
    }

    public void onStop() {

    }

    private void registerCommands() {

    }

    private void registerListeners() {

    }

    private void registerCraftRecipeListeners() {
        new CraftKillMarkListener(plugin);
        new CraftTotemFragmentListener(plugin);
        new CraftKillMarkListener(plugin);
    }

    private void registerRecipes() {
        new LifeCoreRecipe(plugin).register();
        new KillMarkRecipe(plugin).register();
        new TotemFragmentRecipe(plugin);
    }

    private void registerConfigurations() {
        this.dataConfiguration = new DataConfig(plugin);
        this.dataConfiguration.load();
        this.defaultConfiguration = new DefaultConfig(plugin);
        this.defaultConfiguration.load();
    }

}
