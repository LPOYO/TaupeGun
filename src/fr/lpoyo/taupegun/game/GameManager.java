package fr.lpoyo.taupegun.game;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.core.player.TaupePlayer;
import fr.lpoyo.taupegun.utils.ConfigUtils;
import fr.lpoyo.taupegun.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;

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
        GameState.setCurrentState(GameState.GAME);

        Bukkit.getWorlds().forEach(world -> world.setTime(0));

        Random r = new Random();
        for (TaupePlayer taupePlayer : pl.getPlayerManager().getPlayers()) {
            Title.sendTitle(taupePlayer.getPlayer(), "§6Bonne chance !", "", 20);

            int x = r.nextInt(pl.getWorldBorderUtils().getSize() / 2), z = r.nextInt(pl.getWorldBorderUtils().getSize() / 2);
            if (r.nextBoolean())
                x *= -1;
            if (r.nextBoolean())
                z *= -1;
            final Location loc = new Location(Bukkit.getWorlds().get(0), x, 200, z);
            loc.getChunk().load(true);

            Bukkit.getScheduler().runTaskLater(pl, () -> taupePlayer.getPlayer().teleport(loc), 20);

            taupePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        }

        Bukkit.broadcastMessage(getPrefix() + "§cDégats activés dans une minute !");
        Bukkit.getScheduler().runTaskLater(pl, () -> setDamage(true), 20 * 60);
    }
}

