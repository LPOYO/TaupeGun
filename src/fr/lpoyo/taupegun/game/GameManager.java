package fr.lpoyo.taupegun.game;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class GameManager extends Game {

    private TaupeGun pl;

    public GameManager(TaupeGun pl) {
        this.pl = pl;
        setMaxPlayers(pl.getConfigUtils().getInt("joueurs.maximum"));
        setMinPlayers(pl.getConfigUtils().getInt("joueurs.minimum"));
        setPrefix(pl.getConfigUtils().getString("prefix").replaceAll("&", "§"));
    }

    @Override
    public void start() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Title.sendTitle(player, "§6Bonne chance !", "", 20);
        }
    }
}

