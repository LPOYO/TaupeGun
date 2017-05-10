package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by ROMAIN on 15/11/2016.
 */
public class GameListener extends TaupeEvent {

    public GameListener(TaupeGun pl) {
        super(pl);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!getGame().isDamage() && event.getEntity() instanceof Player)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (GameState.isState(GameState.GAME)) {
            event.setDeathMessage(event.getEntity().getDisplayName() + (event.getDeathMessage().contains("was slain") ? " §cà été tué !" : "§cest mort !"));

        }
    }
}
