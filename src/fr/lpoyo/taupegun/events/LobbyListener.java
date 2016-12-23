package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.GameState;
import fr.lpoyo.taupegun.guis.MainGui;
import fr.lpoyo.taupegun.guis.TeamsGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class LobbyListener extends TaupeEvent {

    public LobbyListener(TaupeGun pl) {
        super(pl);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);

            if (event.getItem() == null)
                return;

            if (event.getItem().getType() == Material.NETHER_STAR) {
                new MainGui(event.getPlayer());
            }

            if (event.getItem().getType() == Material.PAPER) {
                new TeamsGui(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (GameState.isState(GameState.LOBBY)) {
            event.setCancelled(true);

            if (event.getClickedInventory() == null || event.getCurrentItem() == null)
                return;

            if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
                if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
                    new MainGui((Player) event.getWhoClicked());
                }

                if (event.getCurrentItem().getType() == Material.PAPER) {
                    new TeamsGui((Player) event.getWhoClicked());
                }
            }
        }
    }
}
