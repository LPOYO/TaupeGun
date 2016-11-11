package fr.lpoyo.taupegun.core.player;

import fr.lpoyo.taupegun.core.TaupeGun;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class PlayerManager {

    private TaupeGun pl;
    private List<TaupePlayer> players;

    public PlayerManager(TaupeGun pl) {
        this.pl = pl;
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(new TaupePlayer(player));
    }

    public void removePlayer(Player player) {
        players.remove(getTaupePlayer(player));
    }

    public TaupePlayer getTaupePlayer(Player player) {
        return players.stream().filter(taupePlayer -> taupePlayer.getUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }
}
