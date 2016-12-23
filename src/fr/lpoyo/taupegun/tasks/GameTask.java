package fr.lpoyo.taupegun.tasks;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.teams.TaupeTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class GameTask extends TaupeRunnable {

    private int minutes = 19;
    private int seconds = 59;
    private int episode = 1;

    public GameTask(TaupeGun pl) {
        super(pl, Task.GAMETASK, 0);
    }

    @Override
    public void run() {
        count ++;
        seconds --;

        if (seconds <= -1) {
            seconds = 59;
            minutes--;
        }

        if (minutes <= -1) {
            minutes = 19;
            seconds = 59;
            episode++;
        }

        if (count >= pl.getGame().getMode().getRevelationTime() && !pl.getGame().isTaupesRevealed()) {
            for (Map.Entry<Player, TaupeTeam> entry : pl.getGame().getTaupes().entrySet()) {
                Player player = entry.getKey();
                player.sendMessage("§c----------");
                player.sendMessage("§6Vous êtes une taupe ! Roulez votre équipe et rejoignez les autre taupes.");
                player.sendMessage("§6Tapez /reveal pour vous révéler à tout moment.");
                player.sendMessage("§6Tapez /t message pour envoyer un message aux autres taupes.");
                player.sendMessage("§6Vous avez le kit §cAérien §6! Tapez /claim pour le recevoir.");
                player.sendMessage("§6Les items de votre kit vont dropper à vos pieds, ne vous faîtes pas repérer en le claimant.");
                player.sendMessage("§c----------");
            }
            pl.getGame().setTaupesRevealed(true);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            pl.getScoreboardManager().updateGame(player, minutes, seconds, episode);
        }
    }

}
