package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.core.player.PlayerManager;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.game.ScoreboardManager;
import fr.lpoyo.taupegun.utils.ConfigUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

/**
 * Created by ROMAIN on 11/11/2016.
 */
@AllArgsConstructor
public class TaupeEvent implements Listener {

    public static void registerEvents(TaupeGun pl) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerConnect(pl), pl);
    }

    public TaupeGun pl;

    public PlayerManager getPlayerManager() {
        return pl.getPlayerManager();
    }

    public ConfigUtils getConfigUtils() {
        return pl.getConfigUtils();
    }

    public Game getGame() {
        return pl.getGame();
    }

    public ScoreboardManager getScoreboardManager() {
        return pl.getScoreboardManager();
    }
}