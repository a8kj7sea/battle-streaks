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

    private final AbilityManager abilityManager = new AbilityManagerImpl();

    private Configuration defaultConfiguration;
    private Configuration dataConfiguration;

    public void onLunch() {
        onPreLunch();
    }

    public void onPreLunch() {
        registerAbilities();
        registerConfigurations();
        registerRecipes();
        registerCraftRecipeListeners();
        registerListeners();
        registerCommands();
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

    private void registerAbilities() {
        abilityManager.registerAbility(new DashOfFury());
        abilityManager.registerAbility(new ChargedStrike());
        abilityManager.registerAbility(new BlindingBurst());
        abilityManager.registerAbility(new RampageSurge());
        abilityManager.registerAbility(new EarthquakeSlam());
        abilityManager.registerAbility(new Thunderstrike());
    }
}
