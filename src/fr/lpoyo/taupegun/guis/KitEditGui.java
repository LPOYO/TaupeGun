package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Kit;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.FormattedInventoryGUI;
import fr.lpoyo.taupegun.utils.gui.RunnableInventoryGUI;
import fr.lpoyo.taupegun.utils.item.CustomItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class KitEditGui {

    private int slot = 0;

    public static Map<Player, Kit> edits = new HashMap<>();

    public KitEditGui(Player p, Kit kit) {
        TaupeGun pl = TaupeGun.getInstance();
        RunnableInventoryGUI gui = new RunnableInventoryGUI(7, "§6Kit " + kit.getName()) {
            @Override
            public void update() {
                options.clear();
                inventory.clear();
                slot = 0;

                kit.getItems().forEach(is -> {
                        setOption(slot, new AbstractGuiOption(is, "§7Clique gauche pour enchanter", "§7Clique droit pour supprimer") {
                            @Override
                            public void onSelection(Player selecter, InventoryAction selectionType) {
                                if (selectionType == InventoryAction.PICKUP_ALL) {
                                    closeGUI(selecter);
                                    new EnchantItemGui(selecter, kit, is);
                                }

                                else if (selectionType == InventoryAction.PICKUP_HALF) {
                                    for (int i = 0; i < kit.getItems().size(); i++) {
                                        if (kit.getItems().get(i).isSimilar(is))
                                            kit.getItems().remove(i);
                                    }

                                    pl.getConfigUtils().saveKit(kit);
                                }
                            }
                        });

                    slot++;
                });

                setOption(52, new AbstractGuiOption(Material.DIAMOND, "§aAjouter", 1, 0, null, false, false, "§7Clique pour ajouter des items") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                        closeGUI(selecter);
                        selecter.playSound(selecter.getLocation(), Sound.BLOCK_NOTE_PLING, 5F, 5F);
                        edits.put(selecter, kit);
                        selecter.getInventory().clear();
                        selecter.sendMessage("§c=================================================");
                        selecter.sendMessage("§aMets les items a ajouter dans ton inventaire");
                        selecter.sendMessage("§aClique avec le verre pour valider");
                        selecter.sendMessage("§c=================================================");
                        selecter.setGameMode(GameMode.CREATIVE);
                        selecter.setInvulnerable(true);
                        selecter.getInventory().setItem(4, new CustomItem(Material.STAINED_GLASS_PANE, "§aValider", null, 1, (byte)13).create());
                    }
                });

                setOption(53, new AbstractGuiOption(Material.BARRIER, "§cRetour", 1, 0, null, false, false) {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                        new KitsGui(selecter);
                    }
                });
            }
        };

        gui.openGUI(p);
    }

}