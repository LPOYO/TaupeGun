package fr.lpoyo.taupegun.tasks;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.utils.Title;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class LobbyTask extends TaupeRunnable {

    private int[] counts = new int[]{120, 90, 60, 30 , 20, 15, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

    private int time = 19;

    public LobbyTask(TaupeGun pl) {
        super(pl, Task.LOBBYTASK, 120);
    }

    @Override
    public void run() {
        time++;

        Game game = getGame();

        // AUTO START PAS VRAIMENT UTIL POUR UN TAUPE GUN


        /*if (Bukkit.getOnlinePlayers().size() < game.getMinPlayers() && !TaupeGun.debug) {
            cancelTask();
            return;
        }

        if (!task.isRunning()) {
            cancelTask();
            return;
        }

        if (time == 20) {
            if (game.getMaxPlayers() - Bukkit.getOnlinePlayers().size() <= 2 && count > 10)
                count = 10;

            else if (game.getMaxPlayers() - Bukkit.getOnlinePlayers().size() <= 4 && count > 60)
                count = 60;

            if (ArrayUtils.contains(counts, count)) {
                if (count == 0) {
                    game.start();
                    cancelTask();
                    return;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, "§6La partie commence dans §a" + count + (count <= 1 ? " §6seconde" : " §6secondes"), "§ePréparez vous !", 20);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1F, 1F);
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setLevel(count);
            }

            count--;
            time = 0;
        }*/
    }

}
