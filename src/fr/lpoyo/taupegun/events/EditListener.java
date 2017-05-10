package fr.lpoyo.taupegun.events;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.GameState;
import fr.lpoyo.taupegun.game.Kit;
import fr.lpoyo.taupegun.guis.KitEditGui;
import fr.lpoyo.taupegun.guis.MainGui;
import fr.lpoyo.taupegun.guis.TeamsGui;
import fr.lpoyo.taupegun.utils.item.CustomItem;
import org.bukkit.GameMode;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

/**
 * Created by ROMAIN on 11/11/2016.
 */
public class EditListener extends TaupeEvent {

    public EditListener(TaupeGun pl) {
        super(pl);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (KitEditGui.edits.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§aClique avec le verre pour valider");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (KitEditGui.edits.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§aClique avec le verre pour valider");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (KitEditGui.edits.containsKey(event.getPlayer())) {
            event.setCancelled(true);

            if (event.getItem() != null && event.getItem().getType() == Material.STAINED_GLASS_PANE && event.getItem().getDurability() == 13) {
                Player p = event.getPlayer();
                Kit kit = KitEditGui.edits.get(p);
                for (ItemStack itemStack : p.getInventory()) {
                    if (itemStack == null)
                        continue;

                    if (itemStack.getType() == Material.STAINED_GLASS_PANE && itemStack.getDurability() == 13)
                        continue;

                    kit.getItems().add(itemStack);
                    pl.getConfigUtils().saveKit(kit);
                }

                p.getInventory().clear();
                p.getInventory().setItem(0, new CustomItem(Material.PAPER, "§6Equipes", Collections.singletonList("§7Ouvre le menu des équieps")).create());
                if (p.isOp() || p.hasPermission("taupegun.menu"))
                    p.getInventory().setItem(4, new CustomItem(Material.NETHER_STAR, "§6Menu", Collections.singletonList("§7Ouvre le menu principal")).create());

                p.setGameMode(GameMode.ADVENTURE);
                p.setInvulnerable(false);
                KitEditGui.edits.remove(p);
                new KitEditGui(p, kit);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (KitEditGui.edits.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§aClique avec le verre pour valider");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (KitEditGui.edits.containsKey(event.getPlayer())) {
            event.getPlayer().teleport(event.getFrom());
            event.getPlayer().sendMessage("§aClique avec le verre pour valider");
        }
    }
}
