package fr.lpoyo.taupegun.guis;

import fr.lpoyo.taupegun.core.TaupeGun;
import fr.lpoyo.taupegun.utils.gui.AbstractGuiOption;
import fr.lpoyo.taupegun.utils.gui.FormattedInventoryGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
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
            gui.setOption(i, 0, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {
                }
            });

            if (i <= 4)
                gui.setOption(0, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                    }
                });

            gui.setOption(i, 4, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {
                }
            });

            if (i <= 4)
                gui.setOption(8, i, new AbstractGuiOption(Material.STAINED_GLASS_PANE, "§6Coucou", 1, random.nextInt(15) + 1, "§7Ca va ?") {
                    @Override
                    public void onSelection(Player selecter, InventoryAction selectionType) {
                    }
                });
        }

        pl.getConfigUtils().getAllKits().forEach((s, itemStacks) -> {
            System.out.println(itemStacks);
            gui.setOption(x, y, new AbstractGuiOption(itemStacks.get(0).getType(), "§c" + s, 1, 0, "§7Clique gauche pour éditer", "§7Clique droit pour voir") {
                @Override
                public void onSelection(Player selecter, InventoryAction selectionType) {

                }
            });

            x++;
            if (x >= 8) {
                x = 1;
                y++;
            }
        });

        gui.openGUI(p);
    }
}
