package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by ROMAIN on 15/11/2016.
 */
public class GameListener extends TaupeEvent {

    public GameListener(TaupeGun pl) {
        super(pl);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {/*
        if (!getGame().isDamage() && event.getEntity() instanceof Player)
            event.setCancelled(true);
            */
    }
}
