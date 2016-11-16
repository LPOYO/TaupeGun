package fr.lpoyo.taupegun.game.teams;

import fr.lpoyo.taupegun.core.TaupeGun;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class TaupeTeam {

    private TaupeGun pl;

    @Getter
    private Team team;
    @Getter
    private String name, displayName, prefix;
    @Getter
    private ItemStack itemStack;
    @Getter
    private Color color;
    @Getter @Setter
    private int banners;
    @Getter @Setter
    private boolean enabled;


    public TaupeTeam(TaupeGun pl, String name, String displayName, String prefix, ItemStack itemStack, Color color) {
        this.pl = pl;
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.itemStack = itemStack;
        this.color = color;
        this.banners = 8;
        this.enabled = true;
    }

    public void register() {
        team = pl.getScoreboardManager().getBoard().registerNewTeam(name);
        team.setPrefix(prefix);
        team.setDisplayName(displayName);
        team.setSuffix("§r");
    }

    public void add(Player player) {
        player.setDisplayName(prefix + player.getName());
        player.setCustomName(prefix + player.getName());
        player.setCustomNameVisible(true);
        player.setPlayerListName(prefix + player.getName());


        pl.getPlayerManager().getTaupePlayer(player).setTeam(this);
        team.addEntry(player.getName());
    }

    public void remove(Player player) {
        player.setDisplayName(player.getName());
        player.setCustomName(player.getName());
        player.setCustomNameVisible(false);
        player.setPlayerListName(player.getName());

        pl.getPlayerManager().getTaupePlayer(player).setTeam(null);
        team.removeEntry(player.getName());
    }
}
