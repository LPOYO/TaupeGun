package fr.lpoyo.taupegun.tasks;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.core.player.PlayerManager;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.game.ScoreboardManager;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public abstract class TaupeRunnable extends BukkitRunnable {

    public TaupeGun pl;
    public Task task;
    public int count;

    public TaupeRunnable(TaupeGun pl, Task task, int count) {
        this.pl = pl;
        this.task = task;
        this.count = count;
        task.setRunning(true);
    }

    public void cancelTask() {
        cancel();
        task.setRunning(false);
    }

    public Game getGame() {
        return pl.getGame();
    }

    public ScoreboardManager getScoreboardManager() {
        return pl.getScoreboardManager();
    }

    public PlayerManager getPlayerManager() {
        return pl.getPlayerManager();
    }
}
