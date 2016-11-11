package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.GameState;
import fr.lpoyo.taupegun.tasks.LobbyTask;
import fr.lpoyo.taupegun.tasks.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
            if (!Task.LOBBYTASK.isRunning())
                new LobbyTask(pl).runTaskTimer(pl, 0, 0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        getPlayerManager().removePlayer(p);

        event.setQuitMessage(getGame().getPrefix() + p.getName() + " a quitté la partie ! §e(§6" + (Bukkit.getOnlinePlayers().size() - 1) + "§e/§6" + getGame().getMaxPlayers() + "§e)");
    }
}
