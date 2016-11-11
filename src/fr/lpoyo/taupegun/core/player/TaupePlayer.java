package fr.lpoyo.taupegun.core.player;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

/**
 * Created by ROMAIN on 11/11/2016.
 */
@Data
public class TaupePlayer {

    private Player player;
    private UUID uuid;
    private String name;
    private Scoreboard scoreboard;

    public TaupePlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }
}
