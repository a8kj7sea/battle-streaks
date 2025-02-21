package me.a8kj.battlestreaks.plugin;

import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.battlestreaks.ability.AbilityManager;
import me.a8kj.battlestreaks.ability.impl.BlindingBurst;
import me.a8kj.battlestreaks.ability.impl.ChargedStrike;
import me.a8kj.battlestreaks.ability.impl.DashOfFury;
import me.a8kj.battlestreaks.ability.impl.EarthquakeSlam;
import me.a8kj.battlestreaks.ability.impl.RampageSurge;
import me.a8kj.battlestreaks.ability.impl.Thunderstrike;
import me.a8kj.battlestreaks.command.ReloadConfigCommand;
import me.a8kj.battlestreaks.configuration.Configuration;
import me.a8kj.battlestreaks.configuration.impl.DataConfig;
import me.a8kj.battlestreaks.configuration.impl.DefaultConfig;
import me.a8kj.battlestreaks.cooldown.CooldownTime;
import me.a8kj.battlestreaks.effect.NegativeEffectManager;
import me.a8kj.battlestreaks.effect.impl.GlowingAndAllEffects;
import me.a8kj.battlestreaks.effect.impl.JumpAndSlowFallEffect;
import me.a8kj.battlestreaks.effect.impl.MiningFatigueEffect;
import me.a8kj.battlestreaks.effect.impl.SlownessEffect;
import me.a8kj.battlestreaks.effect.impl.WeaknessEffect;
import me.a8kj.battlestreaks.listener.impl.EntityDamageByEntityListener;
import me.a8kj.battlestreaks.listener.impl.PlayerActiveListener;
import me.a8kj.battlestreaks.listener.impl.PlayerConnectionListeners;
import me.a8kj.battlestreaks.listener.impl.PlayerEffectAppliedListener;
import me.a8kj.battlestreaks.listener.impl.PlayerInteractListener;
import me.a8kj.battlestreaks.listener.impl.PlayerKillStreakListener;
import me.a8kj.battlestreaks.listener.impl.PlayerLivesListener;
import me.a8kj.battlestreaks.listener.impl.PlayerMoveListener;
import me.a8kj.battlestreaks.listener.impl.PlayerRespawnListener;
import me.a8kj.battlestreaks.manager.AbilityManagerImpl;
import me.a8kj.battlestreaks.manager.NegativeEffectManagerImpl;
import me.a8kj.battlestreaks.manager.PlayerAbilityManagerImpl;
import me.a8kj.battlestreaks.player.PlayerAbilityManager;
import me.a8kj.battlestreaks.recipe.crafts.CraftKillMarkListener;
import me.a8kj.battlestreaks.recipe.crafts.CraftLifeCoreListener;
import me.a8kj.battlestreaks.recipe.crafts.CraftTotemFragmentListener;
import me.a8kj.battlestreaks.recipe.impl.KillMarkRecipe;
import me.a8kj.battlestreaks.recipe.impl.LifeCoreRecipe;
import me.a8kj.battlestreaks.recipe.impl.TotemFragmentRecipe;
import me.a8kj.battlestreaks.recipe.interacts.KillMarksInteractListener;
import me.a8kj.battlestreaks.recipe.interacts.LifeCoreInteractListener;

@RequiredArgsConstructor
@Getter
public class PluginFacade {

    private final Logger logger;
    private final JavaPlugin plugin;

    private PlayerAbilityManager playerAbilityManager;
    private final AbilityManager abilityManager = new AbilityManagerImpl();
    private final NegativeEffectManager effectManager = new NegativeEffectManagerImpl();
    private Configuration defaultConfiguration;
    private Configuration dataConfiguration;

    @Getter
    private static Set<UUID> playersInLivesMode = Sets.newHashSet();

    public void onLunch() {
        onPreLunch();
    }

    private void onPreLunch() {
        registerConfigurations();
        registerRecipes();
        registerRecipeListeners();
        registerListeners();
        registerCommands();
        registerAbilities();
        playerAbilityManager = new PlayerAbilityManagerImpl(abilityManager, this);
        registerListeners();
        registerNegativeEffects();
    }

    public void onStop() {

    }

    public void addPlayerToLivesMode(Player player) {
        playersInLivesMode.add(player.getUniqueId());
    }

    public void removePlayerFromLivesMode(Player player) {
        playersInLivesMode.remove(player.getUniqueId());
    }

    private void registerAbilities() {
        this.abilityManager.registerAbility("blinding_burst",
                new BlindingBurst("blinding_burst", new CooldownTime(0, 60), 999, 12));
        this.abilityManager.registerAbility("charged_strike",
                new ChargedStrike("charged_strike", new CooldownTime(0, 60), 6, 5, this));
        this.abilityManager.registerAbility("dash", new DashOfFury("dash", new CooldownTime(0, 30), this, 1, 4));
        this.abilityManager.registerAbility("earthquake_slam",
                new EarthquakeSlam("earthquake_slam", new CooldownTime(0, 70), 7, 5, this));
        this.abilityManager.registerAbility("rampage_surge",
                new RampageSurge("rampage_surge", new CooldownTime(0, 50), this, 9, 7));
        this.abilityManager.registerAbility("thunderstrike",
                new Thunderstrike("thunderstrike", new CooldownTime(0, 200), 12, 9));
    }

    private void registerNegativeEffects() {
        DefaultConfig defaultConfig = (DefaultConfig) this.getDataConfiguration();
        String[] effects = { "slowness", "weakness", "mining_fatigue", "jump_slow_fall", "glowing_all" };

        for (String effectName : effects) {
            if (defaultConfig.isEffectEnabled(effectName)) {
                int requiredLives = defaultConfig.getRequiredLivesForEffect(effectName);
                switch (effectName.toLowerCase()) {
                    case "slowness":
                        this.getEffectManager().registerNegativeEffect(effectName,
                                new SlownessEffect(requiredLives, effectName));
                        break;
                    case "weakness":
                        this.getEffectManager().registerNegativeEffect(effectName,
                                new WeaknessEffect(requiredLives, effectName));
                        break;
                    case "mining_fatigue":
                        this.getEffectManager().registerNegativeEffect(effectName,
                                new MiningFatigueEffect(requiredLives, effectName));
                        break;
                    case "jump_slow_fall":
                        this.getEffectManager().registerNegativeEffect(effectName,
                                new JumpAndSlowFallEffect(requiredLives, effectName));
                        break;
                    case "glowing_all":
                        this.getEffectManager().registerNegativeEffect(effectName,
                                new GlowingAndAllEffects(requiredLives, effectName));
                        break;
                    default:
                        System.out.println("Effect not recognized: " + effectName);
                        break;
                }
            }
        }
    }

    private void registerCommands() {
        this.plugin.getCommand("bsreload").setExecutor(new ReloadConfigCommand(this));
    }

    private void registerListeners() {
        new PlayerActiveListener(this).register();
        new PlayerConnectionListeners(this).register();
        //
        new PlayerInteractListener(this).register();
        new PlayerKillStreakListener(this).register();
        new PlayerLivesListener(this).register();
        new PlayerMoveListener(this).register();
        new PlayerRespawnListener(this).register();
        new PlayerEffectAppliedListener(this).register();
        new EntityDamageByEntityListener(this).register();
    }

    private void registerRecipeListeners() {
        new CraftKillMarkListener(this).register();
        new CraftTotemFragmentListener(this).register();
        new CraftLifeCoreListener(this).register();

        new KillMarksInteractListener(this);
        new LifeCoreInteractListener(this);
    }

    private void registerRecipes() {
        new LifeCoreRecipe(plugin).register();
        new KillMarkRecipe(plugin).register();
        new TotemFragmentRecipe(plugin).register();
    }

    private void registerConfigurations() {
        this.dataConfiguration = new DataConfig(plugin);
        this.dataConfiguration.load();
        this.defaultConfiguration = new DefaultConfig(plugin);
        this.defaultConfiguration.load();
    }

}
