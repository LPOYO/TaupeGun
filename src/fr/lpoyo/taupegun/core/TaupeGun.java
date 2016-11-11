package fr.lpoyo.taupegun.core;

import fr.lpoyo.taupegun.core.player.PlayerManager;
import fr.lpoyo.taupegun.events.TaupeEvent;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.game.GameManager;
import fr.lpoyo.taupegun.game.ScoreboardManager;
import fr.lpoyo.taupegun.utils.ConfigUtils;
import fr.lpoyo.taupegun.utils.WorldBorderUtils;
import fr.lpoyo.taupegun.utils.WorldUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ROMAIN on 11/11/2016.
 */
@Getter
public class TaupeGun extends JavaPlugin {

    public static boolean debug = true;

    private TaupeGun instance;
    private boolean worldRegen = true;
    private Game game;
    private PlayerManager playerManager;
    private ScoreboardManager scoreboardManager;
    private ConfigUtils configUtils;
    private WorldUtils worldUtils;
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
        game = new GameManager(this);
        playerManager = new PlayerManager(this);
        scoreboardManager = new ScoreboardManager(this);

        log(game.getPrefix() + "Taupe Gun active !");

        worldBorderUtils = new WorldBorderUtils(this);
        worldBorderUtils.set(configUtils.getInt("monde.taille"), 0);

        TaupeEvent.registerEvents(this);
        Bukkit.getOnlinePlayers().forEach(o -> Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(o, null)));

        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("naturalRegeneration", "false"));
    }
}