package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.GameState;
import fr.lpoyo.taupegun.tasks.LobbyTask;
import fr.lpoyo.taupegun.tasks.Task;
import fr.lpoyo.taupegun.utils.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class PlayerConnect extends TaupeEvent {

    public PlayerConnect(TaupeGun pl) {
        super(pl);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (Bukkit.getOnlinePlayers().size() >= getGame().getMaxPlayers())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "La partie est pleine !");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (GameState.isState(GameState.LOBBY)) {
            getPlayerManager().addPlayer(p);
            event.setJoinMessage(getGame().getPrefix() + p.getName() + " a rejoint la partie ! §e(§6" + Bukkit.getOnlinePlayers().size() + "§e/§6" + getGame().getMaxPlayers() + "§e)");

            p.getInventory().setItem(0, new CustomItem(Material.PAPER, "§6Equipes", Collections.singletonList("§7Ouvre le menu des équieps")).create());
            if (p.isOp() || p.hasPermission("taupegun.menu"))
                p.getInventory().setItem(4, new CustomItem(Material.NETHER_STAR, "§6Menu", Collections.singletonList("§7Ouvre le menu principal")).create());

            if (!Task.LOBBYTASK.isRunning())
                new LobbyTask(pl).runTaskTimer(pl, 0, 20);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        getPlayerManager().removePlayer(p);

        event.setQuitMessage(getGame().getPrefix() + p.getName() + " a quitté la partie ! §e(§6" + (Bukkit.getOnlinePlayers().size() - 1) + "§e/§6" + getGame().getMaxPlayers() + "§e)");
    }
}
