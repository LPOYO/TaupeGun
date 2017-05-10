package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.game.Kit;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.FormattedInventoryGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ROMAIN on 29/10/2016.
 */
public class KitsGui {

    private int x = 1, y = 1;

    public KitsGui(Player p) {
        TaupeGun pl = TaupeGun.getInstance();
        FormattedInventoryGUI gui = new FormattedInventoryGUI(5, "§6Kits réservés aux taupes");
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            gui.setOption(i, 0, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, null, false, false, "§7Ca va ?") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {
                }
            });

            if (i <= 4)
                gui.setOption(0, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, null, false, false, "§7Ca va ?") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                    }
                });

            gui.setOption(i, 4, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, null, false, false, "§7Ca va ?") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {
                }
            });

            if (i <= 4)
                gui.setOption(8, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, null, false, false, "§7Ca va ?") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                    }
                });
        }

        pl.getConfigUtils().getAllKits().forEach(kit -> {
            if (!kit.getItems().isEmpty()) {
                gui.setOption(x, y, new AbstractGuiOption(kit.getItems().get(0), "§c" + kit.getName(), "§7Clique gauche pour éditer", "§7Clique droit pour supprimer le kit") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                        if (selectionType == InventoryAction.PICKUP_ALL) {
                            gui.closeGUI(selecter);
                            new KitEditGui(selecter, kit);
                        } else if (selectionType == InventoryAction.PICKUP_HALF) {
                            pl.getConfigUtils().deleteKit(kit);
                            gui.closeGUI(selecter);
                            new KitsGui(selecter);
                        }
                    }
                });

                x++;
                if (x >= 8) {
                    if (y >= 3)
                        return;
                    x = 1;
                    y++;
                }
            }
        });

        gui.setOption(8, 4, new AbstractGuiOption(Material.PAPER, "§aNouveau", 1, 0, null, false, false, "§7Clique pour créer un kit") {
            @Override
            public void onSelection(Player selecter, InventoryAction selectionType) {
                AnvilGUI anvilGUI = new AnvilGUI(selecter, event -> {
                    if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                        event.setWillClose(true);
                        event.setWillDestroy(true);

                        if (event.getName() != null) {
                            event.setWillClose(false);
                            event.setWillDestroy(false);
                            gui.closeGUI(selecter);
                            Kit kit = new Kit(event.getName(), new ArrayList<>());
                            TaupeGun.getInstance().getConfigUtils().addKit(kit);
                            new KitEditGui(selecter, kit);
                        }
                    }

                    else {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                    }
                });

                anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, new ItemStack(Material.PAPER));

                anvilGUI.open();
            }
        });

        gui.openGUI(p);
    }
}
