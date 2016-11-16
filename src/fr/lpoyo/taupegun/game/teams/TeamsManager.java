package fr.lpoyo.taupegun.game.teams;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.core.player.TaupePlayer;
import fr.lpoyo.taupegun.game.Game;
import fr.lpoyo.taupegun.utils.item.CustomItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class TeamsManager {

    private TaupeGun pl;
    @Getter
    private List<TaupeTeam> teams;
    @Getter
    private List<ChatColor> prefixs;

    public TeamsManager(TaupeGun pl) {
        this.pl = pl;

        teams = new ArrayList<>();

        //TAUPE
        teams.add(new TaupeTeam(pl, "Taupe1", "Equipe Taupe", "§4",
                new CustomItem(Material.REDSTONE_ORE, "§4Equipe Taupe", null).create(), Color.RED));

        teams.add(new TaupeTeam(pl, "Taupe2", "Equipe Taupe", "§4",
                new CustomItem(Material.REDSTONE_ORE, "§4Equipe Taupe", null).create(), Color.RED));


        //NORMALS
        teams.add(new TaupeTeam(pl, "Bleu", "Equipe Bleue", "§b",
                new CustomItem(Material.STAINED_GLASS, "§bEquipe Bleue", null, 1, (byte)11).create(), Color.BLUE));

        teams.add(new TaupeTeam(pl, "Vert", "Equipe Verte", "§2",
                new CustomItem(Material.STAINED_GLASS, "§2Equipe Verte", null, 1, (byte)13).create(), Color.GREEN));

        teams.add(new TaupeTeam(pl, "Jaune", "Equipe Jaune", "§e",
                new CustomItem(Material.STAINED_GLASS, "§eEquipe Jaune", null, 1, (byte)4).create(), Color.YELLOW));

        teams.add(new TaupeTeam(pl, "Violet", "Equipe Violette", "§5",
                new CustomItem(Material.STAINED_GLASS, "§5Equipe Violette", null, 1, (byte)2).create(), Color.PURPLE));

        teams.add(new TaupeTeam(pl, "Gris", "Equipe Grise", "§7",
                new CustomItem(Material.STAINED_GLASS, "§7Equipe Grise", null, 1, (byte)15).create(), Color.YELLOW));

        teams.add(new TaupeTeam(pl, "Rose", "Equipe Rose", "§d",
                new CustomItem(Material.STAINED_GLASS, "§dEquipe Rose", null, 1, (byte)6).create(), Color.FUCHSIA));

        teams.add(new TaupeTeam(pl, "Orange", "Equipe Orange", "§6",
                new CustomItem(Material.STAINED_GLASS, "§6Equipe Orange", null, 1, (byte)1).create(), Color.ORANGE));

    }

    public void setMode(Game.Mode mode) {
        pl.getScoreboardManager().getBoard().getTeams().forEach(Team::unregister);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setCustomName(player.getName());
            player.setCustomNameVisible(false);
            player.setPlayerListName(player.getName());
            player.setDisplayName(player.getName());
        }

        for (int i = 0; i < mode.getTeams(); i++) {
            teams.get(i + 2).register();
        }

        for (int i = 0; i < mode.getTaupeTeams(); i++) {
            teams.get(i).register();
        }
    }
    /*public void autoTeam(TaupePlayer player) {
        if (player.getTeam() != null)
            return;

        Map<TaupeTeam, Integer> map = new HashMap<>();
        map.put(red, red.getTeam().getEntries().size());
        map.put(blue, blue.getTeam().getEntries().size());
        map.put(green, green.getTeam().getEntries().size());
        map.put(yellow, yellow.getTeam().getEntries().size());

        sortByValue(map).keySet().iterator().next().add(player.getPlayer());
    }*/

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    public int getTeamsNumber() {
        int r = 0;
        for (TaupeTeam team : teams) {
            if (team.isEnabled())
                r++;
        }

        return r;
    }
}