package fr.lpoyo.taupegun.core;

import fr.lpoyo.taupegun.commands.ClaimCommand;
import fr.lpoyo.taupegun.commands.TCommand;
import fr.lpoyo.taupegun.commands.RevealCommand;
import fr.lpoyo.taupegun.commands.TaupeCommand;
import fr.lpoyo.taupegun.core.player.PlayerManager;
import fr.lpoyo.taupegun.events.TaupeEvent;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.game.GameManager;
import fr.lpoyo.taupegun.game.ScoreboardManager;
import fr.lpoyo.taupegun.game.teams.TeamsManager;
import fr.lpoyo.taupegun.utils.ConfigUtils;
import fr.lpoyo.taupegun.utils.world.WorldBorderUtils;
import fr.lpoyo.taupegun.utils.world.WorldUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class TaupeGun extends JavaPlugin {

    public static boolean debug = true;
    @Getter
    private static TaupeGun instance;
    private boolean worldRegen = false;
    @Getter
    private Game game;
    @Getter
    private PlayerManager playerManager;
    @Getter
    private ScoreboardManager scoreboardManager;
    @Getter
    private TeamsManager teamsManager;
    @Getter
    private ConfigUtils configUtils;
    @Getter
    private WorldUtils worldUtils;
    @Getter
    private WorldBorderUtils worldBorderUtils;

    public static void log(String s) {
        Bukkit.getConsoleSender().sendMessage(s);
    }

    @Override
    public void onLoad() {
        instance = this;
        worldUtils = new WorldUtils(this, worldRegen);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configUtils = new ConfigUtils(this);
        scoreboardManager = new ScoreboardManager(this);
        teamsManager = new TeamsManager(this);
        game = new GameManager(this);
        playerManager = new PlayerManager(this);

        getCommand("taupe").setExecutor(new TaupeCommand(this));
        getCommand("reveal").setExecutor(new RevealCommand(this));
        getCommand("t").setExecutor(new TCommand(this));
        getCommand("claim").setExecutor(new ClaimCommand(this));

        log(game.getPrefix() + "Taupe Gun active !");

        worldBorderUtils = new WorldBorderUtils(this);
        worldBorderUtils.set(configUtils.getInt("monde.taille"), 0);

        TaupeEvent.registerEvents(this);
        Bukkit.getOnlinePlayers().forEach(o -> Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(o, null)));

        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("naturalRegeneration", "false"));
    }
}