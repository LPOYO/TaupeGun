package fr.lpoyo.taupegun.game;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.core.player.TaupePlayer;
import fr.lpoyo.taupegun.game.teams.TaupeTeam;
import fr.lpoyo.taupegun.tasks.GameTask;
import fr.lpoyo.taupegun.tasks.Task;
import fr.lpoyo.taupegun.utils.ConfigUtils;
import fr.lpoyo.taupegun.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class GameManager extends Game {

    private TaupeGun pl;

    public GameManager(TaupeGun pl) {
        this.pl = pl;
        ConfigUtils cu = pl.getConfigUtils();
        setMode(Mode.byId(cu.getInt("mode")));
        setPrefix(cu.getString("prefix").replaceAll("&", "§") + " ");
        setTaupes(new HashMap<>());
        setTaupesKit(new HashMap<>());
        setDisconnecteds(new ArrayList<>());
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
        pl.getConfig().set("mode", mode.getId());
        pl.getTeamsManager().setMode(mode);
        setMaxPlayers(getMode().getPlayers());
    }

    @Override
    public void start() {

        if (!TaupeGun.debug) {
            if (pl.getPlayerManager().getPlayers().size() < getMode().getPlayers()) {
                Bukkit.broadcastMessage("§6§l=============================================");
                Bukkit.broadcastMessage("§c§lErreur ! Impossible de lancer la partie !");
                Bukkit.broadcastMessage("§c§lIl manque " + (getMode().getPlayers() - pl.getPlayerManager().getPlayers().size()) + " joueurs !");
                Bukkit.broadcastMessage("§6§l=============================================");
                return;
            }

            boolean teamChoosen = true;
            for (TaupePlayer taupePlayer : pl.getPlayerManager().getPlayers())
                if (taupePlayer.getTeam() == null)
                    teamChoosen = false;
            if (!teamChoosen) {
                Bukkit.broadcastMessage("§6§l=============================================");
                Bukkit.broadcastMessage("§c§lErreur ! Impossible de lancer la partie !");
                Bukkit.broadcastMessage("§c§lTous les joueurs n'ont pas choisi d'équipe !");
                Bukkit.broadcastMessage("§6§l=============================================");
                return;
            }
        }

        Task.LOBBYTASK.setRunning(false);
        new GameTask(pl).runTaskTimer(pl, 0, 20);
        GameState.setCurrentState(GameState.GAME);
        Bukkit.getWorlds().forEach(world -> world.setTime(0));

        Random r = new Random();
        for (Team team : pl.getScoreboardManager().getBoard().getTeams()) {
            if (team.getEntries().isEmpty())
                continue;

            int x = r.nextInt(pl.getWorldBorderUtils().getSize() / 2), z = r.nextInt(pl.getWorldBorderUtils().getSize() / 2);
            if (r.nextBoolean())
                x *= -1;
            if (r.nextBoolean())
                z *= -1;
            final Location loc = new Location(Bukkit.getWorlds().get(0), x, 200, z);
            loc.getChunk().load(true);

            for (String s : team.getEntries()) {
                Player p = Bukkit.getPlayer(s);
                Title.sendTitle(p, "§6Bonne chance !", "", 20);
                Bukkit.getScheduler().runTaskLater(pl, () -> p.teleport(loc), 20);
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(loc);
            }

            Random rand = new Random();
            for (int i = 0; i < getMode().getTaupeTeams(); i++) {
                List<String> players = new ArrayList<>(team.getEntries());
                Player p = Bukkit.getPlayer(players.get(rand.nextInt(players.size())));

                while (isTaupe(p))
                    p = Bukkit.getPlayer(players.get(rand.nextInt(players.size())));

                if (mode.getTaupeTeams() == 1)
                    getTaupes().put(p, pl.getTeamsManager().getTeams().get(0));
                else {
                    List<TaupeTeam> teams = pl.getTeamsManager().getTeams();
                    if (teams.get(1).getTeam().getSize() > teams.get(0).getTeam().getSize())
                        getTaupes().put(p, teams.get(0));
                    else
                        getTaupes().put(p, teams.get(1));

                }
            }
        }

        Bukkit.broadcastMessage(getPrefix() + "§cDégats activés dans une minute !");
        Bukkit.getScheduler().runTaskLater(pl, () -> setDamage(true), 20 * 60);
    }

    @Override
    public boolean isTaupe(Player p) {
        return getTaupes().containsKey(p);
    }

    @Override
    public void setTaupeKit(Player player, Kit kit) {
        getTaupesKit().putIfAbsent(player, kit);
    }

    @Override
    public Kit getTaupeKit(Player player) {
        return getTaupesKit().get(player);
    }
}

