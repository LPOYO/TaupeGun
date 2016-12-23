package fr.lpoyo.taupegun.game;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.core.player.TaupePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class ScoreboardManager {

    private TaupeGun pl;

    public ScoreboardManager(TaupeGun pl) {
        this.pl = pl;

        if (Bukkit.getScoreboardManager().getMainScoreboard().getObjective("vie") != null)
            Bukkit.getScoreboardManager().getMainScoreboard().getObjective("vie").unregister();

        Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
            @Override
            public void run() {
                /*Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives add vie health");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives setdisplay list vie"); */
            }
        }, 20);
    }

    public void updateLobby(TaupePlayer player) {
        Scoreboard sb;
        Objective o;
        if (player.getScoreboard() != null) {
            sb = player.getScoreboard();
            o = sb.getObjective("taupe");
        } else {
            sb = Bukkit.getScoreboardManager().getNewScoreboard();
            o = sb.registerNewObjective("taupe", "dummy");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName("Taupe Gun");

            Objective health = sb.registerNewObjective("vie", "health");
            health.setDisplayName("vie");
            health.setDisplaySlot(DisplaySlot.PLAYER_LIST);

            player.setScoreboard(sb);
        }

        sb.getEntries().stream().filter(s -> Bukkit.getPlayer(s) == null).forEach(sb::resetScores);

        o.getScore("§r ").setScore(-1);
        o.getScore("§7Joueurs : §r" + pl.getPlayerManager().getPlayers().size()).setScore(-2);
        o.getScore("§7Equipes : §r" + pl.getGame().getMode().getTeams()).setScore(-3);


        player.setScoreboard(sb);
        player.getPlayer().setScoreboard(player.getScoreboard());
    }

    public void updateGame(Player player, int minutes, int seconds, int episode) {
        Scoreboard sb = player.getScoreboard();
        Objective o = sb.getObjective("taupe");
        if (o == null) {
            sb = Bukkit.getScoreboardManager().getNewScoreboard();
            o = sb.registerNewObjective("taupe", "dummy");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName("Taupe Gun");

            Objective health = sb.registerNewObjective("vie", "health");
            health.setDisplayName("vie");
            health.setDisplaySlot(DisplaySlot.PLAYER_LIST);

            player.setScoreboard(sb);
        }

        sb.getEntries().stream().filter(s -> Bukkit.getPlayer(s) == null).forEach(sb::resetScores);

        o.getScore("§r ").setScore(-1);
        o.getScore("§7Episode : §r" + episode).setScore(-2);
        o.getScore(pl.getPlayerManager().getPlayers().size() + " §7joueurs").setScore(-3);
        o.getScore(pl.getGame().getMode().getTeams() + " §7equipes").setScore(-4);
        o.getScore("§r  ").setScore(-5);

        if (minutes < 10 && seconds < 10)
            o.getScore("§7Temps : §r0" + minutes
                    + "§7:§r0" + seconds).setScore(-6);
        else if (seconds < 10)
            o.getScore("§7Temps : §r" + minutes
                    + "§7:§r0" + seconds).setScore(-6);
        else if (minutes < 10)
            o.getScore("§7Temps : §r0" + minutes
                    + "§7:§r" + seconds).setScore(-6);
        else
            o.getScore("§7Temps : §r" + minutes
                    + "§7:§r" + seconds).setScore(-6);

        player.setScoreboard(sb);
        player.getPlayer().setScoreboard(player.getScoreboard());
    }

    public Scoreboard getBoard() {
        return Bukkit.getScoreboardManager().getMainScoreboard();
    }
}
