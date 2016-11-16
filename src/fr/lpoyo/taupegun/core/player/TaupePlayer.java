package fr.lpoyo.taupegun.core.player;

import fr.lpoyo.taupegun.game.teams.TaupeTeam;
import lombok.Data;
import org.bukkit.GameMode;
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
    private TaupeTeam team;

    public TaupePlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();

        player.setGameMode(GameMode.ADVENTURE);
        player.setLevel(0);
        player.getInventory().clear();
        for (int i = 0; i < 20; i++) {
            player.sendMessage(" ");
        }
        player.setHealth(20.0D);
        player.setFoodLevel(40);
    }
}
