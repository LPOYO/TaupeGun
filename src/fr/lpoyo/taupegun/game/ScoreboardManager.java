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

    /*public void updateGame(Player player) {
        ScoreboardSign sb;
        if (sbs.containsKey(player.getUniqueId()))
            sb = sbs.get(player.getUniqueId());
        else {
            sb = new ScoreboardSign(player, "§6§lMINERS WAR");
            sb.create();
            sb.setLine(0, "§r");
            sb.setLine(5, "§r ");
            sb.setLine(7, "§r  ");
            sbs.put(player.getUniqueId(), sb);
        }

        if (red.isEnabled())
            sb.setLine(1, "§cRouge : §6" + red.getBanners() + "/8");
        if (blue.isEnabled())
            sb.setLine(2, "§bBleue : §6" + blue.getBanners() + "/8");
        if (green.isEnabled())
            sb.setLine(3, "§2Verte : §6" + green.getBanners() + "/8");
        if (yellow.isEnabled())
            sb.setLine(4, "§6Jaune : §6" + yellow.getBanners() + "/8");

        double kills = Statistic.KILL.get(player.getUniqueId());
        double deaths = Statistic.DEATH.get(player.getUniqueId());
        double ratio = kills / deaths;

        sb.setLine(6, "§bK/D/R: §a" + (int) kills + "/§c" + (int) deaths + "/§e" + (deaths == 0 ? kills : ratio));

        if (pl.getGameManager().getMinePlayer(player.getUniqueId()) != null)
            sb.setLine(8, "§aGolds : §6" + pl.getGameManager().getMinePlayer(player.getUniqueId()).getGolds());

        sbs.replace(player.getUniqueId(), sb);
    }*/

    public Scoreboard getBoard() {
        return Bukkit.getScoreboardManager().getMainScoreboard();
    }
}
